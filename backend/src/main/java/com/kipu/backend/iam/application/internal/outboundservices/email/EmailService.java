package com.kipu.backend.iam.application.internal.outboundservices.email;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private static final Logger log = LoggerFactory.getLogger(EmailService.class);
    private final JavaMailSender javaMailSender;

    public EmailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void sendOtpEmail(String to, String otpCode) {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setFrom("pircaindustries@gmail.com");
            helper.setTo(to);
            helper.setSubject("Código de Verificación Kipu");

            String htmlContent = "<div style=\"font-family: Arial, sans-serif; max-width: 600px; margin: 0 auto; padding: 20px; border: 1px solid #ddd; border-radius: 10px;\">" +
                    "<h2 style=\"color: #2c3e50; text-align: center;\">Verificación de Seguridad</h2>" +
                    "<p style=\"color: #555;\">Hola,</p>" +
                    "<p style=\"color: #555;\">Has solicitado un código de verificación para tu cuenta en Kipu. Por favor, introduce el siguiente código de 6 dígitos para continuar:</p>" +
                    "<div style=\"background-color: #f8f9fa; padding: 15px; text-align: center; border-radius: 5px; margin: 20px 0;\">" +
                    "<span style=\"font-size: 24px; font-weight: bold; letter-spacing: 5px; color: #3498db;\">" + otpCode + "</span>" +
                    "</div>" +
                    "<p style=\"color: #555; font-size: 12px;\">Este código expirará en 5 minutos. Si no solicitaste este código, puedes ignorar este correo con seguridad.</p>" +
                    "<hr style=\"border: none; border-top: 1px solid #eee; margin: 20px 0;\" />" +
                    "<p style=\"color: #999; font-size: 10px; text-align: center;\">&copy; 2026 Kipu - Pirca Industries. Todos los derechos reservados.</p>" +
                    "</div>";

            helper.setText(htmlContent, true);

            javaMailSender.send(message);
            log.info("OTP email sent successfully to: {}", to);
        } catch (MessagingException e) {
            log.error("Failed to send OTP email to {}: {}", to, e.getMessage(), e);
        }
    }

    public void sendHtmlEmail(String to, String subject, String htmlContent) {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setFrom("pircaindustries@gmail.com");
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(htmlContent, true);

            javaMailSender.send(message);
            log.info("HTML email sent successfully to: {}", to);
        } catch (MessagingException e) {
            log.error("Failed to send HTML email to {}: {}", to, e.getMessage(), e);
        }
    }
}

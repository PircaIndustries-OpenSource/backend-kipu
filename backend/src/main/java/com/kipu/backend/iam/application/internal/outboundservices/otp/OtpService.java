package com.kipu.backend.iam.application.internal.outboundservices.otp;

import com.kipu.backend.iam.application.internal.outboundservices.email.EmailService;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Service
public class OtpService {

    private final EmailService emailService;
    private final Map<String, String> otpStorage = new ConcurrentHashMap<>();
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    private final Random random = new Random();

    public OtpService(EmailService emailService) {
        this.emailService = emailService;
    }

    public void generateAndSendOtp(String email) {
        // Generate a 6 digit code
        int code = 100000 + random.nextInt(900000);
        String otp = String.valueOf(code);

        // Store OTP
        otpStorage.put(email, otp);

        // Send via Email
        emailService.sendOtpEmail(email, otp);

        // Schedule expiration (5 minutes)
        scheduler.schedule(() -> otpStorage.remove(email, otp), 5, TimeUnit.MINUTES);
    }

    public boolean validateOtp(String email, String otp) {
        String storedOtp = otpStorage.get(email);
        if (storedOtp != null && storedOtp.equals(otp)) {
            otpStorage.remove(email); // consume OTP
            return true;
        }
        return false;
    }
}

package com.kipu.backend.shared.infrastructure.openapi.configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import org.springdoc.core.customizers.OperationCustomizer;
import io.swagger.v3.oas.models.parameters.HeaderParameter;

/**
 * Configures the OpenAPI specification exposed by the platform.
 */
@Configuration
public class OpenApiConfiguration {

        // Properties
        @Value("${spring.application.name}")
        private String applicationName;

        @Value("${documentation.application.description}")
        private String applicationDescription;

        @Value("${documentation.application.version}")
        private String applicationVersion;

        // Methods

        /**
         * Builds the OpenAPI document used by Swagger UI and client generation tools.
         *
         * @return configured OpenAPI descriptor
         */
        @Bean
        public OpenAPI kipuOpenApi() {

                // General configuration
                var openApi = new OpenAPI();
                openApi
                        .info(new Info()
                                .title(this.applicationName)
                                .description(this.applicationDescription)
                                .version(this.applicationVersion)
                                .contact(new Contact()
                                        .name("Kipu Support")
                                        .email("support@kipu.com")
                                        .url("https://kipu.com/support"))
                                .license(new License()
                                        .name("Apache 2.0")
                                        .url("https://www.apache.org/licenses/LICENSE-2.0.html")))
                        .externalDocs(new ExternalDocumentation()
                                .description("Kipu Platform Documentation Wiki")
                                .url("https://github.com/kipu/backend/wiki"));

                // Add server configurations
                openApi.servers(List.of(
                        new Server()
                                .url("http://158.23.163.220")
                                .description("Production Environment"),
                        new Server()
                                .url("http://localhost:8080")
                                .description("Local Development Environment")
                ));

                // Add a security scheme for JWT Authentication
                final String securitySchemeName = "bearerAuth";

                openApi.addSecurityItem(new SecurityRequirement()
                                .addList(securitySchemeName))
                        .components(new Components()
                                .addSecuritySchemes(securitySchemeName,
                                        new SecurityScheme()
                                                .name(securitySchemeName)
                                                .type(SecurityScheme.Type.HTTP)
                                                .scheme("bearer")
                                                .bearerFormat("JWT")
                                                .in(SecurityScheme.In.HEADER)
                                                .description("Please input Bearer JWT token in the format 'Bearer <token>'")));

                return openApi;
        }

        @Bean
        public OperationCustomizer addAcceptLanguageHeader() {
                return (operation, handlerMethod) -> {
                        operation.addParametersItem(new HeaderParameter()
                                        .name("Accept-Language")
                                        .description("Language preference (e.g. es, en)")
                                        .required(false));
                        return operation;
                };
        }
}
package com.kipu.backend.shared.infrastructure.documentation;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Shared OpenAPI documentation configuration defining metadata and bearer token
 * authorization schemes.
 */
@Configuration
public class OpenAPIConfiguration {

        @Bean
        public OpenAPI kipuOpenAPI() {
                return new OpenAPI()
                                .info(new Info()
                                                .title("Kipu Backend API")
                                                .description("REST API documentation for Kipu Platform application.")
                                                .version("v0.0.1")
                                                .license(new License().name("Apache 2.0").url("https://springdoc.org")))
                                .addSecurityItem(new SecurityRequirement().addList("bearerAuth"))
                                .components(new Components()
                                                .addSecuritySchemes("bearerAuth", new SecurityScheme()
                                                                .name("bearerAuth")
                                                                .type(SecurityScheme.Type.HTTP)
                                                                .scheme("bearer")
                                                                .bearerFormat("JWT")
                                                                .in(SecurityScheme.In.HEADER)
                                                                .description("Please input Bearer JWT token in the format 'Bearer <token>'")));
        }
}

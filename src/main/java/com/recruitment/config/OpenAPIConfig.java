package com.recruitment.config;


import io.swagger.v3.oas.models.Components;
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

@Configuration
public class OpenAPIConfig {

    @Value("${bezkoder.openapi.dev-url}")
    private String devUrl;

    @Value("${bezkoder.openapi.prod-url}")
    private String prodUrl;

    @Bean
    public OpenAPI myOpenAPI () {
        // Servers
        Server devServer = new Server().url(devUrl).description("Server URL in Development environment");
        Server prodServer = new Server().url(prodUrl).description("Server URL in Production environment");

        // Contact
        Contact contact = new Contact().email("bezkoder@gmail.com").name("BezKoder").url("https://www.bezkoder.com");

        // License
        License mitLicense = new License().name("MIT License").url("https://choosealicense.com/licenses/mit/");

        // Info
        Info info = new Info().title("Tutorial Management API")
                              .version("1.0")
                              .contact(contact)
                              .description("This API exposes endpoints to manage tutorials.")
                              .termsOfService("https://www.bezkoder.com/terms")
                              .license(mitLicense);

        // Define Security Scheme for Bearer Token
        SecurityScheme bearerScheme = new SecurityScheme().type(SecurityScheme.Type.HTTP)
                                                          .scheme("bearer")
                                                          .bearerFormat("JWT");

        // Add to Components
        Components components = new Components().addSecuritySchemes("BearerAuth" , bearerScheme);

        // Add a global or top-level Security Requirement (optional)
        SecurityRequirement securityRequirement = new SecurityRequirement().addList("BearerAuth");

        // Combine everything in the OpenAPI
        return new OpenAPI().info(info)
                            .servers(List.of(devServer , prodServer))
                            .components(components)
                            .addSecurityItem(securityRequirement);
    }
}
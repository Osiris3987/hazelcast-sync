package com.example.hackathon_becoder_backend.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
        info = @Info(
                title = "Fin Tech Engine",
                description = "Sample API of Fin Tech engine", version = "1.0.0",
                contact = @Contact(
                        name = "Karabanov Andrey",
                        email = "deskpa@yandex.ru"
                )
        ),
        servers = {
                @Server(url = "http://localhost:8080", description = "Default Server URL"),
        }
)
@SecurityScheme(
        name = "JWT",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        scheme = "bearer"
)
public class OpenApiConfig {

}
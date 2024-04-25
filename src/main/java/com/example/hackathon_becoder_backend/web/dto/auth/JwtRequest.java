package com.example.hackathon_becoder_backend.web.dto.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;


@Data
@Schema(description = "JWT Authentication Request")
public class JwtRequest {
    @Schema(description = "Username", required = true, example = "johndoe@gmail.com")
    @NotNull(message = "Username must be not null")
    private String username;

    @Schema(description = "Password", required = true, example = "password123")
    @NotNull(message = "Password must be not null")
    private String password;
}
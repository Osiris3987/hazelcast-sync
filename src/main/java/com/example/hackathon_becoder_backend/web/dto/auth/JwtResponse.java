package com.example.hackathon_becoder_backend.web.dto.auth;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class JwtResponse {
    @NotNull
    private UUID id;
    @NotNull
    private String username;
    @NotNull
    private String accessToken;
    @NotNull
    private String refreshToken;
}

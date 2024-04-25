package com.example.hackathon_becoder_backend.web.dto.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;
@Data
@Schema(description = "JWT Authentication Response")
public class JwtResponse {
    @Schema(description = "Unique identifier of the user", example = "123e4567-e89b-12d3-a456-426655440000")
    @NotNull
    private UUID id;

    @Schema(description = "Username", example = "johndoe@gmail.com")
    @NotNull
    private String username;

    @Schema(description = "Access Token", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c")
    @NotNull
    private String accessToken;

    @Schema(description = "Refresh Token", example = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c")
    @NotNull
    private String refreshToken;
}

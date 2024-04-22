package com.example.hackathon_becoder_backend.web.dto.client;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
public class ClientDto {
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private UUID id;
    private String name;
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private String status;
}

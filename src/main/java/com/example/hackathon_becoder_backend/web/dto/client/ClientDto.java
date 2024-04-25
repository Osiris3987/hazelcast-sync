package com.example.hackathon_becoder_backend.web.dto.client;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.UUID;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "DTO for Client entity")
public class ClientDto {
    @Schema(description = "Unique identifier of the client", example = "123e4567-e89b-12d3-a456-426655440000", accessMode = Schema.AccessMode.READ_ONLY)
    private UUID id;

    @Schema(description = "Name of the client", example = "John Doe")
    private String name;

    @Schema(description = "Status of the client", example = "Active", accessMode = Schema.AccessMode.READ_ONLY)
    private String status;
}

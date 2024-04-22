package com.example.hackathon_becoder_backend.web.dto.client;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.util.UUID;

@Data
public class ClientDto {
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private UUID id;

    private String name;

}

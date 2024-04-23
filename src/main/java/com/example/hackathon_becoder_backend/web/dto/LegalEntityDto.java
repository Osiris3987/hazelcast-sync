package com.example.hackathon_becoder_backend.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class LegalEntityDto {
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private UUID id;
    private String name;
    private BigDecimal balance;
    private Integer version;

    public record AssignClientToLegalEntity(UUID legalEntityId, UUID clientId){}
}

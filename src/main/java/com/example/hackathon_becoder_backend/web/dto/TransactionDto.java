package com.example.hackathon_becoder_backend.web.dto;

import com.example.hackathon_becoder_backend.domain.transaction.TransactionType;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class TransactionDto {
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private UUID id;
    private TransactionType type;
    private BigDecimal amount;
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private UUID clientId;
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private UUID legalEntityId;
}

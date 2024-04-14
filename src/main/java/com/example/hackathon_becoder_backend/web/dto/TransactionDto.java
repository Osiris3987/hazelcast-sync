package com.example.hackathon_becoder_backend.web.dto;

import com.example.hackathon_becoder_backend.domain.transaction.TransactionType;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class TransactionDto {
    private UUID id;
    private TransactionType type;
    private BigDecimal amount;
    private UUID clientId;
    private UUID legalEntityId;
}

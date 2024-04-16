package com.example.hackathon_becoder_backend.web.dto;

import com.example.hackathon_becoder_backend.domain.transaction.TransactionType;
import com.example.hackathon_becoder_backend.web.dto.validation.OnCreate;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class TransactionDto {
    @Null(groups = OnCreate.class)
    private UUID id;
    @NotNull(groups = OnCreate.class)
    private TransactionType type;
    @NotNull(groups = OnCreate.class)
    private BigDecimal amount;
    @NotNull(groups = OnCreate.class)
    private UUID clientId;
    @NotNull(groups = OnCreate.class)
    private UUID legalEntityId;
}

package com.example.hackathon_becoder_backend.web.dto;

import com.example.hackathon_becoder_backend.domain.transaction.TransactionType;
import com.example.hackathon_becoder_backend.web.dto.validation.OnCreate;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class TransactionDto {
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    @Null(message = "transaction id must be null", groups = OnCreate.class)
    private UUID id;
    @NotNull(message = "transaction type must not be null", groups = OnCreate.class)
    private TransactionType type;
    @NotNull(message = "amount must not be null", groups = OnCreate.class)
    @Positive
    private BigDecimal amount;
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    @Null(message = "client id must be null", groups = OnCreate.class)
    private UUID clientId;
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    @Null(message = "legal entity id must be null", groups = OnCreate.class)
    private UUID legalEntityId;
}

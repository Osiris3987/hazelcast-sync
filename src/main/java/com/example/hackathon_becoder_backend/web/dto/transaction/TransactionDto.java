package com.example.hackathon_becoder_backend.web.dto.transaction;

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
@Schema(description = "DTO for Transaction entity")
public class TransactionDto {
    @Schema(description = "Unique identifier of the transaction", example = "123e4567-e89b-12d3-a456-426655440000", accessMode = Schema.AccessMode.READ_ONLY)
    @Null(message = "transaction id must be null", groups = OnCreate.class)
    private UUID id;

    @Schema(description = "Type of the transaction")
    @NotNull(message = "transaction type must not be null", groups = OnCreate.class)
    private TransactionType type;

    @Schema(description = "Amount of the transaction", example = "100.00")
    @NotNull(message = "amount must not be null", groups = OnCreate.class)
    private BigDecimal amount;

    @Schema(description = "Unique identifier of the client associated with the transaction", example = "123e4567-e89b-12d3-a456-426655440001", accessMode = Schema.AccessMode.READ_ONLY)
    @Null(message = "client id must be null", groups = OnCreate.class)
    private UUID clientId;

    @Schema(description = "Unique identifier of the legal entity associated with the transaction", example = "123e4567-e89b-12d3-a456-426655440002", accessMode = Schema.AccessMode.READ_ONLY)
    @Null(message = "legal entity id must be null", groups = OnCreate.class)
    private UUID legalEntityId;
}
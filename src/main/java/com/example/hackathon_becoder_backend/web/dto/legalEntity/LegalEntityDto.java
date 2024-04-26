package com.example.hackathon_becoder_backend.web.dto.legalEntity;

import com.example.hackathon_becoder_backend.web.dto.validation.OnCreate;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Schema(description = "DTO for Legal Entity entity")
public class LegalEntityDto {
    @Schema(description = "Unique identifier of the legal entity", example = "123e4567-e89b-12d3-a456-426655440000", accessMode = Schema.AccessMode.READ_ONLY)
    @Null(message = "Legal entity id must be null", groups = OnCreate.class)
    private UUID id;

    @Schema(description = "Name of the legal entity", example = "ABC Corporation")
    @NotNull(message = "Legal entity name must not be null", groups = OnCreate.class)
    private String name;

    @Schema(description = "Balance of the legal entity", example = "10000.50")
    @NotNull(message = "Legal entity balance must not be null", groups = OnCreate.class)
    private BigDecimal balance;

    @Schema(description = "Owner of the legal entity", example = "yfnf72@gmail.com", accessMode = Schema.AccessMode.READ_ONLY)
    @Null(message = "Legal entity owner must be null", groups = OnCreate.class)
    private String owner;

    @Schema(description = "Request for assigning a client to a legal entity")
    public record AssignClientToLegalEntity(
            @Schema(description = "Unique identifier of the legal entity", example = "123e4567-e89b-12d3-a456-426655440000") UUID legalEntityId,
            @Schema(description = "Unique identifier of the client", example = "123e4567-e89b-12d3-a456-426655440001") UUID clientId
    ){}
}
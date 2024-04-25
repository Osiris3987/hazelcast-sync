package com.example.hackathon_becoder_backend.web.dto.legalEntity;

import com.example.hackathon_becoder_backend.web.dto.client.ClientDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Data
@Schema(description = "DTO for Legal Entity entity with associated clients")
public class LegalEntityWithClientsDto {
    @Schema(description = "Unique identifier of the legal entity", example = "123e4567-e89b-12d3-a456-426655440000", accessMode = Schema.AccessMode.READ_ONLY)
    private UUID id;

    @Schema(description = "Name of the legal entity", example = "ABC Corporation")
    private String name;

    @Schema(description = "Balance of the legal entity", example = "10000.50")
    private BigDecimal balance;

    @Schema(description = "Set of clients associated with the legal entity")
    private Set<ClientDto> clients;
}

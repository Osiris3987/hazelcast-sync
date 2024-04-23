package com.example.hackathon_becoder_backend.web.dto;

import com.example.hackathon_becoder_backend.domain.client.Client;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Set;
import java.util.UUID;

@Data
public class LegalEntityWithClientsDto {
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private UUID id;
    private String name;
    private BigDecimal balance;
    private Integer version;
    private Set<Client> clients;
}

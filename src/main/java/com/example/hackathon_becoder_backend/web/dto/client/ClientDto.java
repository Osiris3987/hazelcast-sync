package com.example.hackathon_becoder_backend.web.dto.client;

import com.example.hackathon_becoder_backend.domain.legal_entity.LegalEntity;
import com.example.hackathon_becoder_backend.web.dto.validation.OnCreate;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.Null;
import lombok.Data;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.Set;
import java.util.UUID;

@Data
public class ClientDto {
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private UUID id;

    private String name;
    
    private Set<LegalEntity> legalEntities;
}

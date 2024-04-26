package com.example.hackathon_becoder_backend.web.dto.client;

import com.example.hackathon_becoder_backend.web.dto.legalEntity.LegalEntityDto;
import com.example.hackathon_becoder_backend.web.dto.validation.OnCreate;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.util.Set;
import java.util.UUID;

@Data
@Schema(description = "DTO for client with legal entities")
public class ClientWithLegalEntitiesDto {
    @Schema(description = "Unique identifier of the client", example = "123e4567-e89b-12d3-a456-426655440000")
    private UUID id;

    @Schema(description = "Name of the client", example = "John Doe")
    private String name;

    @Schema(description = "Status of the client", example = "EXISTS")
    private String status;

    @Schema(description = "Username of the client", maxLength = 255, example = "johndoe@gmail.com")
    private String username;

    @Schema(description = "Set of legal entities associated with the client")
    private Set<LegalEntityDto> legalEntities;
}

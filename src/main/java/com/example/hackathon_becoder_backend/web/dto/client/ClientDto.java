package com.example.hackathon_becoder_backend.web.dto.client;

import com.example.hackathon_becoder_backend.web.dto.validation.OnCreate;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.util.UUID;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "DTO for Client entity")
public class ClientDto {
    @Schema(description = "Unique identifier of the client", example = "123e4567-e89b-12d3-a456-426655440000", accessMode = Schema.AccessMode.READ_ONLY)
    private UUID id;

    @Schema(description = "Name of the client", example = "John Doe")
    private String name;

    @Schema(description = "Status of the client", example = "EXISTS", accessMode = Schema.AccessMode.READ_ONLY)
    private String status;

    @Schema(description = "Username of the client", maxLength = 255, example = "johndoe@gmail.com")
    @Length(max = 255, message = "Username length must be smaller than 255 symbols.", groups = {OnCreate.class})
    private String username;

    @Schema(description = "Password of the client", accessMode = Schema.AccessMode.WRITE_ONLY)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotNull(message = "Password must be not null.", groups = {OnCreate.class})
    private String password;
}

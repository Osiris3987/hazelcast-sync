package com.example.hackathon_becoder_backend.web.dto.client;

import com.example.hackathon_becoder_backend.web.dto.validation.OnCreate;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.util.UUID;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "DTO for Client entity")
public class ClientDto {
    @Schema(description = "Unique identifier of the client", example = "123e4567-e89b-12d3-a456-426655440000", accessMode = Schema.AccessMode.READ_ONLY)
    @Null(message = "Client id must be null", groups = OnCreate.class)
    private UUID id;

    @Schema(description = "Name of the client", example = "John Doe")
    @NotNull(message = "Client name must not be null", groups = OnCreate.class)
    private String name;

    @Schema(description = "Status of the client", example = "EXISTS", accessMode = Schema.AccessMode.READ_ONLY)
    @Null(message = "Client status must be null", groups = OnCreate.class)
    private String status;

    @Schema(description = "Username of the client", maxLength = 255, example = "johndoe@gmail.com")
    @Length(max = 255, message = "Username length must be smaller than 255 symbols.", groups = {OnCreate.class})
    @Email(message = "Username must be a valid email", groups = OnCreate.class)
    private String username;

    @Schema(description = "Password of the client", accessMode = Schema.AccessMode.WRITE_ONLY)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotNull(message = "Password must be not null.", groups = {OnCreate.class})
    private String password;
}

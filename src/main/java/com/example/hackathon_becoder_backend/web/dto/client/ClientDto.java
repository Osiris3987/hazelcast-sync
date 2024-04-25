package com.example.hackathon_becoder_backend.web.dto.client;

import com.example.hackathon_becoder_backend.web.dto.validation.OnCreate;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.util.UUID;

@Data
public class ClientDto {
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private UUID id;
    private String name;
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private String status;

    @Length(max = 255,
            message = "Username length must be smaller than 255 symbols.",
            groups = {OnCreate.class})
    private String username;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotNull(message = "Password must be not null.",
            groups = {OnCreate.class})
    private String password;
}

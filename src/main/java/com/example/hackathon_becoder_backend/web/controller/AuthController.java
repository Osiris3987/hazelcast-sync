package com.example.hackathon_becoder_backend.web.controller;

import com.example.hackathon_becoder_backend.domain.client.Client;
import com.example.hackathon_becoder_backend.exception.ErrorMessage;
import com.example.hackathon_becoder_backend.service.AuthService;
import com.example.hackathon_becoder_backend.service.ClientService;
import com.example.hackathon_becoder_backend.web.dto.auth.JwtRequest;
import com.example.hackathon_becoder_backend.web.dto.auth.JwtResponse;
import com.example.hackathon_becoder_backend.web.dto.client.ClientDto;
import com.example.hackathon_becoder_backend.web.dto.legalEntity.LegalEntityWithClientsDto;
import com.example.hackathon_becoder_backend.web.dto.validation.OnCreate;
import com.example.hackathon_becoder_backend.web.mapper.ClientMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Tag(
        name = "Auth Controller",
        description = "Auth API"
)
public class AuthController {

    private final AuthService authService;
    private final ClientService clientService;
    private final ClientMapper clientMapper;


    @Operation(summary = "Generates a new JWT for user requests", description = "Generates a new JWT for user requests and using username and password")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successful operation",content = {@Content(schema = @Schema(implementation = JwtResponse.class), mediaType = "application/json")}),
    })
    @PostMapping("/login")
    public JwtResponse login(
            @Validated @RequestBody final JwtRequest loginRequest
    ) {
        return authService.login(loginRequest);
    }

    @Operation(summary = "Registers client in system.", description = "Creates new user accounts in data base.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successful operation",content = {@Content(schema = @Schema(implementation = ClientDto.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", description = "Client already exist",content = {@Content(schema = @Schema(implementation = ErrorMessage.class), mediaType = "application/json")})
    })
    @PostMapping("/register")
    public ClientDto register(
            @Validated(OnCreate.class)
            @RequestBody final ClientDto userDto
    ) {
        Client client = clientMapper.toEntity(userDto);
        Client createdUser = clientService.create(client);
        return clientMapper.toDto(createdUser);
    }

    @Operation(summary = "Refreshes JWT", description = "Generates new java web token by refresh token.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successful operation",content = {@Content(schema = @Schema(implementation = JwtResponse.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", description = "Client already exist",content = {@Content(schema = @Schema(implementation = ErrorMessage.class), mediaType = "application/json")})
    })
    @PostMapping("/refresh")
    public JwtResponse refresh(
            @RequestBody final String refreshToken
    ) {
        return authService.refresh(refreshToken);
    }

}

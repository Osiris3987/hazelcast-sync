package com.example.hackathon_becoder_backend.web.controller;


import com.example.hackathon_becoder_backend.exception.ErrorMessage;
import com.example.hackathon_becoder_backend.service.ClientService;
import com.example.hackathon_becoder_backend.service.TransactionService;
import com.example.hackathon_becoder_backend.web.dto.client.ClientWithLegalEntitiesDto;
import com.example.hackathon_becoder_backend.web.dto.transaction.TransactionDto;
import com.example.hackathon_becoder_backend.web.dto.client.ClientDto;
import com.example.hackathon_becoder_backend.web.mapper.ClientMapper;
import com.example.hackathon_becoder_backend.web.mapper.TransactionMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/client")
@Validated
@Tag(name = "Client API", description = "Endpoints for managing clients")
@SecurityRequirement(name = "JWT")
public class ClientController {
    private final ClientService clientService;
    private final ClientMapper clientMapper;

    private final TransactionService transactionService;
    private final TransactionMapper transactionMapper;

    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successful operation",content = {@Content(schema = @Schema(implementation = ClientDto.class), mediaType = "application/json")})
    })
    @Operation(summary = "Get all clients", description = "Get a list of all clients", operationId = "getAllClients")
    @GetMapping("")
    public List<ClientDto> getAllClients() {
        return clientMapper.toDtoList(clientService.findAll());
    }

    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successful operation",content = {@Content(schema = @Schema(implementation = ClientDto.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", description = "Invalid input",content = {@Content(schema = @Schema(implementation = ErrorMessage.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", description = "Client not found",content = {@Content(schema = @Schema(implementation = ErrorMessage.class), mediaType = "application/json")})
    })
    @Operation(summary = "Get client by ID", description = "Get a client by its ID", operationId = "getClientById")
    @GetMapping("/{clientId}")
    public ClientDto getClientById(
            @PathVariable @Parameter(description = "Client id", required = true) UUID clientId
    ) {
        return clientMapper.toDto(clientService.findById(clientId));
    }

    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(array = @ArraySchema(schema = @Schema(implementation = TransactionDto.class)), mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "Invalid input",content = {@Content(schema = @Schema(implementation = ErrorMessage.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", description = "Client not found",content = {@Content(schema = @Schema(implementation = ErrorMessage.class), mediaType = "application/json")})
    })
    @Operation(summary = "Get client by ID", description = "Get all transactions of client by its ID", operationId = "getTransactionsByClientId")
    @GetMapping("/{clientId}/transactions")
    public List<TransactionDto> getTransactionsByClientId(
            @PathVariable @Parameter(description = "Client id", required = true) UUID clientId) {
        return transactionMapper.toDtoList(transactionService.getAllByClientId(clientId));
    }

    @Operation(summary = "Returns Client DTO with legal entities", description = "Return client DTO with legal entities and uses client if to it.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successful operation",content = {@Content(schema = @Schema(implementation = ClientWithLegalEntitiesDto.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", description = "Legal entity or client not found",content = {@Content(schema = @Schema(implementation = ErrorMessage.class), mediaType = "application/json")})
    })
    @GetMapping("/{clientId}/legalEntities")
    public ClientWithLegalEntitiesDto getLegalEntitiesByClientId(@PathVariable UUID clientId) {
        return clientMapper.toClientWithLegalEntitiesDto(clientService.findLegalEntitiesByClientId(clientId));
    }

    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Successful operation"),
            @ApiResponse(responseCode = "400", description = "Invalid input",content = {@Content(schema = @Schema(implementation = ErrorMessage.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", description = "Client not found",content = {@Content(schema = @Schema(implementation = ErrorMessage.class), mediaType = "application/json")})
    })
    @Operation(summary = "Delete client by ID", description = "Set the \"Deleted\" status for this client", operationId = "deleteById")
    @DeleteMapping("/{clientId}")
    public ResponseEntity<String> deleteById(
            @PathVariable @Parameter(description = "Client id", required = true) UUID clientId
    ) {
        clientService.deleteById(clientId);
        return ResponseEntity.noContent().build();
    }
}
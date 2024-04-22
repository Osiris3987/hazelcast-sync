package com.example.hackathon_becoder_backend.web.controller;


import com.example.hackathon_becoder_backend.domain.legal_entity.LegalEntity;
import com.example.hackathon_becoder_backend.service.ClientService;
import com.example.hackathon_becoder_backend.web.dto.LegalEntityDto;
import com.example.hackathon_becoder_backend.web.dto.client.ClientDto;
import com.example.hackathon_becoder_backend.web.mapper.ClientMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
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
public class ClientController {
    private final ClientService clientService;
    private final ClientMapper clientMapper;

    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successful operation")
    })
    @Operation(summary = "Get all clients", description = "Get a list of all clients", operationId = "getAllClients")
    @GetMapping("")
    public List<ClientDto> getAllClients() {
        return clientMapper.toDtoList(clientService.findAll());
    }

    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "404", description = "Topic not found")
    })
    @Operation(summary = "Get all legal entities by client ID", description = "Get a list of all legal entities belonging to a client", operationId = "getAllLegalEntityByClientId")
    @GetMapping("/{clientId}/legalEntities")
    public List<LegalEntityDto> getAllClientsByLegalEntityId(
            @PathVariable @Parameter(description = "Legal entity id", required = true) UUID clientId
    ) {
//        return clientMapper.toDtoList(clientService.findAllLegalEntitiesByClientId(clientId));
        return null; //TO DO
    }

    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "404", description = "Client not found")
    })
    @Operation(summary = "Get client by ID", description = "Get a client by its ID", operationId = "getClientById")
    @GetMapping("/{clientId}")
    public ClientDto getClientById(
            @PathVariable @Parameter(description = "Client id", required = true) UUID clientId
    ) {
        return clientMapper.toDto(clientService.findById(clientId));
    }

    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "400", description = "Invalid input (Ex.Client already exists)"),
            @ApiResponse(responseCode = "422", description = "Validation exception")
    })
    @Operation(summary = "Create client", description = "Create new client to store client", operationId = "createClient")
    @PostMapping("")
    public ClientDto createClient(
            @Valid @RequestBody final ClientDto clientDto
    ) {
        return clientMapper.toDto(clientService.create(clientMapper.toEntity(clientDto)));
    }

    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Successful operation"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "404", description = "Client not found")
    })
    @Operation(summary = "Delete client by ID", description = "Set the \"Deleted\" status for this client", operationId = "deleteById")
    @DeleteMapping("")
    public ResponseEntity<String> deleteById(
            @RequestParam @Parameter(description = "Client id", required = true) UUID clientId
    ) {
        clientService.deleteById(clientId);
        return ResponseEntity.noContent().build();
    }
}
package com.example.hackathon_becoder_backend.web.controller;

import com.example.hackathon_becoder_backend.domain.legal_entity.LegalEntity;
import com.example.hackathon_becoder_backend.domain.transaction.Transaction;
import com.example.hackathon_becoder_backend.exception.ErrorMessage;
import com.example.hackathon_becoder_backend.service.LegalEntityService;
import com.example.hackathon_becoder_backend.service.TransactionService;
import com.example.hackathon_becoder_backend.web.dto.legalEntity.LegalEntityDto;
import com.example.hackathon_becoder_backend.web.dto.legalEntity.LegalEntityWithClientsDto;
import com.example.hackathon_becoder_backend.web.dto.transaction.TransactionDto;
import com.example.hackathon_becoder_backend.web.dto.validation.OnCreate;
import com.example.hackathon_becoder_backend.web.mapper.LegalEntityMapper;
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
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/legalEntity")
@Tag(name = "Legal Entity API", description = "Endpoints for managing legal entities")
@SecurityRequirement(name = "JWT")
public class LegalEntityController {
    private final LegalEntityService legalEntityService;
    private final LegalEntityMapper legalEntityMapper;

    private final TransactionService transactionService;
    private final TransactionMapper transactionMapper;

    @Operation(summary = "Create new legal entity", description = "Creates new legal entity via delegated LegalEntityDto")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(array = @ArraySchema(schema = @Schema(implementation = LegalEntityDto.class)), mediaType = "application/json")),
    })
    @PostMapping
    public LegalEntityDto create(
            @Validated(OnCreate.class) @RequestBody LegalEntityDto legalEntityDto,
            @AuthenticationPrincipal UserDetails userDetails
            ) {
        LegalEntity legalEntity = legalEntityService.create(legalEntityMapper.toEntity(legalEntityDto), userDetails.getUsername());
        return legalEntityMapper.toDto(legalEntity);
    }

    @Operation(summary = "Get all legal entities", description = "Get all legal entities")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successful operation")
    })
    @GetMapping
    public List<LegalEntityDto> getAll() {
        return legalEntityMapper.toDtoList(legalEntityService.getAll());
    }

    @Operation(summary = "Get legal entity", description = "Get a legal entity by its ID")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Successfully operation"),
            @ApiResponse(responseCode = "404", description = "Legal entity not found",content = {@Content(schema = @Schema(implementation = ErrorMessage.class), mediaType = "application/json")})
    })
    @GetMapping("/{legalEntityId}")
    public LegalEntityDto getById(
            @PathVariable UUID legalEntityId
    ) {
        return legalEntityMapper.toDto(legalEntityService.findById(legalEntityId));
    }

    @Operation(summary = "Delete legal entity", description = "Delete a legal entity by its ID")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Legal entity deleted"),
            @ApiResponse(responseCode = "404", description = "Legal entity not found",content = {@Content(schema = @Schema(implementation = ErrorMessage.class), mediaType = "application/json")})
    })
    @DeleteMapping("/{legalEntityId}")
    public ResponseEntity<String> deleteById(
            @PathVariable @Parameter(description = "LegalEntity id", required = true) UUID legalEntityId
    ) {
        legalEntityService.deleteById(legalEntityId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{legalEntityId}/transactions")
    @Operation(summary = "Get transactions by legal entity ID", description = "Get all transactions of a legal entity by its ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(array = @ArraySchema(schema = @Schema(implementation = TransactionDto.class)), mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "Legal entity not found",content = {@Content(schema = @Schema(implementation = ErrorMessage.class), mediaType = "application/json")})
    })
    public List<TransactionDto> getTransactionsByLegalEntityId(
            @PathVariable UUID legalEntityId) {
        List<Transaction> transactions = transactionService
                .getAllByLegalEntityId(legalEntityId);
        return transactionMapper.toDtoList(transactions);
    }

    @GetMapping("/{legalEntityId}/client")
    @Operation(summary = "Find all legal entities by client ID", description = "Get all legal entities of a client by client ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(array = @ArraySchema(schema = @Schema(implementation = LegalEntityDto.class)), mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "Client not found",content = {@Content(schema = @Schema(implementation = ErrorMessage.class), mediaType = "application/json")})
    })
    public LegalEntityWithClientsDto getClientsByLegalEntityId(@PathVariable("legalEntityId") @Parameter(description = "Client id", required = true) UUID legalEntityId) {
        return  legalEntityMapper.toDtoWithClients(legalEntityService.findClientsByLegalEntityId(legalEntityId));
    }

    @PostMapping("/assign")
    @Operation(summary = "Assign client to legal entity", description = "Assign a client to a legal entity")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successful operation",content = {@Content(schema = @Schema(implementation = LegalEntityWithClientsDto.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", description = "Legal entity or client not found",content = {@Content(schema = @Schema(implementation = ErrorMessage.class), mediaType = "application/json")})
    })
    public LegalEntityWithClientsDto assign(@RequestBody LegalEntityDto.AssignClientToLegalEntity assignParams) {
        return legalEntityMapper.toDtoWithClients(legalEntityService
                .assignClientToLegalEntity(
                        assignParams.legalEntityId(),
                        assignParams.clientId()
                )
        );
    }
}

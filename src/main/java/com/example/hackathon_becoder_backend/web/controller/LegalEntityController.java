package com.example.hackathon_becoder_backend.web.controller;

import com.example.hackathon_becoder_backend.domain.transaction.Transaction;
import com.example.hackathon_becoder_backend.service.LegalEntityService;
import com.example.hackathon_becoder_backend.service.TransactionService;
import com.example.hackathon_becoder_backend.web.dto.LegalEntityDto;
import com.example.hackathon_becoder_backend.web.dto.LegalEntityWithClientsDto;
import com.example.hackathon_becoder_backend.web.dto.TransactionDto;
import com.example.hackathon_becoder_backend.web.mapper.LegalEntityMapper;
import com.example.hackathon_becoder_backend.web.mapper.TransactionMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/legalEntity")
@Tag(name = "Legal Entity API", description = "Endpoints for managing legal entities")
public class LegalEntityController {
    private final LegalEntityService legalEntityService;
    private final LegalEntityMapper legalEntityMapper;

    private final TransactionService transactionService;
    private final TransactionMapper transactionMapper;

    @Operation(summary = "Delete legal entity", description = "Delete a legal entity by its ID")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Legal entity deleted"),
            @ApiResponse(responseCode = "404", description = "Legal entity not found")
    })
    @DeleteMapping
    public ResponseEntity<String> deleteById(
            @RequestParam @Parameter(description = "LegalEntity id", required = true) UUID legalEntityId
    ) {
        legalEntityService.deleteById(legalEntityId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{legalEntityId}/transactions")
    @Operation(summary = "Get transactions by legal entity ID", description = "Get all transactions of a legal entity by its ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "404", description = "Legal entity not found")
    })
    public List<TransactionDto> getTransactionsByLegalEntityId(
            @PathVariable UUID legalEntityId) {
        List<Transaction> transactions = transactionService
                .getAllByLegalEntityId(legalEntityId);
        return transactionMapper.toDtoList(transactions);
    }

    @GetMapping("/{clientId}/client")
    @Operation(summary = "Find all legal entities by client ID", description = "Get all legal entities of a client by client ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "404", description = "Client not found")
    })
    public List<LegalEntityDto> findAll(@PathVariable("clientId") @Parameter(description = "Client id", required = true) UUID clientId) {
        return legalEntityMapper.toDtoList(legalEntityService.getAllLegalEntitiesByClientId(clientId));
    }

    @PostMapping("/assign")
    @Operation(summary = "Assign client to legal entity", description = "Assign a client to a legal entity")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "404", description = "Legal entity or client not found")
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

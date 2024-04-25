package com.example.hackathon_becoder_backend.web.controller;

import com.example.hackathon_becoder_backend.domain.transaction.Transaction;
import com.example.hackathon_becoder_backend.exception.ErrorMessage;
import com.example.hackathon_becoder_backend.service.TransactionService;
import com.example.hackathon_becoder_backend.web.dto.TransactionDto;
import com.example.hackathon_becoder_backend.web.dto.validation.OnCreate;
import com.example.hackathon_becoder_backend.web.mapper.TransactionMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/transactions")
@Tag(name = "Transaction API", description = "Endpoints for managing transactions")
public class TransactionController {
    private final TransactionService transactionService;
    private final TransactionMapper transactionMapper;

    @PostMapping
    @Operation(summary = "Create transaction", description = "Create a new transaction")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successful operation",content = {@Content(schema = @Schema(implementation = TransactionDto.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", description = "Invalid input (Ex.Transaction already exists)",content = {@Content(schema = @Schema(implementation = ErrorMessage.class), mediaType = "application/json")})
    })
    public TransactionDto create(
            @Validated(OnCreate.class) @RequestBody TransactionDto transactionDto,
            @RequestParam UUID clientId,
            @RequestParam UUID legalEntityId
    ) {
        Transaction transaction = transactionService.create(
                transactionMapper.toEntity(transactionDto),
                clientId,
                legalEntityId
        );
        return transactionMapper.toDto(transaction);
    }
}

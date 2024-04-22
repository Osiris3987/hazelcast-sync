package com.example.hackathon_becoder_backend.web.controller;

import com.example.hackathon_becoder_backend.domain.transaction.Transaction;
import com.example.hackathon_becoder_backend.repository.LegalEntityRepository;
import com.example.hackathon_becoder_backend.service.LegalEntityService;
import com.example.hackathon_becoder_backend.service.TransactionService;
import com.example.hackathon_becoder_backend.web.dto.TransactionDto;
import com.example.hackathon_becoder_backend.web.mapper.LegalEntityMapper;
import com.example.hackathon_becoder_backend.web.mapper.TransactionMapper;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/legalEntity")
public class LegalEntityController {
    private final LegalEntityService legalEntityService;
    private final LegalEntityMapper legalEntityMapper;

    private final TransactionService transactionService;
    private final TransactionMapper transactionMapper;

    @DeleteMapping
    public ResponseEntity<String> deleteById(
            @RequestParam @Parameter(description = "LegalEntity id", required = true) UUID legalEntityId
    ) {
        legalEntityService.deleteById(legalEntityId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{legalEntityId}/transactions")
    public List<TransactionDto> getTransactionsByLegalEntityId(
            @PathVariable UUID legalEntityId) {
        List<Transaction> transactions = transactionService
                .getAllByLegalEntityId(legalEntityId);
        return transactionMapper.toDtoList(transactions);
    }
}

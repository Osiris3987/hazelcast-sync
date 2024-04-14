package com.example.hackathon_becoder_backend.web.controller;

import com.example.hackathon_becoder_backend.domain.transaction.Transaction;
import com.example.hackathon_becoder_backend.service.TransactionService;
import com.example.hackathon_becoder_backend.web.dto.TransactionDto;
import com.example.hackathon_becoder_backend.web.mapper.TransactionMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/transactions")
public class TransactionController {
    private final TransactionService transactionService;
    private final TransactionMapper transactionMapper;

    @PostMapping
    public TransactionDto create(
            @RequestBody TransactionDto transactionDto,
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

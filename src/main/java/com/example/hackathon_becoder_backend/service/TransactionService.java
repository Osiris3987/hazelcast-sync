package com.example.hackathon_becoder_backend.service;

import com.example.hackathon_becoder_backend.domain.transaction.Transaction;
import com.example.hackathon_becoder_backend.web.dto.TransactionDto;

import java.util.UUID;

public interface TransactionService {
    Transaction create(Transaction transaction, UUID clientId, UUID legalEntityId);
}

package com.example.hackathon_becoder_backend.service;

import com.example.hackathon_becoder_backend.domain.legal_entity.LegalEntity;
import com.example.hackathon_becoder_backend.domain.transaction.Transaction;
import com.example.hackathon_becoder_backend.domain.transaction.TransactionType;

import java.math.BigDecimal;
import java.util.UUID;

public interface LegalEntityService {
    LegalEntity findById(UUID id);
    void changeBalance(UUID id, BigDecimal amount, TransactionType type);
}

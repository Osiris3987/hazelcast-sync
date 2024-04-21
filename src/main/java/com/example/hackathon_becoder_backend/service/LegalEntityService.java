package com.example.hackathon_becoder_backend.service;

import com.example.hackathon_becoder_backend.domain.legal_entity.LegalEntity;
import com.example.hackathon_becoder_backend.domain.transaction.Transaction;
import com.example.hackathon_becoder_backend.domain.transaction.TransactionType;
import com.example.hackathon_becoder_backend.web.dto.LegalEntityWithClientsDto;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public interface LegalEntityService {
    LegalEntity findById(UUID id);
    void changeBalance(UUID id, BigDecimal amount, TransactionType type);
    void deleteById(UUID id);
    LegalEntity assignClientToLegalEntity(UUID legalEntityId, UUID clientId);
    List<LegalEntity> getAllLegalEntitiesByClientId(UUID clientId);
    List<LegalEntity> getAll();
}

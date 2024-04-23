package com.example.hackathon_becoder_backend.service.impl;

import com.example.hackathon_becoder_backend.domain.exception.LackOfBalanceException;
import com.example.hackathon_becoder_backend.domain.exception.ResourceNotFoundException;
import com.example.hackathon_becoder_backend.domain.legal_entity.LegalEntity;
import com.example.hackathon_becoder_backend.domain.transaction.TransactionType;
import com.example.hackathon_becoder_backend.repository.LegalEntityRepository;
import com.example.hackathon_becoder_backend.service.LegalEntityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LegalEntityServiceImpl implements LegalEntityService {
    private final LegalEntityRepository legalEntityRepository;
    private final LegalEntityServiceImpl self;

    @Override
    @Transactional(readOnly = true)
    public LegalEntity findById(UUID id) {
        return legalEntityRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Legal entity not found"));
    }

    @Override
    @Transactional()
    public void changeBalance(UUID id, BigDecimal amount, TransactionType type) {
        LegalEntity legalEntity = self.findById(id);
        switch (type) {
            case DEBIT -> {
                BigDecimal newBalance = legalEntity.getBalance().subtract(amount);
                if (newBalance.compareTo(BigDecimal.ZERO) < 0) {
                    throw new LackOfBalanceException("Not enough balance for this DEBIT");
                }
                legalEntity.setBalance(legalEntity.getBalance().subtract(amount));
            }
            case REFILL -> legalEntity.setBalance(legalEntity.getBalance().add(amount));
        }
        legalEntityRepository.save(legalEntity);
    }

    @Override
    public void deleteById(UUID id) {

    }

    @Override
    public LegalEntity assignClientToLegalEntity(UUID legalEntityId, UUID clientId) {
        return null;
    }

    @Override
    public List<LegalEntity> getAllLegalEntitiesByClientId(UUID clientId) {
        return null;
    }

    @Override
    public List<LegalEntity> getAll() {
        return null;
    }
}

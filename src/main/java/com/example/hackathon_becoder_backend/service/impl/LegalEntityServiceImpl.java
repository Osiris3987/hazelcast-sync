package com.example.hackathon_becoder_backend.service.impl;

import com.example.hackathon_becoder_backend.domain.client.Client;
import com.example.hackathon_becoder_backend.domain.enums.EntityStatus;
import com.example.hackathon_becoder_backend.domain.exception.LackOfBalanceException;
import com.example.hackathon_becoder_backend.domain.exception.ResourceNotFoundException;
import com.example.hackathon_becoder_backend.domain.legal_entity.LegalEntity;
import com.example.hackathon_becoder_backend.domain.transaction.Transaction;
import com.example.hackathon_becoder_backend.domain.transaction.TransactionType;
import com.example.hackathon_becoder_backend.repository.LegalEntityRepository;
import com.example.hackathon_becoder_backend.service.ClientService;
import com.example.hackathon_becoder_backend.service.LegalEntityService;
import com.example.hackathon_becoder_backend.web.dto.LegalEntityWithClientsDto;
import jakarta.persistence.OptimisticLockException;
import lombok.RequiredArgsConstructor;
import org.hibernate.StaleStateException;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LegalEntityServiceImpl implements LegalEntityService {
    private final LegalEntityRepository legalEntityRepository;
    private final ClientService clientService;
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
        LegalEntity legalEntity = findById(id);
        switch (type) {
            case DEBIT -> {
                BigDecimal newBalance = legalEntity.getBalance().subtract(amount);
                if (newBalance.compareTo(BigDecimal.ZERO) < 0) {
                    throw new LackOfBalanceException("Not enough balance for this DEBIT");
                }
                legalEntity.setBalance(legalEntity.getBalance().subtract(amount));
            }
            case REFILL -> {
                legalEntity.setBalance(legalEntity.getBalance().add(amount));
            }
        }
        legalEntityRepository.save(legalEntity);
    }

    @Override
    public void deleteById(UUID id) {
        LegalEntity legalEntity = findById(id);
        legalEntity.setStatus(String.valueOf(EntityStatus.DELETED));
        legalEntityRepository.save(legalEntity);
    }

    @Override
    public LegalEntity assignClientToLegalEntity(UUID legalEntityId, UUID clientId) {
        LegalEntity legalEntity = findById(legalEntityId);
        Client client = clientService.findById(clientId);
        legalEntity.getClients().add(client);
        legalEntityRepository.save(legalEntity);
        return legalEntity;
    }

    @Override
    public List<LegalEntity> getAllLegalEntitiesByClientId(UUID clientId) {
        List<LegalEntity> legalEntities = legalEntityRepository.findAllLegalEntitiesByClientId(clientId);
        return legalEntities;
    }

    @Override
    public List<LegalEntity> getAll() {
        return legalEntityRepository.findAll();
    }
}

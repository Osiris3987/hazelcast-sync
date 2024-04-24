package com.example.hackathon_becoder_backend.service.impl;

import com.example.hackathon_becoder_backend.domain.client.Client;
import com.example.hackathon_becoder_backend.domain.exception.ResourceNotFoundException;
import com.example.hackathon_becoder_backend.domain.exception.ValidationException;
import com.example.hackathon_becoder_backend.domain.legal_entity.LegalEntity;
import com.example.hackathon_becoder_backend.domain.legal_entity.LegalEntityStatus;
import com.example.hackathon_becoder_backend.domain.transaction.Transaction;
import com.example.hackathon_becoder_backend.repository.TransactionRepository;
import com.example.hackathon_becoder_backend.service.ClientService;
import com.example.hackathon_becoder_backend.service.LegalEntityService;
import com.example.hackathon_becoder_backend.service.TransactionService;
import com.example.hackathon_becoder_backend.util.LegalEntityValidator;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.coyote.BadRequestException;
import org.hibernate.StaleStateException;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {
    private final TransactionRepository transactionRepository;
    private final ClientService clientService;
    private final LegalEntityService legalEntityService;

    @Override
    @Retryable(maxAttempts = 20, retryFor = StaleStateException.class, noRetryFor = BadRequestException.class)
    @Transactional
    @SneakyThrows
    public Transaction create(Transaction transaction, UUID clientId, UUID legalEntityId) {
        Client client = clientService.findById(clientId);
        LegalEntity legalEntity = legalEntityService.findById(legalEntityId);
        //TODO fix it, doesn't work
        //if (LegalEntityStatus.valueOf(legalEntity.getStatus()) == LegalEntityStatus.DELETED) throw new BadRequestException();
        LegalEntityValidator.isClientInLegalEntity(client, legalEntityId);
        transaction.setClient(client);
        transaction.setLegalEntity(legalEntity);
        legalEntityService.changeBalance(
                legalEntityId,
                transaction.getAmount(),
                transaction.getType()
        );
        return transactionRepository.save(transaction);
    }

    @Override
    @Transactional(readOnly = true)
    public Transaction getById(UUID id) {
        return transactionRepository
                .findById(id)
                .orElseThrow(()
                        -> new ResourceNotFoundException("Requested transaction not found"));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Transaction> getAllByLegalEntityId(UUID legalEntityId) {
        return transactionRepository
                .findAllByLegalEntityId(legalEntityId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Transaction> getAllByClientId(UUID clientId) {
        return transactionRepository
                .findAllByClientId(clientId);
    }
}

package com.example.hackathon_becoder_backend.service.impl;

import com.example.hackathon_becoder_backend.domain.client.Client;
import com.example.hackathon_becoder_backend.domain.exception.ResourceNotFoundException;
import com.example.hackathon_becoder_backend.domain.legal_entity.LegalEntity;
import com.example.hackathon_becoder_backend.domain.transaction.Transaction;
import com.example.hackathon_becoder_backend.domain.transaction.TransactionType;
import com.example.hackathon_becoder_backend.repository.ClientRepository;
import com.example.hackathon_becoder_backend.repository.LegalEntityRepository;
import com.example.hackathon_becoder_backend.repository.TransactionRepository;
import com.example.hackathon_becoder_backend.service.ClientService;
import com.example.hackathon_becoder_backend.service.LegalEntityService;
import com.example.hackathon_becoder_backend.service.TransactionService;
import com.example.hackathon_becoder_backend.util.LegalEntityValidator;
import com.example.hackathon_becoder_backend.web.dto.TransactionDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {
    private final TransactionRepository transactionRepository;
    private final ClientService clientService;
    private final LegalEntityService legalEntityService;

    @Override
    @Transactional
    public Transaction create(Transaction transaction, UUID clientId, UUID legalEntityId) {
        Client client = clientService.findById(clientId);
        LegalEntity legalEntity = legalEntityService.findById(legalEntityId);
        if(!LegalEntityValidator.isClientInLegalEntity(client, legalEntityId)){
            throw new RuntimeException();
        }
        transaction.setClient(client);
        transaction.setLegalEntity(legalEntity);
        legalEntityService.changeBalance(
                legalEntityId,
                transaction.getAmount(),
                transaction.getType()
        );
        return transactionRepository.save(transaction);
    }
}

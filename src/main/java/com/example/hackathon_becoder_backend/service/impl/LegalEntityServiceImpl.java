package com.example.hackathon_becoder_backend.service.impl;

import com.example.hackathon_becoder_backend.domain.exception.ResourceNotFoundException;
import com.example.hackathon_becoder_backend.domain.legal_entity.LegalEntity;
import com.example.hackathon_becoder_backend.domain.transaction.Transaction;
import com.example.hackathon_becoder_backend.domain.transaction.TransactionType;
import com.example.hackathon_becoder_backend.repository.LegalEntityRepository;
import com.example.hackathon_becoder_backend.service.LegalEntityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LegalEntityServiceImpl implements LegalEntityService {
    private final LegalEntityRepository legalEntityRepository;
    @Override
    @Transactional(readOnly = true)
    public LegalEntity findById(UUID id) {
        return legalEntityRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Legal entity not found"));
    }

    @Override
    @Transactional
    public void changeBalance(UUID id, BigDecimal amount, TransactionType type) {
        switch (type){
            case DEBIT -> legalEntityRepository.decreaseBalance(amount, id.toString());
            case REFILL -> legalEntityRepository.increaseBalance(amount, id.toString());
        }
    }
}

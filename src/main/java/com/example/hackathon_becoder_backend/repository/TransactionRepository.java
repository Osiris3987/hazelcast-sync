package com.example.hackathon_becoder_backend.repository;

import com.example.hackathon_becoder_backend.domain.transaction.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface TransactionRepository extends JpaRepository<Transaction, UUID> {
    List<Transaction> findAllByLegalEntityId(UUID legalEntityId);

    List<Transaction> findAllByClientId(UUID clientId);
}

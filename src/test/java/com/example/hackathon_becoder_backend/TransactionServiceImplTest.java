package com.example.hackathon_becoder_backend;

import com.example.hackathon_becoder_backend.domain.client.Client;
import com.example.hackathon_becoder_backend.domain.legal_entity.LegalEntity;
import com.example.hackathon_becoder_backend.domain.transaction.Transaction;
import com.example.hackathon_becoder_backend.domain.transaction.TransactionType;
import com.example.hackathon_becoder_backend.repository.LegalEntityRepository;
import com.example.hackathon_becoder_backend.service.ClientService;
import com.example.hackathon_becoder_backend.service.LegalEntityService;
import com.example.hackathon_becoder_backend.service.TransactionService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.*;

@SpringBootTest
@Transactional
class TransactionServiceImplTest {
    @Autowired
    private LegalEntityService legalEntityService;

    @Test
    void testConcurrentTransactionsWithConsistentBalance() throws InterruptedException {
        // Create a legal entity
        LegalEntity legalEntity1 = legalEntityService.findById(UUID.fromString("b3ec6a4c-6245-419d-b884-024a69fea3ec"));

        ExecutorService executor1 = Executors.newFixedThreadPool(5);
        ExecutorService executor2 = Executors.newCachedThreadPool();

        List<Callable> list = new ArrayList<>();

        executor2.submit(() -> {legalEntityService.changeBalance(legalEntity1.getId(), BigDecimal.valueOf(1), TransactionType.REFILL);
        });


        /*
        for (int i = 0; i < 100; i++) {
            executor1.submit(() -> {
                legalEntityService.changeBalance(legalEntity1.getId(), BigDecimal.valueOf(1000), TransactionType.REFILL);
            });
            executor2.submit(() -> {
                legalEntityService.changeBalance(legalEntity1.getId(), BigDecimal.valueOf(1000), TransactionType.REFILL);
            });
        }
         */

        executor1.shutdown();
        executor2.shutdown();

        // Verify the final balance
        LegalEntity updatedLegalEntity = legalEntityService.findById(legalEntity1.getId());
    }
}

package com.example.hackathon_becoder_backend.service.impl;

import com.example.hackathon_becoder_backend.domain.client.Client;
import com.example.hackathon_becoder_backend.domain.legal_entity.LegalEntity;
import com.example.hackathon_becoder_backend.domain.transaction.Transaction;
import com.example.hackathon_becoder_backend.exception.ResourceNotFoundException;
import com.example.hackathon_becoder_backend.repository.TransactionRepository;
import com.example.hackathon_becoder_backend.service.ClientService;
import com.example.hackathon_becoder_backend.service.LegalEntityService;
import org.apache.coyote.BadRequestException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TransactionServiceImplTest {
    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private ClientService clientService;

    @Mock
    private LegalEntityService legalEntityService;

    @InjectMocks
    private TransactionServiceImpl transactionService;

    @Test
    void create_shouldCallSaveInRepository() {
        LegalEntity legalEntity = new LegalEntity();
        legalEntity.setClients(new HashSet<>());
        legalEntity.setStatus("EXISTS");
        Client client = new Client();
        client.setId(UUID.randomUUID());
        legalEntity.getClients().add(client);
        Transaction transaction = mock(Transaction.class);

        when(legalEntityService.findById(legalEntity.getId())).thenReturn(legalEntity);
        when(clientService.findById(client.getId())).thenReturn(client);
        when(transactionRepository.save(transaction)).thenReturn(transaction);
        Transaction actualTransaction = transactionService.create(transaction, client.getId(), legalEntity.getId());

        assertNotNull(actualTransaction);
        assertEquals(transaction, actualTransaction);
        verify(transactionRepository, times(1)).save(transaction);
        System.out.println(LocalDateTime.now().toLocalTime() + "[create_shouldCallSaveInRepository] passed!");
    }

    @Test
    void create_shouldCallChangeDebit() {
        LegalEntity legalEntity = new LegalEntity();
        legalEntity.setClients(new HashSet<>());
        legalEntity.setStatus("EXISTS");
        Client client = new Client();
        client.setId(UUID.randomUUID());
        legalEntity.getClients().add(client);
        Transaction transaction = new Transaction();

        when(legalEntityService.findById(legalEntity.getId())).thenReturn(legalEntity);
        when(clientService.findById(client.getId())).thenReturn(client);
        doNothing()
                .when(legalEntityService)
                .changeBalance(legalEntity.getId(), transaction.getAmount(), transaction.getType());
        transactionService.create(transaction, client.getId(), legalEntity.getId());

        verify(legalEntityService, times(1))
                .changeBalance(legalEntity.getId(), transaction.getAmount(), transaction.getType());
        System.out.println(LocalDateTime.now().toLocalTime() + "[create_shouldCallChangeDebit] passed!");
    }

    @Test
    void create_whenLegalEntityDoesNotExist_shouldThrowBadRequestException() {
        LegalEntity legalEntity = new LegalEntity();
        legalEntity.setClients(new HashSet<>());
        legalEntity.setStatus("DELETED");
        Client client = new Client();
        client.setId(UUID.randomUUID());
        legalEntity.getClients().add(client);
        Transaction transaction = mock(Transaction.class);

        when(legalEntityService.findById(legalEntity.getId())).thenReturn(legalEntity);
        when(clientService.findById(client.getId())).thenReturn(client);

        assertThrows(BadRequestException.class, ()
                -> transactionService.create(transaction, client.getId(), legalEntity.getId()));
        System.out.println(LocalDateTime.now().toLocalTime() + "[create_whenLegalEntityDoesNotExist_shouldThrowBadRequestException] passed!");
    }

    @Test
    void getById_shouldCallRepositoryAndReturnTransaction() {
        UUID transactionId = UUID.randomUUID();
        Transaction transaction = new Transaction();
        transaction.setId(transactionId);

        when(transactionRepository.findById(transactionId)).thenReturn(Optional.of(transaction));
        Transaction actualTransaction = transactionService.getById(transactionId);

        assertNotNull(actualTransaction);
        assertEquals(transaction, actualTransaction);
        verify(transactionRepository, times(1)).findById(transactionId);
        System.out.println(LocalDateTime.now().toLocalTime() + "[getById_shouldCallRepositoryAndReturnTransaction] passed!");
    }

    @Test
    void getById_whenNotFound_shouldThrowResourceNotFoundException() {
        UUID transactionId = UUID.randomUUID();

        when(transactionRepository.findById(transactionId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> transactionService.getById(transactionId));
    }

    @Test
    void findAllByLegalEntityId_shouldCallFindAllByLegalEntityIdInRepository() {
        UUID legalEntityId = UUID.randomUUID();

        when(transactionRepository.findAllByLegalEntityId(legalEntityId)).thenReturn(null);
        transactionService.getAllByLegalEntityId(legalEntityId);

        verify(transactionRepository, times(1)).findAllByLegalEntityId(legalEntityId);
        System.out.println(LocalDateTime.now().toLocalTime() + "[findAllByLegalEntityId_shouldCallFindAllByLegalEntityIdInRepository] passed!");
    }

    @Test
    void findAllByClientId_shouldCallFindAllByClientIdInRepository() {
        UUID clientId = UUID.randomUUID();

        when(transactionRepository.findAllByClientId(clientId)).thenReturn(null);
        transactionService.getAllByClientId(clientId);

        verify(transactionRepository, times(1)).findAllByClientId(clientId);
        System.out.println(LocalDateTime.now().toLocalTime() + "[findAllByClientId_shouldCallFindAllByClientIdInRepository] passed!");
    }
}
package com.example.hackathon_becoder_backend.web.controller;

import com.example.hackathon_becoder_backend.domain.client.Client;
import com.example.hackathon_becoder_backend.domain.legal_entity.LegalEntity;
import com.example.hackathon_becoder_backend.domain.transaction.Transaction;
import com.example.hackathon_becoder_backend.service.TransactionService;
import com.example.hackathon_becoder_backend.web.dto.transaction.TransactionDto;
import com.example.hackathon_becoder_backend.web.mapper.TransactionMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TransactionControllerTest {
    @Mock
    private TransactionService transactionService;

    @Mock
    private TransactionMapper transactionMapper;

    @InjectMocks
    private TransactionController transactionController;

    @Test
    void create() {
        TransactionDto transactionDto = mock(TransactionDto.class);
        Transaction transaction = mock(Transaction.class);
        Client client = new Client();
        LegalEntity legalEntity = new LegalEntity();
        UUID clientID = UUID.randomUUID();
        UUID legalEntityID = UUID.randomUUID();
        client.setId(clientID);
        legalEntity.setId(clientID);

        when(transactionMapper.toEntity(transactionDto)).thenReturn(transaction);
        when(transactionMapper.toDto(transaction)).thenReturn(transactionDto);
        when(transactionService.create(transaction, clientID, legalEntityID)).thenReturn(transaction);
        TransactionDto actualDto = transactionController.create(transactionDto, clientID, legalEntityID);

        verify(transactionMapper, times(1)).toDto(transaction);
        verify(transactionMapper, times(1)).toEntity(transactionDto);
        verify(transactionService, times(1)).create(transaction, clientID, legalEntityID);

        assertNotNull(actualDto);
        assertEquals(transactionDto, actualDto);
        System.out.println(LocalDateTime.now().toLocalTime() + "[create] passed!");
    }
}
package com.example.hackathon_becoder_backend.web.controller;

import com.example.hackathon_becoder_backend.domain.client.Client;
import com.example.hackathon_becoder_backend.domain.legal_entity.LegalEntity;
import com.example.hackathon_becoder_backend.domain.transaction.Transaction;
import com.example.hackathon_becoder_backend.service.LegalEntityService;
import com.example.hackathon_becoder_backend.service.TransactionService;
import com.example.hackathon_becoder_backend.web.dto.legalEntity.LegalEntityDto;
import com.example.hackathon_becoder_backend.web.dto.legalEntity.LegalEntityWithClientsDto;
import com.example.hackathon_becoder_backend.web.dto.transaction.TransactionDto;
import com.example.hackathon_becoder_backend.web.mapper.LegalEntityMapper;
import com.example.hackathon_becoder_backend.web.mapper.TransactionMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LegalEntityControllerTest {
    @Mock
    private LegalEntityService legalEntityService;

    @Mock
    private LegalEntityMapper legalEntityMapper;

    @Mock
    private TransactionService transactionService;

    @Mock
    private TransactionMapper transactionMapper;

    @InjectMocks
    private LegalEntityController legalEntityController;

    @Test
    void create() {
        LegalEntity legalEntity = mock(LegalEntity.class);
        LegalEntityDto legalEntityDto = mock(LegalEntityDto.class);
        UserDetails userDetails = mock(UserDetails.class);

        when(userDetails.getUsername()).thenReturn("username");
        when(legalEntityMapper.toDto(legalEntity)).thenReturn(legalEntityDto);
        when(legalEntityMapper.toEntity(legalEntityDto)).thenReturn(legalEntity);
        when(legalEntityService.create(legalEntity, userDetails.getUsername())).thenReturn(legalEntity);
        LegalEntityDto actualLegalEntityDto = legalEntityController.create(legalEntityDto, userDetails);

        assertNotNull(actualLegalEntityDto);
        assertEquals(legalEntityDto, actualLegalEntityDto);
        verify(legalEntityMapper, times(1)).toDto(legalEntity);
        verify(legalEntityMapper, times(1)).toEntity(legalEntityDto);
        verify(legalEntityService, times(1)).create(legalEntity, userDetails.getUsername());
        System.out.println(LocalDateTime.now().toLocalTime() + "[create] passed!");
    }

    @Test
    void deleteById() {
        UUID legalEntityId = UUID.randomUUID();

        doNothing().when(legalEntityService).deleteById(legalEntityId);
        legalEntityController.deleteById(legalEntityId);

        verify(legalEntityService, times(1)).deleteById(legalEntityId);
        System.out.println(LocalDateTime.now().toLocalTime() + "[deleteById] passed!");
    }

    @Test
    void getTransactionsByLegalEntityId() {
        UUID legalEntityId = UUID.randomUUID();
        Transaction transaction = mock(Transaction.class);
        TransactionDto transactionDto = mock(TransactionDto.class);
        List<Transaction> transactions = new ArrayList<>();
        List<TransactionDto> transactionDtos = new ArrayList<>();
        transactions.add(transaction);
        transactionDtos.add(transactionDto);

        when(transactionService.getAllByLegalEntityId(legalEntityId)).thenReturn(transactions);
        when(transactionMapper.toDtoList(transactions)).thenReturn(transactionDtos);
        List<TransactionDto> actualDtos = legalEntityController.getTransactionsByLegalEntityId(legalEntityId);

        assertNotNull(actualDtos);
        assertEquals(transactionDtos.size(), actualDtos.size());
        verify(transactionService, times(1)).getAllByLegalEntityId(legalEntityId);
        verify(transactionMapper, times(1)).toDtoList(transactions);
        System.out.println(LocalDateTime.now().toLocalTime() + "[getTransactionsByLegalEntityId] passed!");
    }

    @Test
    void getClientsByLegalEntityId() {
        UUID legalEntityId = UUID.randomUUID();
        LegalEntity legalEntity = mock(LegalEntity.class);
        LegalEntityWithClientsDto legalEntityWithClientsDto = mock(LegalEntityWithClientsDto.class);

        when(legalEntityMapper.toDtoWithClients(legalEntity)).thenReturn(legalEntityWithClientsDto);
        when(legalEntityService.findClientsByLegalEntityId(legalEntityId)).thenReturn(legalEntity);
        LegalEntityWithClientsDto actualDto = legalEntityController.getClientsByLegalEntityId(legalEntityId);

        assertNotNull(actualDto);
        assertEquals(legalEntityWithClientsDto, actualDto);
        verify(legalEntityMapper, times(1)).toDtoWithClients(legalEntity);
    }

    @Test
    void assign() {
        UUID clientId = UUID.randomUUID();
        UUID legalEntityId = UUID.randomUUID();
        Client client = new Client();
        LegalEntity legalEntity = new LegalEntity();
        client.setId(clientId);
        legalEntity.setId(clientId);
        LegalEntityWithClientsDto legalEntityWithClientsDto = new LegalEntityWithClientsDto();
        LegalEntityDto.AssignClientToLegalEntity params =
                new LegalEntityDto.AssignClientToLegalEntity(legalEntityId, clientId);

        when(legalEntityMapper.toDtoWithClients(legalEntity)).thenReturn(legalEntityWithClientsDto);
        when(legalEntityService.assignClientToLegalEntity(legalEntityId, clientId)).thenReturn(legalEntity);
        LegalEntityWithClientsDto actualDto = legalEntityController.assign(params);

        assertNotNull(actualDto);
        assertEquals(legalEntityWithClientsDto, actualDto);
        verify(legalEntityService, times(1)).assignClientToLegalEntity(legalEntityId, clientId);
        verify(legalEntityMapper, times(1)).toDtoWithClients(legalEntity);
        System.out.println(LocalDateTime.now().toLocalTime() + "[assign] passed!");
    }
}
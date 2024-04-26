package com.example.hackathon_becoder_backend.web.controller;

import com.example.hackathon_becoder_backend.domain.client.Client;
import com.example.hackathon_becoder_backend.domain.transaction.Transaction;
import com.example.hackathon_becoder_backend.service.ClientService;
import com.example.hackathon_becoder_backend.service.TransactionService;
import com.example.hackathon_becoder_backend.web.dto.client.ClientDto;
import com.example.hackathon_becoder_backend.web.dto.transaction.TransactionDto;
import com.example.hackathon_becoder_backend.web.mapper.ClientMapper;
import com.example.hackathon_becoder_backend.web.mapper.TransactionMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClientControllerTest {
    @Mock
    private ClientService clientService;

    @Mock
    private ClientMapper clientMapper;

    @Mock
    private TransactionService transactionService;

    @Mock
    private TransactionMapper transactionMapper;

    @InjectMocks
    private ClientController clientController;

    @Test
    void getAllClients() {
        Client client = mock(Client.class);
        ClientDto clientDto = mock(ClientDto.class);
        List<Client> clients = new ArrayList<>();
        List<ClientDto> clientDtos = new ArrayList<>();
        clients.add(client);
        clientDtos.add(clientDto);

        when(clientMapper.toDtoList(clients)).thenReturn(clientDtos);
        when(clientService.findAll()).thenReturn(clients);
        List<ClientDto> actualDtos = clientController.getAllClients();

        assertNotNull(actualDtos);
        assertEquals(clientDtos.size(), actualDtos.size());
        verify(clientService, times(1)).findAll();
        verify(clientMapper, times(1)).toDtoList(clients);
        System.out.println(LocalDateTime.now().toLocalTime() + "[getAllClients] passed!");
    }

    @Test
    void getClientById() {
        UUID clientId = UUID.randomUUID();
        Client client = mock(Client.class);
        ClientDto clientDto = mock(ClientDto.class);

        when(clientMapper.toDto(client)).thenReturn(clientDto);
        when(clientService.findById(clientId)).thenReturn(client);
        ClientDto actualDto = clientController.getClientById(clientId);

        assertNotNull(actualDto);
        assertEquals(clientDto, actualDto);
        verify(clientService, times(1)).findById(clientId);
        verify(clientMapper, times(1)).toDto(client);
        System.out.println(LocalDateTime.now().toLocalTime() + "[getClientById] passed!");
    }

    @Test
    void getTransactionsByClientId() {
        UUID clientId = UUID.randomUUID();
        Transaction transaction = mock(Transaction.class);
        TransactionDto transactionDto = mock(TransactionDto.class);
        List<Transaction> transactions = new ArrayList<>();
        List<TransactionDto> transactionDtos = new ArrayList<>();
        transactions.add(transaction);
        transactionDtos.add(transactionDto);

        when(transactionService.getAllByClientId(clientId)).thenReturn(transactions);
        when(transactionMapper.toDtoList(transactions)).thenReturn(transactionDtos);
        List<TransactionDto> actualDtos = clientController.getTransactionsByClientId(clientId);

        assertNotNull(actualDtos);
        assertEquals(transactionDtos.size(), actualDtos.size());
        verify(transactionService, times(1)).getAllByClientId(clientId);
        verify(transactionMapper, times(1)).toDtoList(transactions);
        System.out.println(LocalDateTime.now().toLocalTime() + "[getTransactionsByClientId] passed!");
    }

    @Test
    void deleteById() {
        UUID clientId = UUID.randomUUID();

        doNothing().when(clientService).deleteById(clientId);
        clientController.deleteById(clientId);

        verify(clientService, times(1)).deleteById(clientId);
        System.out.println(LocalDateTime.now().toLocalTime() + "[deleteById] passed!");
    }
}
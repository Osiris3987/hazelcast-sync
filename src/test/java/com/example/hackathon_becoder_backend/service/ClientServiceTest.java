package com.example.hackathon_becoder_backend.service;

import com.example.hackathon_becoder_backend.domain.client.Client;
import com.example.hackathon_becoder_backend.domain.client.ClientStatus;
import com.example.hackathon_becoder_backend.repository.ClientRepository;
import com.example.hackathon_becoder_backend.service.impl.ClientServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ClientServiceTest {

    @Mock
    private ClientRepository clientRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private ClientServiceImpl clientService;

    @Test
    void findById_shouldReturnClient() {
        UUID clientUUID = UUID.randomUUID();
        Client client = new Client();
        client.setId(clientUUID);
        client.setStatus(ClientStatus.EXISTS.name());

        when(clientRepository.findById(clientUUID)).thenReturn(Optional.of(client));
        Client recievedClient = clientService.findById(clientUUID);

        assertNotNull(recievedClient); //  Клиент был получен
        assertEquals(clientUUID, recievedClient.getId()); //  Верифицируем id полученного клиента
        verify(clientRepository, Mockito.times(1)).findById(clientUUID); //  Было единственное обращение к репозиторию
        System.out.println(LocalDateTime.now().toLocalTime() + "[findById_shouldReturnClient] passed!");
    }

    @Test
    void findAll_shouldReturnAllClients() {
        Client client1 = new Client();
        Client client2 = new Client();
        Client client3 = new Client();
        List<UUID> clientsUUIDs = List.of(UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID());
        client1.setId(clientsUUIDs.get(0));
        client2.setId(clientsUUIDs.get(1));
        client3.setId(clientsUUIDs.get(2));

        when(clientRepository.findAll()).thenReturn(List.of(client1, client2, client3));
        List<Client> receivedClients = clientService.findAll();

        assertNotNull(receivedClients); //  Клиенты были получены
        assertArrayEquals(clientsUUIDs.toArray(), receivedClients.stream().map(Client::getId).toArray()); //  Верифицируем значения
        verify(clientRepository, Mockito.times(1)).findAll();
        System.out.println(LocalDateTime.now().toLocalTime() + "[findAll_shouldReturnAllClients] passed!");
    }

    @Test
    void create_shouldInvokeSaveMethodOnceWithDelegatedClient() {
        Client client = new Client();
        client.setId(UUID.randomUUID());

        clientService.create(client);

        verify(clientRepository, Mockito.times(1)).save(client);
        System.out.println(LocalDateTime.now().toLocalTime() + "[create_shouldInvokeSaveMethodOnceWithDelegatedClient] passed!");
    }

    @Test
    void deleteById_shouldRemoveClientWithDelegatedId() {
        UUID clientUUID = UUID.randomUUID();
        Client client = new Client();
        client.setId(clientUUID);

        when(clientRepository.findById(clientUUID)).thenReturn(Optional.of(client));
        clientService.deleteById(clientUUID);

        assertEquals(ClientStatus.DELETED, ClientStatus.valueOf(client.getStatus())); //  Статус клиента DELETED
        verify(clientRepository, Mockito.times(1)).findById(clientUUID); //  Поиск клиента был выполнен единожды
        verify(clientRepository, Mockito.times(1)).save(client); //  Удаление конкретного клиента было выполнено единожды
        System.out.println(LocalDateTime.now().toLocalTime() + "[deleteById_shouldRemoveClientWithDelegatedId] passed!");
    }

    @Test
    void findByUserName_shouldReturnClientByName() {
        String clientName = "client";
        UUID clientUUID = UUID.randomUUID();
        Client client = new Client();
        client.setName(clientName);
        client.setId(clientUUID);

        when(clientRepository.findByUsername(clientName)).thenReturn(Optional.of(client));
        Client recievedClient = clientService.findByUsername(clientName);

        assertNotNull(recievedClient); //  Проверка, что результат был получен
        assertEquals(clientUUID, recievedClient.getId()); //  Убедимся, что получен ожидаемый клиент
        verify(clientRepository, Mockito.times(1)).findByUsername(clientName); //  Обращение к репозиторию было единожды
        System.out.println(LocalDateTime.now().toLocalTime() + "[findByUserName_shouldReturnClientByName] passed!");
    }

}

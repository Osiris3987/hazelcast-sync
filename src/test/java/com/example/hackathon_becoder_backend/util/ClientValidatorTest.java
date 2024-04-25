package com.example.hackathon_becoder_backend.util;

import com.example.hackathon_becoder_backend.domain.client.Client;
import com.example.hackathon_becoder_backend.exception.UserAlreadyExistException;
import com.example.hackathon_becoder_backend.repository.ClientRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClientValidatorTest {

    @Mock
    private ClientRepository clientRepository;

    @Test
    void assertClientNotExist_whenClientNotFound_shouldPass() {
        Client client = new Client();

        when(clientRepository.findByUsername(client.getUsername())).thenReturn(Optional.empty());

        assertDoesNotThrow(() ->
                ClientValidator.assertClientNotExist(clientRepository, client));
    }

    @Test
    void assertClientExist_whenClientExist_shouldThrowUserAlreadyExistException() {
        Client client = new Client();

        when(clientRepository.findByUsername(client.getUsername())).thenReturn(Optional.of(client));

        assertThrows(UserAlreadyExistException.class, () ->
                ClientValidator.assertClientNotExist(clientRepository, client)
        );
    }
}
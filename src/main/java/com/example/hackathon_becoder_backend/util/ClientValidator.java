package com.example.hackathon_becoder_backend.util;

import com.example.hackathon_becoder_backend.domain.client.Client;
import com.example.hackathon_becoder_backend.exception.UserAlreadyExistException;
import com.example.hackathon_becoder_backend.repository.ClientRepository;

public class ClientValidator {
    public static void assertClientNotExist(ClientRepository clientRepository, Client client) {
        if (clientRepository.findByUsername(client.getUsername()).isPresent()) {
            throw new UserAlreadyExistException("User already exists.");
        }
    }
}

package com.example.hackathon_becoder_backend.service;

import com.example.hackathon_becoder_backend.domain.client.Client;

import java.util.List;
import java.util.UUID;

public interface ClientService {
    Client findById(UUID id);

    Client findByUsername(String username);

    Client create(Client client);

    List<Client> findAll();

    void deleteById(UUID id);

}

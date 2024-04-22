package com.example.hackathon_becoder_backend.service;

import com.example.hackathon_becoder_backend.domain.client.Client;
import com.example.hackathon_becoder_backend.domain.legal_entity.LegalEntity;

import java.util.List;
import java.util.UUID;

public interface ClientService {
    Client findById(UUID id);
    Client create(Client client);
    List<Client> findAll();
    List<Client> getAllByLegalEntityId(UUID legalEntityID);
    void deleteById(UUID id);
}

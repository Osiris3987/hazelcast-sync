package com.example.hackathon_becoder_backend.service.impl;

import com.example.hackathon_becoder_backend.domain.client.Client;
import com.example.hackathon_becoder_backend.domain.exception.ResourceNotFoundException;
import com.example.hackathon_becoder_backend.repository.ClientRepository;
import com.example.hackathon_becoder_backend.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {
    private final ClientRepository clientRepository;
    @Override
    @Transactional(readOnly = true)
    public Client findById(UUID id) {
        return clientRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Client not found"));
    }

    @Override
    public List<Client> getAll() {
        return null;
    }

    @Override
    public Client getById() {
        return null;
    }

    @Override
    public List<Client> getAllByLegalEntityId(UUID legalEntityID) {
        return null;
    }

    @Override
    public Client create(Client client) {
        return null;
    }

    @Override
    public void deleteById(UUID id) {

    }
}

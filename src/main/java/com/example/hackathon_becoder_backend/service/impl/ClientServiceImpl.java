package com.example.hackathon_becoder_backend.service.impl;

import com.example.hackathon_becoder_backend.domain.client.Client;
import com.example.hackathon_becoder_backend.domain.enums.EntityStatus;
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
    private final ClientServiceImpl self;

    @Override
    @Transactional(readOnly = true)
    public Client findById(UUID id) {
        return clientRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Client not found"));
    }

    @Override
    public List<Client> findAll() {
        return clientRepository.findAll();
    }

    @Override
    public Client create(Client client) {
        client.setStatus(EntityStatus.EXISTS.name());
        return clientRepository.save(client);
    }

    @Override
    public void deleteById(UUID id) {
        var client = self.findById(id);
        client.setStatus(EntityStatus.DELETED.name());
        clientRepository.save(client);
    }
}

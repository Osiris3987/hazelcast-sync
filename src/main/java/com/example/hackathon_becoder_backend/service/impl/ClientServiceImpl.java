package com.example.hackathon_becoder_backend.service.impl;

import com.example.hackathon_becoder_backend.domain.client.Client;
import com.example.hackathon_becoder_backend.domain.client.ClientStatus;
import com.example.hackathon_becoder_backend.domain.client.Role;
import com.example.hackathon_becoder_backend.domain.exception.ResourceNotFoundException;
import com.example.hackathon_becoder_backend.repository.ClientRepository;
import com.example.hackathon_becoder_backend.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {
    private final ClientRepository clientRepository;

    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional(readOnly = true)
    public Client findById(UUID id) {
        return clientRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Client not found"));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Client> findAll() {
        return clientRepository.findAll();
    }

    @Override
    @Transactional
    public Client create(Client client) {
        if (clientRepository.findByUsername(client.getUsername()).isPresent()) {
            throw new IllegalStateException("User already exists.");
        }
        client.setPassword(passwordEncoder.encode(client.getPassword()));
        client.setStatus(ClientStatus.EXISTS.name());
        Set<Role> roles = Set.of(Role.USER);
        client.setRoles(roles);
        return clientRepository.save(client);
    }

    @Override
    @Transactional
    public void deleteById(UUID id) {
        var client = findById(id);
        client.setStatus(ClientStatus.DELETED.name());
        clientRepository.save(client);
    }

    @Override
    public Client findByUsername(String username) {
        return clientRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("Client not found"));
    }
}

package com.example.hackathon_becoder_backend.service;

import com.example.hackathon_becoder_backend.domain.client.Client;

import java.util.UUID;

public interface ClientService {
    Client findById(UUID id);
}

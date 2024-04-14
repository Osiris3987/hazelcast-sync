package com.example.hackathon_becoder_backend.repository;

import com.example.hackathon_becoder_backend.domain.client.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ClientRepository extends JpaRepository<Client, UUID> {
}

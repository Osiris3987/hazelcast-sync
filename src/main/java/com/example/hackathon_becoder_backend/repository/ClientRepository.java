package com.example.hackathon_becoder_backend.repository;

import com.example.hackathon_becoder_backend.domain.client.Client;
import com.example.hackathon_becoder_backend.domain.legal_entity.LegalEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ClientRepository extends JpaRepository<Client, UUID> {

     List<Client> getAllByLegalEntity(LegalEntity legalEntity);
}

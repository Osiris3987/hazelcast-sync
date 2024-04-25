package com.example.hackathon_becoder_backend.repository;

import com.example.hackathon_becoder_backend.domain.client.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ClientRepository extends JpaRepository<Client, UUID> {

    @Query("SELECT c FROM Client c JOIN c.legalEntities le WHERE le.id = :legalEntityId")
    List<Client> findByLegalEntityId(UUID legalEntityId);

    Optional<Client> findByUsername(String username);
}

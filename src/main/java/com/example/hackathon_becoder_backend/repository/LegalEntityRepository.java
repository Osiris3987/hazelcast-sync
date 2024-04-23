package com.example.hackathon_becoder_backend.repository;

import com.example.hackathon_becoder_backend.domain.legal_entity.LegalEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface LegalEntityRepository extends JpaRepository<LegalEntity, UUID> {
    @Query("SELECT le FROM LegalEntity le JOIN le.clients c WHERE c.id = :clientID")
    List<LegalEntity> findAllLegalEntitiesByClientId(UUID clientID);
}

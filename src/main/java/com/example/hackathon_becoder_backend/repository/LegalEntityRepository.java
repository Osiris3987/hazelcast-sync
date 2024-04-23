package com.example.hackathon_becoder_backend.repository;

import com.example.hackathon_becoder_backend.domain.legal_entity.LegalEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface LegalEntityRepository extends JpaRepository<LegalEntity, UUID> {

}

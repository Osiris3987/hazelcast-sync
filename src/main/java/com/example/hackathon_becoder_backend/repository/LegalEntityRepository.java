package com.example.hackathon_becoder_backend.repository;

import com.example.hackathon_becoder_backend.domain.legal_entity.LegalEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.UUID;

public interface LegalEntityRepository extends JpaRepository<LegalEntity, UUID> {

    @Modifying
    @Query(
            value = "UPDATE legal_entity SET balance = balance + :amount WHERE id = :id",
            nativeQuery = true
    )
    void increaseBalance(@Param("amount") BigDecimal amount, @Param("id") String id);

    @Modifying
    @Query(
            value = "UPDATE legal_entity SET balance = balance - :amount WHERE id = :id",
            nativeQuery = true
    )
    void decreaseBalance(@Param("amount") BigDecimal amount, @Param("id") String id);
}

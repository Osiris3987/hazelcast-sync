package com.example.hackathon_becoder_backend.domain.client;

import com.example.hackathon_becoder_backend.domain.legal_entity.LegalEntity;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "client")
@Data
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    @Column(name = "id")
    private UUID id;

    @Column(name = "name")
    private String name;


    @ManyToMany
    @JoinTable(name = "legal_entity_clients",
            joinColumns = @JoinColumn(name = "client_id"),
            inverseJoinColumns = @JoinColumn(name = "legal_entity_id"))
    Set<LegalEntity> legalEntities;
}

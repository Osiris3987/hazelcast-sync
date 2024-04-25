package com.example.hackathon_becoder_backend.domain.client;

import com.example.hackathon_becoder_backend.domain.legal_entity.LegalEntity;
import jakarta.persistence.*;
import lombok.*;
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
    @Getter
    @Setter
    private UUID id;

    @Column(name = "name")
    private String name;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "status")
    private String status;

    @Column(name = "role")
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "clients_roles")
    @Enumerated(value = EnumType.STRING)
    private Set<Role> roles;

    @ManyToMany
    @JoinTable(name = "legal_entity_clients",
            joinColumns = @JoinColumn(name = "client_id"),
            inverseJoinColumns = @JoinColumn(name = "legal_entity_id"))
    @EqualsAndHashCode.Exclude
    private Set<LegalEntity> legalEntities;
}

package com.example.hackathon_becoder_backend.util;

import com.example.hackathon_becoder_backend.domain.client.Client;

import java.util.UUID;

public class LegalEntityValidator {
    private LegalEntityValidator() {
    }

    public static boolean isClientInLegalEntity(Client client, UUID legalEntityId) {
        return client.getLegalEntities().stream()
                .anyMatch(legalEntity -> legalEntity.getId().equals(legalEntityId));
    }
}

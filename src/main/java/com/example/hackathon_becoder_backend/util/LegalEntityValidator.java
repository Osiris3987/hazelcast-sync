package com.example.hackathon_becoder_backend.util;

import com.example.hackathon_becoder_backend.domain.client.Client;
import com.example.hackathon_becoder_backend.exception.ResourceNotFoundException;

import java.util.UUID;

public class LegalEntityValidator {
    private LegalEntityValidator() {
    }

    public static void isClientInLegalEntity(Client client, UUID legalEntityId) {
        boolean clientConsistsInLegalEntity = client.getLegalEntities().stream()
                .anyMatch(legalEntity ->
                        legalEntity.getId()
                        .equals(legalEntityId)
                );
        if(!clientConsistsInLegalEntity) throw new ResourceNotFoundException("Client is not in this legal entity");
    }
}

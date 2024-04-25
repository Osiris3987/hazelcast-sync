package com.example.hackathon_becoder_backend.util;

import com.example.hackathon_becoder_backend.domain.client.Client;
import com.example.hackathon_becoder_backend.domain.exception.ResourceNotFoundException;
import com.example.hackathon_becoder_backend.domain.legal_entity.LegalEntity;
import com.example.hackathon_becoder_backend.domain.legal_entity.LegalEntityStatus;
import org.apache.coyote.BadRequestException;

public class LegalEntityValidator {
    private LegalEntityValidator() {
    }

    public static void isClientInLegalEntity(Client client, LegalEntity legalEntity) {
        boolean clientConsistsInLegalEntity = legalEntity
                .getClients()
                .stream()
                .anyMatch(client::equals);
        if(!clientConsistsInLegalEntity) throw new ResourceNotFoundException("Client is not in this legal entity");
    }


    public static void assertLegalEntityExists(LegalEntity legalEntity) throws BadRequestException {
        if (LegalEntityStatus.valueOf(legalEntity.getStatus()) == LegalEntityStatus.DELETED)
            throw new BadRequestException("Legal entity status is DELETED");
    }
}

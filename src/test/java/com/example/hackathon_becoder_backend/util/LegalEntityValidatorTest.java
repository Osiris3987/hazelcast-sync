package com.example.hackathon_becoder_backend.util;

import com.example.hackathon_becoder_backend.domain.client.Client;
import com.example.hackathon_becoder_backend.exception.ResourceNotFoundException;
import com.example.hackathon_becoder_backend.domain.legal_entity.LegalEntity;
import org.apache.coyote.BadRequestException;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class LegalEntityValidatorTest {
    @Test
    void isClientInLegalEntity_onClientPresentInLegalEntity_shouldPass() {
        LegalEntity legalEntity = new LegalEntity();
        Client client = new Client();
        client.setId(UUID.randomUUID());
        legalEntity.setClients(new HashSet<>());
        legalEntity.getClients().add(client);

        assertDoesNotThrow(() ->
                LegalEntityValidator.isClientInLegalEntity(client, legalEntity));
        System.out.println(LocalDateTime.now().toLocalTime() + "[isClientInLegalEntity_onClientPresentInLegalEntity_shouldPass] passed!");
    }

    @Test
    void isClientInLegalEntity_onClientAbsent_shouldThrowException() {
        LegalEntity legalEntity = new LegalEntity();
        Client client = new Client();
        legalEntity.setClients(new HashSet<>());

        assertThrows(ResourceNotFoundException.class, () ->
                LegalEntityValidator.isClientInLegalEntity(client, legalEntity));
        System.out.println(LocalDateTime.now().toLocalTime() + "[isClientInLegalEntity_onClientAbsent_shouldThrowException] passed!");
    }

    @Test
    void assertLegalEntityPresent_onExistsLegalEntity_shouldPass() {
        LegalEntity legalEntity = new LegalEntity();
        legalEntity.setStatus("EXISTS");

        assertDoesNotThrow(() -> LegalEntityValidator.assertLegalEntityExists(legalEntity));
        System.out.println(LocalDateTime.now().toLocalTime() + "[assertLegalEntityPresent_onExistsLegalEntity_shouldPass] passed!");
    }

    @Test
    void assertLegalEntityPresent_onRemovedLegalEntity_shouldThrowBadRequestException() {
        LegalEntity legalEntity = new LegalEntity();
        legalEntity.setStatus("DELETED");

        assertThrows(BadRequestException.class, () ->
                LegalEntityValidator.assertLegalEntityExists(legalEntity));
        System.out.println(LocalDateTime.now().toLocalTime() + "[assertLegalEntityPresent_onRemovedLegalEntity_shouldThrowBadRequestException] passed!");
    }
}
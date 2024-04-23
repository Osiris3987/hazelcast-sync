package com.example.hackathon_becoder_backend;

import com.example.hackathon_becoder_backend.domain.legal_entity.LegalEntity;
import com.example.hackathon_becoder_backend.service.LegalEntityService;
import com.example.hackathon_becoder_backend.service.impl.LegalEntityServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class LegalEntityServiceImplTest {
    @Autowired
    LegalEntityService legalEntityService;

    @Test
    void deleteById() {
        final UUID testId = UUID.fromString("b3ec6a4c-6245-419d-b884-024a69fea3eb");
        legalEntityService.deleteById(testId);
        assertEquals(legalEntityService.findById(testId).getStatus(), "DELETED");
        System.out.println("Test passed!");
    }

    @Test
    void getAllLegalEntitiesByClientId(){
        final UUID testId = UUID.fromString("f0caf844-5a61-43a7-b1c2-e66971f5e08a");
        List<LegalEntity> legalEntities = legalEntityService.getAllLegalEntitiesByClientId(testId);
        for (var legalEntity: legalEntities) {
            System.out.println(legalEntity.getId());
        }
    }
}
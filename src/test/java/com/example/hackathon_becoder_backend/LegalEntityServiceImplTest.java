package com.example.hackathon_becoder_backend;

import com.example.hackathon_becoder_backend.service.LegalEntityService;
import com.example.hackathon_becoder_backend.service.impl.LegalEntityServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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
}
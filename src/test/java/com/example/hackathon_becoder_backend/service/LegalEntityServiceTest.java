package com.example.hackathon_becoder_backend.service;

import com.example.hackathon_becoder_backend.domain.client.ClientStatus;
import com.example.hackathon_becoder_backend.exception.LackOfBalanceException;
import com.example.hackathon_becoder_backend.domain.legal_entity.LegalEntity;
import com.example.hackathon_becoder_backend.domain.legal_entity.LegalEntityStatus;
import com.example.hackathon_becoder_backend.domain.transaction.TransactionType;
import com.example.hackathon_becoder_backend.repository.LegalEntityRepository;
import com.example.hackathon_becoder_backend.service.impl.ClientServiceImpl;
import com.example.hackathon_becoder_backend.service.impl.LegalEntityServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.Semaphore;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@Transactional
class LegalEntityServiceTest {
    @Mock
    LegalEntityRepository legalEntityRepository;

    @Mock
    ClientServiceImpl clientService;

    @InjectMocks
    LegalEntityServiceImpl legalEntityService;

    @Test
    void create_shouldCreateNewLegalEntityWithDelegatedOwner(){
        String ownerName = "owner";
        LegalEntity legalEntity = new LegalEntity();

        legalEntityService.create(legalEntity, ownerName);

        assertEquals(ownerName, legalEntity.getOwner()); //  Для юр.лица установлен владелец
        assertEquals(LegalEntityStatus.EXISTS.name(), legalEntity.getStatus()); //  Статус юр. лица EXISTS
        verify(legalEntityRepository, Mockito.times(1)).save(legalEntity); //  Единственное обращение к репозиторию
    }

    @Test
    void findById_shouldReturnLegalEntityWithDelegatedID(){
        UUID legalEntityUUID = UUID.randomUUID();
        LegalEntity legalEntity = new LegalEntity();
        legalEntity.setId(legalEntityUUID);

        when(legalEntityRepository.findById(legalEntityUUID)).thenReturn(Optional.of(legalEntity));
        LegalEntity receivedLegalEntity = legalEntityService.findById(legalEntityUUID);

        assertNotNull(receivedLegalEntity); //  Юр. лицо было получено
        assertEquals(legalEntityUUID, receivedLegalEntity.getId());
        verify(legalEntityRepository, Mockito.times(1)).findById(legalEntityUUID);
        System.out.println(LocalDateTime.now().toLocalTime() + "[findById_shouldReturnLegalEntityWithDelegatedID] passed!");
    }

    @Test
    void changeBalance_shouldDebitAndRefillDelegatedLegalEntityBalance(){
        BigDecimal amount = new BigDecimal(1000);
        BigDecimal basicBalance = new BigDecimal(10000);
        UUID legalEntityUUID = UUID.randomUUID();
        LegalEntity legalEntity = new LegalEntity();
        legalEntity.setBalance(basicBalance);
        legalEntity.setId(legalEntityUUID);

        when(legalEntityRepository.findById(legalEntityUUID)).thenReturn(Optional.of(legalEntity));
        legalEntityService.changeBalance(legalEntityUUID, amount, TransactionType.DEBIT);
        legalEntityService.changeBalance(legalEntityUUID, amount, TransactionType.REFILL);
        legalEntityService.changeBalance(legalEntityUUID, amount, TransactionType.REFILL);
        try {
            legalEntityService.changeBalance(legalEntityUUID, amount.multiply(BigDecimal.valueOf(12)), TransactionType.DEBIT);
            fail();
        } catch (LackOfBalanceException lackOfBalanceException){}

        assertEquals(BigDecimal.valueOf(11000), legalEntity.getBalance()); //  Баланс соответствует ожидаемому значению
        verify(legalEntityRepository, Mockito.times(4)).findById(legalEntityUUID); //  Поиск по id был выполнен 3 раза
        verify(legalEntityRepository, Mockito.times(3)).save(legalEntity); //  Сохранение в БД было выполнено 3 раза
        System.out.println(LocalDateTime.now().toLocalTime() + "[changeBalance_shouldDebitAndRefillDelegatedLegalEntityBalance] passed!");
    }

    @Test
    void deleteById_shouldRemoveLegalEntityWithDelegatedId(){
        UUID clientUUID = UUID.randomUUID();
        LegalEntity legalEntity = new LegalEntity();
        legalEntity.setId(clientUUID);

        when(legalEntityRepository.findById(clientUUID)).thenReturn(Optional.of(legalEntity));
        legalEntityService.deleteById(clientUUID);

        assertEquals(LegalEntityStatus.DELETED, LegalEntityStatus.valueOf(legalEntity.getStatus())); //  Статус клиента DELETED
        verify(legalEntityRepository, Mockito.times(1)).findById(clientUUID); //  Поиск клиента был выполнен единожды
        verify(legalEntityRepository, Mockito.times(1)).save(legalEntity); //  Удаление конкретного клиента было выполнено единожды
        System.out.println(LocalDateTime.now().toLocalTime() + "[deleteById_shouldRemoveLegalEntityWithDelegatedId] passed!");
    }



}
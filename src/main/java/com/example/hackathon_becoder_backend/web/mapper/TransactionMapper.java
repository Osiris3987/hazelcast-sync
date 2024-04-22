package com.example.hackathon_becoder_backend.web.mapper;

import com.example.hackathon_becoder_backend.domain.transaction.Transaction;
import com.example.hackathon_becoder_backend.web.dto.TransactionDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TransactionMapper {
    @Mapping(source = "transaction.legalEntity.id", target = "legalEntityId")
    @Mapping(source = "transaction.client.id", target = "clientId")
    TransactionDto toDto(Transaction transaction);
    Transaction toEntity(TransactionDto dto);
    List<TransactionDto> toDtoList(List<Transaction> transactions);
}

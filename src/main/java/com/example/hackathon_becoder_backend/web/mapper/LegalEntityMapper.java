package com.example.hackathon_becoder_backend.web.mapper;

import com.example.hackathon_becoder_backend.domain.legal_entity.LegalEntity;
import com.example.hackathon_becoder_backend.web.dto.LegalEntityDto;
import com.example.hackathon_becoder_backend.web.dto.LegalEntityWithClientsDto;
import org.mapstruct.Mapper;
import org.mapstruct.MapperConfig;

import java.util.List;

@Mapper(componentModel = "spring", uses = ClientMapper.class)
public interface LegalEntityMapper {
    LegalEntityDto toDto(LegalEntity legalEntity);

    LegalEntityWithClientsDto toDtoWithClients(LegalEntity legalEntity);

    LegalEntity toEntityWithClients(LegalEntityWithClientsDto legalEntityWithClientsDto);

    LegalEntity toEntity(LegalEntityDto legalEntityDto);

    List<LegalEntityDto> toDtoList(List<LegalEntity> legalEntityList);
}

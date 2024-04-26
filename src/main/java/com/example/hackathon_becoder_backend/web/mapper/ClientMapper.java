package com.example.hackathon_becoder_backend.web.mapper;

import com.example.hackathon_becoder_backend.domain.client.Client;
import com.example.hackathon_becoder_backend.web.dto.client.ClientDto;
import com.example.hackathon_becoder_backend.web.dto.client.ClientWithLegalEntitiesDto;
import com.example.hackathon_becoder_backend.web.dto.legalEntity.LegalEntityDto;
import com.example.hackathon_becoder_backend.web.dto.legalEntity.LegalEntityWithClientsDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface ClientMapper {

    @Mapping(source = "status", target = "status")
    ClientDto toDto(Client client);

    Client toEntity(ClientDto dto);
    default ClientWithLegalEntitiesDto toClientWithLegalEntitiesDto(Client client) {
        ClientWithLegalEntitiesDto dto = new ClientWithLegalEntitiesDto();
        dto.setId(client.getId());
        dto.setName(client.getName());
        dto.setUsername(client.getUsername());
        dto.setStatus(dto.getStatus());
        dto.setLegalEntities(client.getLegalEntities().stream().map(e -> {
            LegalEntityDto legalEntityDto = new LegalEntityDto();
            legalEntityDto.setId(e.getId());
            legalEntityDto.setBalance(e.getBalance());
            legalEntityDto.setName(e.getName());
            return legalEntityDto;
        }).collect(Collectors.toSet()));
        return dto;
    }

    List<ClientDto> toDtoList(List<Client> list);

    ClientWithLegalEntitiesDto toDtoWithLegalEntities(Client client);
}

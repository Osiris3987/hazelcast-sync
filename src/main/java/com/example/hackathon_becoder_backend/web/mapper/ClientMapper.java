package com.example.hackathon_becoder_backend.web.mapper;

import com.example.hackathon_becoder_backend.domain.client.Client;
import com.example.hackathon_becoder_backend.web.dto.client.ClientDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ClientMapper {

    ClientDto toDto(Client client);
    Client toEntity(ClientDto dto);
    List<ClientDto> toDtoList(List<Client> list);
}

package com.example.hackathon_becoder_backend.web.mapper;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = ClientMapper.class)
public interface LegalEntityMapper {

}

package com.example.hackathon_becoder_backend.web.controller;


import com.example.hackathon_becoder_backend.service.ClientService;
import com.example.hackathon_becoder_backend.web.dto.client.ClientDto;
import com.example.hackathon_becoder_backend.web.mapper.ClientMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/client")
public class ClientController {
    private final ClientService clientService;
    private final ClientMapper clientMapper;

    @GetMapping("/all")
    public List<ClientDto> getAllClients(){
        return clientMapper.toDtoList(clientService.findAll());
    }

    @GetMapping("/allByLegalEntityId")
    public List<ClientDto> getAllClientsByLegalEntityId(
            @RequestParam UUID legalEntityID
    ){
        return clientMapper.toDtoList(clientService.findAllByLegalEntityId(legalEntityID));
    }
    @GetMapping("")
    public List<ClientDto> getClientById(
            @RequestParam UUID clientId
    ){
        return clientMapper.toDtoList(clientService.findAllByLegalEntityId(clientId));
    }
    @DeleteMapping("")
    public ResponseEntity<String> deleteById(
            @RequestParam UUID clientId
    ){
        clientService.deleteById(clientId);
        return ResponseEntity.noContent().build();
    }
}

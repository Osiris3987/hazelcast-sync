package com.example.hackathon_becoder_backend.web.controller;


import com.example.hackathon_becoder_backend.service.ClientService;
import com.example.hackathon_becoder_backend.web.dto.client.ClientDto;
import com.example.hackathon_becoder_backend.web.mapper.ClientMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/client")
public class ClientController {
    private final ClientService clientService;
    private final ClientMapper clientMapper;

    @GetMapping("")
    public List<ClientDto> getAllClients(){
        return clientMapper.toDtoList(clientService.findAll());
    }
}

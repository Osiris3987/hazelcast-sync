package com.example.hackathon_becoder_backend.web.controller;

import com.example.hackathon_becoder_backend.domain.client.Client;
import com.example.hackathon_becoder_backend.service.AuthService;
import com.example.hackathon_becoder_backend.service.ClientService;
import com.example.hackathon_becoder_backend.web.dto.auth.JwtRequest;
import com.example.hackathon_becoder_backend.web.dto.auth.JwtResponse;
import com.example.hackathon_becoder_backend.web.dto.client.ClientDto;
import com.example.hackathon_becoder_backend.web.mapper.ClientMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthControllerTest {
    @Mock
    private AuthService authService;

    @Mock
    private ClientService clientService;

    @Mock
    private ClientMapper clientMapper;

    @InjectMocks
    private AuthController authController;

    @Test
    void login() {
        JwtRequest jwtRequest = mock(JwtRequest.class);
        JwtResponse jwtResponse = mock(JwtResponse.class);

        when(authService.login(jwtRequest)).thenReturn(jwtResponse);
        JwtResponse actualResponse = authController.login(jwtRequest);

        assertNotNull(actualResponse);
        assertEquals(jwtResponse, actualResponse);
        verify(authService, times(1)).login(jwtRequest);
        System.out.println(LocalDateTime.now().toLocalTime() + "[login] passed!");
    }

    @Test
    void register() {
        Client client = mock(Client.class);
        ClientDto clientDto = mock(ClientDto.class);

        when(clientMapper.toDto(client)).thenReturn(clientDto);
        when(clientMapper.toEntity(clientDto)).thenReturn(client);
        when(clientService.create(client)).thenReturn(client);
        ClientDto actualDto = authController.register(clientDto);

        assertNotNull(actualDto);
        assertEquals(clientDto, actualDto);
        verify(clientMapper, times(1)).toEntity(clientDto);
        verify(clientMapper, times(1)).toDto(client);
        verify(clientService, times(1)).create(client);
        System.out.println(LocalDateTime.now().toLocalTime() + "[register] passed!");
    }

    @Test
    void refresh() {
        String s = String.valueOf(System.currentTimeMillis());
        JwtResponse jwtResponse = mock(JwtResponse.class);

        when(authService.refresh(s)).thenReturn(jwtResponse);
        JwtResponse actualResponse = authController.refresh(s);

        assertNotNull(actualResponse);
        assertEquals(jwtResponse, actualResponse);
        verify(authService, times(1)).refresh(s);
        System.out.println(LocalDateTime.now().toLocalTime() + "[refresh] passed!");
    }
}
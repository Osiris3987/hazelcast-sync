package com.example.hackathon_becoder_backend.service.impl;

import com.example.hackathon_becoder_backend.domain.client.Client;
import com.example.hackathon_becoder_backend.service.AuthService;
import com.example.hackathon_becoder_backend.service.ClientService;
import com.example.hackathon_becoder_backend.web.dto.auth.JwtRequest;
import com.example.hackathon_becoder_backend.web.dto.auth.JwtResponse;
import com.example.hackathon_becoder_backend.web.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final ClientService clientService;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public JwtResponse login(
            final JwtRequest loginRequest
    ) {
        JwtResponse jwtResponse = new JwtResponse();
        Client client = clientService.findByUsername(loginRequest.getUsername());
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(), loginRequest.getPassword())
        );
        jwtResponse.setId(client.getId());
        jwtResponse.setUsername(client.getUsername());
        jwtResponse.setAccessToken(jwtTokenProvider.createAccessToken(
                client.getId(), client.getUsername(), client.getRoles())
        );
        jwtResponse.setRefreshToken(jwtTokenProvider.createRefreshToken(
                client.getId(), client.getUsername())
        );
        return jwtResponse;
    }

    @Override
    public JwtResponse refresh(
            final String refreshToken
    ) {
        return jwtTokenProvider.refreshUserTokens(refreshToken);
    }
}

package com.example.hackathon_becoder_backend.service.impl;

import com.example.hackathon_becoder_backend.domain.client.Client;
import com.example.hackathon_becoder_backend.domain.client.Role;
import com.example.hackathon_becoder_backend.exception.ResourceNotFoundException;
import com.example.hackathon_becoder_backend.service.impl.AuthServiceImpl;
import com.example.hackathon_becoder_backend.service.impl.ClientServiceImpl;
import com.example.hackathon_becoder_backend.web.dto.auth.JwtRequest;
import com.example.hackathon_becoder_backend.web.dto.auth.JwtResponse;
import com.example.hackathon_becoder_backend.web.security.JwtTokenProvider;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthServiceImplTest {
    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private ClientServiceImpl clientService;

    @Mock
    private JwtTokenProvider jwtTokenProvider;

    @InjectMocks
    private AuthServiceImpl authService;

    @Test
    void login_shouldAuthorizeUserAndReturnJwtResponseWithToken(){
        UUID clientUUID = UUID.randomUUID();
        String username = "username";
        String password = "password";
        Set<Role> roles = Collections.emptySet();
        String accessToken = "accessToken";
        String refreshToken = "refreshToken";
        JwtRequest request = new JwtRequest();
        request.setUsername(username);
        request.setPassword(password);
        Client user = new Client();
        user.setId(clientUUID);
        user.setUsername(username);
        user.setRoles(roles);

        when(clientService.findByUsername(username))
                .thenReturn(user);
        when(jwtTokenProvider.createAccessToken(clientUUID, username, roles))
                .thenReturn(accessToken);
        when(jwtTokenProvider.createRefreshToken(clientUUID, username))
                .thenReturn(refreshToken);
        JwtResponse response = authService.login(request);
        verify(authenticationManager)
                .authenticate(
                        new UsernamePasswordAuthenticationToken(
                                request.getUsername(),
                                request.getPassword())
                );

        assertEquals(response.getUsername(), username);
        assertEquals(response.getId(), clientUUID);
        assertNotNull(response.getAccessToken());
        assertNotNull(response.getRefreshToken());
        System.out.println(LocalDateTime.now().toLocalTime() + "[login_shouldAuthorizeUserAndReturnJwtResponseWithToken] passed!");
    }

    @Test
    void login_shouldRejectUserAuthorizationDueToIncorrectUserName(){
        String username = "username";
        String password = "password";
        JwtRequest request = new JwtRequest();
        request.setUsername(username);
        request.setPassword(password);
        Client client = new Client();
        client.setUsername(username);

        when(clientService.findByUsername(username))
                .thenThrow(ResourceNotFoundException.class);

        verifyNoInteractions(jwtTokenProvider);
        assertThrows(ResourceNotFoundException.class,
                () -> authService.login(request));
        System.out.println(LocalDateTime.now().toLocalTime() + "[login_shouldRejectUserAuthorizationDueToIncorrectUserName] passed!");
    }

    @Test
    void refresh_shouldRefreshClientToken(){
        String refreshToken = "refreshToken";
        String accessToken = "accessToken";
        String newRefreshToken = "newRefreshToken";
        JwtResponse response = new JwtResponse();
        response.setAccessToken(accessToken);
        response.setRefreshToken(newRefreshToken);

        when(jwtTokenProvider.refreshUserTokens(refreshToken))
                .thenReturn(response);
        JwtResponse testResponse = authService.refresh(refreshToken);

        verify(jwtTokenProvider).refreshUserTokens(refreshToken);
        assertEquals(testResponse, response);
        System.out.println(LocalDateTime.now().toLocalTime() + "[refresh_shouldRefreshClientToken] passed!");
    }

}

package com.example.hackathon_becoder_backend.principal;

import org.springframework.security.core.Authentication;

public interface IAuthenticationFacade {
    Authentication getAuthentication();
    String getAuthName();
}
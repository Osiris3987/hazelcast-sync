package com.example.hackathon_becoder_backend.service;

import com.example.hackathon_becoder_backend.web.dto.auth.JwtRequest;
import com.example.hackathon_becoder_backend.web.dto.auth.JwtResponse;

public interface AuthService {
    JwtResponse login(JwtRequest loginRequest);

    JwtResponse refresh(String refreshToken);


}

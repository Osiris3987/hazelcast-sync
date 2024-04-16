package com.example.hackathon_becoder_backend.domain.exception;

public class LackOfBalanceException extends RuntimeException {
    public LackOfBalanceException(String message) {
        super(message);
    }
}

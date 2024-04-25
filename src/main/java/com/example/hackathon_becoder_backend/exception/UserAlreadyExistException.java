package com.example.hackathon_becoder_backend.exception;

public class UserAlreadyExistException extends RuntimeException {
    public UserAlreadyExistException(String message) { super(message); }
}

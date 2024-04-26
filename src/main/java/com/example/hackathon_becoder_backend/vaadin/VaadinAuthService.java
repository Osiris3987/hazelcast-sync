package com.example.hackathon_becoder_backend.vaadin;

import com.example.hackathon_becoder_backend.domain.client.Client;
import com.example.hackathon_becoder_backend.service.ClientService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@Component
@RequiredArgsConstructor
public class VaadinAuthService {
    private boolean isAuth;
    private Client client;
    private final ClientService clientService;

    public void logOut(){
        isAuth=false;
    }
    public void signIn(){
        isAuth=true;
    }

    public void setClientByUsername(String username) {
        this.client=clientService.findByUsername(username);
    }
}

package com.neto.smart_money.controller;

import com.neto.smart_money.domain.entities.client.Client;
import com.neto.smart_money.dto.ClientResponseDTO;
import com.neto.smart_money.dto.LoginRequestDTO;
import com.neto.smart_money.dto.RegisterRequestDTO;
import com.neto.smart_money.exceptions.custom.EmailDuplicateException;
import com.neto.smart_money.repositories.ClientRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    private ClientRepository repository;

    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private PasswordEncoder encoder;

    @PostMapping("/login")
    public ResponseEntity<ClientResponseDTO> login(@RequestBody @Valid LoginRequestDTO data){

        var usernamePassword = new UsernamePasswordAuthenticationToken(data.email(), data.password());
        var auth = this.manager.authenticate(usernamePassword);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody @Valid RegisterRequestDTO data){
        if (repository.existsByEmail(data.email())){
            throw new EmailDuplicateException("Email already exists!");
        }

        String encryptedPassword = encoder.encode(data.password());

        Client newClient = new Client();
        newClient.setName(data.name());
        newClient.setEmail(data.email());
        newClient.setPassword(encryptedPassword);
        newClient.setRole(data.role());

        this.repository.save(newClient);


        return ResponseEntity.status(HttpStatus.CREATED).body("User registered with success!");
    }
}

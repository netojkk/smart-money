package com.neto.smart_money.controller;

import com.neto.smart_money.domain.entities.client.Client;
import com.neto.smart_money.dto.ClientResponseDTO;
import com.neto.smart_money.dto.LoginRequestDTO;
import com.neto.smart_money.dto.RegisterRequestDTO;
import com.neto.smart_money.dto.UpdateClientDTO;
import com.neto.smart_money.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/clients")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @PostMapping("/register")
    public ResponseEntity<ClientResponseDTO> create(@RequestBody RegisterRequestDTO body) {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.clientService.createClient(body));

    }
    @PostMapping("/login")
    public ResponseEntity<ClientResponseDTO> login(@RequestBody LoginRequestDTO body){
        return ResponseEntity.ok(this.clientService.loginClient(body));
    }

    @GetMapping
    public ResponseEntity<List<ClientResponseDTO>> getAll(){
        List<ClientResponseDTO> clients = clientService.getAllClients();
        return ResponseEntity.ok(clients);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientResponseDTO> getById(@PathVariable UUID id){
        return ResponseEntity.ok(clientService.getClientById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClientResponseDTO> editById(@PathVariable UUID id, @RequestBody UpdateClientDTO body){
        return ResponseEntity.ok(clientService.editClientById(id,body));
    }

    @DeleteMapping("/id")
    public ResponseEntity<Void> deleteById(@PathVariable UUID id){
        clientService.deleteClient(id);
        //Because there's nothing to return.
        return ResponseEntity.noContent().build();
    }
}

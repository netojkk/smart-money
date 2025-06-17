package com.neto.smart_money.controller;

import com.neto.smart_money.domain.entities.client.Client;
import com.neto.smart_money.domain.entities.client.ClientRequestDTO;
import com.neto.smart_money.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/register")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @PostMapping
    public ResponseEntity<Client> create(@RequestBody ClientRequestDTO body) {
        Client newClient = this.clientService.createClient(body);
        return ResponseEntity.ok(newClient);
    }
}

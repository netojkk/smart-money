package com.neto.smart_money.services;

import com.neto.smart_money.domain.entities.client.Client;
import com.neto.smart_money.domain.entities.client.ClientRequestDTO;
import com.neto.smart_money.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClientService {

    @Autowired
    private ClientRepository repository;

    public Client createClient(ClientRequestDTO data){
        Client newClient = new Client();
        newClient.setName(data.name());
        newClient.setEmail(data.email());
        newClient.setPassword(data.password());

        repository.save(newClient);
        return newClient;
    }
}

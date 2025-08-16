package com.neto.smart_money.services;

import com.neto.smart_money.domain.entities.client.Client;
import com.neto.smart_money.dto.ClientResponseDTO;
import com.neto.smart_money.dto.UpdateClientDTO;
import com.neto.smart_money.exceptions.custom.UserNotFoundException;
import com.neto.smart_money.repositories.ClientRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ClientService {

    @Autowired
    private ClientRepository repository;

    public Client getAuthenticated(){
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return repository.findByEmail(email).orElseThrow(() -> new UserNotFoundException("User Not Found"));
    }
    public List<ClientResponseDTO> getAllClients() {
        return repository.findAll()
                .stream()
                .map(client -> new ClientResponseDTO(client.getId(), client.getName(), client.getEmail()))
                .toList();

    }

    public ClientResponseDTO getClientById(UUID id){
        Client client = this.repository.findById(id)
                        .orElseThrow(() -> new UserNotFoundException("Client Not Found"));

        return new ClientResponseDTO(client.getId(), client.getName(), client.getEmail());

    }

    @Transactional
    public ClientResponseDTO editClientById(UpdateClientDTO data){
        Client client = getAuthenticated();

        client.setName(data.name() != null ? data.name() : client.getName());
        client.setEmail(data.email() != null ? data.email() : client.getEmail());

        repository.save(client);

        return new ClientResponseDTO(client.getId(), client.getName(), client.getEmail());
    }

    @Transactional
    public void deleteClient(){
        Client client = getAuthenticated();
        repository.deleteById(client.getId());
    }
}

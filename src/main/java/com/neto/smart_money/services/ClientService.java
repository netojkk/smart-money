package com.neto.smart_money.services;

import com.neto.smart_money.domain.entities.client.Client;
import com.neto.smart_money.dto.ClientResponseDTO;
import com.neto.smart_money.dto.LoginRequestDTO;
import com.neto.smart_money.dto.RegisterRequestDTO;
import com.neto.smart_money.dto.UpdateClientDTO;
import com.neto.smart_money.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ClientService {

    @Autowired
    private ClientRepository repository;


    public ClientResponseDTO createClient(RegisterRequestDTO data){
        if(this.repository.findByEmail(data.email()).isPresent()) {
            throw new RuntimeException("Email já cadastrado");
        }
        Client client = new Client(data);
        repository.save(client);
        return new ClientResponseDTO(client.getId(), client.getName(), client.getEmail());

    }

    public ClientResponseDTO loginClient(LoginRequestDTO data){
        Client client = this.repository.findByEmail(data.email()).orElseThrow(() -> new RuntimeException("User Not Found"));
        if (!data.password().equals(client.getPassword())){
            throw new RuntimeException("Incorrect Password");
        }
        return new ClientResponseDTO(client.getId(), client.getName(), client.getEmail());
    }

    public List<ClientResponseDTO> getAllClients() {
        return repository.findAll()
                .stream()
                .map(client -> new ClientResponseDTO(client.getId(), client.getName(), client.getEmail()))
                .toList();

    }

    public ClientResponseDTO getClientById(UUID id){
        Client client = this.repository.findById(id)
                        .orElseThrow(() -> new RuntimeException("Client Not Found"));

        return new ClientResponseDTO(client.getId(), client.getName(), client.getEmail());

    }

    public ClientResponseDTO editClientById(UUID id, UpdateClientDTO data){
        Client client = this.repository.findById(id).orElseThrow(() -> new RuntimeException("User Not found!"));

        client.setName(data.name() != null ? data.name() : client.getName());
        client.setEmail(data.email() != null ? data.email() : client.getEmail());

        repository.save(client);

        return new ClientResponseDTO(client.getId(), client.getName(), client.getEmail());
    }

    public void deleteClient(UUID id){
        Client client = this.repository.findById(id).orElseThrow(() -> new RuntimeException("User Not found!"));
        repository.deleteById(id);
    }
}

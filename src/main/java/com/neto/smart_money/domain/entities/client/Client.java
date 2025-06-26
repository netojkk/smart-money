package com.neto.smart_money.domain.entities.client;

import com.neto.smart_money.dto.ClientResponseDTO;
import com.neto.smart_money.dto.LoginRequestDTO;
import com.neto.smart_money.dto.RegisterRequestDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "client")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;

    private String name;
    private String email;
    private String password;

    public Client(LoginRequestDTO data) {
        this.email = data.email();
        this.password = data.password();
    }

    public Client(RegisterRequestDTO data) {
        this.name = data.name();
        this.email = data.email();
        this.password = data.password();
    }

    public Client(UUID id, ClientResponseDTO data) {
        this.id = data.id();
        this.name = data.name();
        this.email = data.email();
    }
}

package com.neto.smart_money.repositories;

import com.neto.smart_money.domain.entities.client.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;
import java.util.UUID;

public interface ClientRepository extends JpaRepository<Client, UUID> {
    Optional<Client> findByEmail(String email);
    Boolean existsByEmail(String email);
}

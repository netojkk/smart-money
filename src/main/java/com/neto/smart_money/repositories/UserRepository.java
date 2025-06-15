package com.neto.smart_money.repositories;

import com.neto.smart_money.domain.entities.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
}

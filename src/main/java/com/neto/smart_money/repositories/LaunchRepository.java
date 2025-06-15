package com.neto.smart_money.repositories;

import com.neto.smart_money.domain.entities.lauch.Launch;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface LaunchRepository extends JpaRepository<Launch, UUID> {
}

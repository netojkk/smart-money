package com.neto.smart_money.repositories;

import com.neto.smart_money.domain.entities.lauch.Launch;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface LaunchRepository extends JpaRepository<Launch, UUID> {
    boolean existsByAccountIdAndDateAndDescriptionAndAmount(
            UUID accountId,
            LocalDate transactionDate,
            String description,
            BigDecimal amount
    );

    List<Launch> findByClientId(UUID clientId);
}

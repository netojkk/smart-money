package com.neto.smart_money.dto;

import com.neto.smart_money.domain.enums.CategoryType;
import com.neto.smart_money.domain.enums.LaunchStatus;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;


public record LaunchResponseDTO(
        UUID id,
        BigDecimal amount,
        LocalDate date,
        CategoryType type,
        LaunchStatus status) {
}

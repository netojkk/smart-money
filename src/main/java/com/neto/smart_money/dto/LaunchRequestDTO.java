package com.neto.smart_money.dto;

import com.neto.smart_money.domain.enums.CategoryType;
import com.neto.smart_money.domain.enums.LaunchStatus;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;

@Valid
public record LaunchRequestDTO(
        @NotBlank String description,
        @NotNull BigDecimal amount,
        @NotNull LocalDate date,
        @NotNull CategoryType type,
        @NotNull LaunchStatus status) {
}

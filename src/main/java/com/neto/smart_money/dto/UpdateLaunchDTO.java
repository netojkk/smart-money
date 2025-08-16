package com.neto.smart_money.dto;

import com.neto.smart_money.domain.enums.CategoryType;
import com.neto.smart_money.domain.enums.SetStatus;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record UpdateLaunchDTO(
        String description,
        BigDecimal amount,
        LocalDate transactiondate,
        CategoryType type,
        SetStatus status,
        UUID account,
        UUID category) {
}

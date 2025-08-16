package com.neto.smart_money.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

import java.math.BigDecimal;

@Valid
public record TransactionDTO(@NotBlank BigDecimal balance) {
}

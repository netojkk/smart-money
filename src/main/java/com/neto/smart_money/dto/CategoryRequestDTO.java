package com.neto.smart_money.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;

@Valid
public record CategoryRequestDTO(
        @NotBlank String name,
        @NotBlank String type,
        @NotNull UUID clientId) {
}

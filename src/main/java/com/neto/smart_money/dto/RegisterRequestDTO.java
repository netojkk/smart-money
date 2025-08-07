package com.neto.smart_money.dto;

import com.neto.smart_money.domain.enums.ClientRole;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Valid
public record RegisterRequestDTO(
        @NotBlank String name,
        @NotBlank String email,
        @NotBlank String password,
        @NotNull ClientRole role
) {
}

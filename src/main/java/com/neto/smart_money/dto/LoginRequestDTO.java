package com.neto.smart_money.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

@Valid
public record LoginRequestDTO (
        @NotBlank String email,
        @NotBlank String password
        ){
}

package com.neto.smart_money.dto;

public record RegisterRequestDTO(
        String name,
        String email,
        String password
) {
}

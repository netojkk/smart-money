package com.neto.smart_money.dto;

import com.neto.smart_money.domain.enums.CategoryType;

import java.util.UUID;

public record CategoryResponseDTO(UUID id, String name, CategoryType type) {
}

package com.neto.smart_money.dto;

import com.neto.smart_money.domain.enums.CategoryType;

public record UpdateCategoryDTO(String name, CategoryType type) {
}

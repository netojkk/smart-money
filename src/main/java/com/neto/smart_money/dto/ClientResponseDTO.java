package com.neto.smart_money.dto;

import java.util.UUID;

public record ClientResponseDTO(UUID id, String name, String email) {
}

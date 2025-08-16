package com.neto.smart_money.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record AccountRequestDTO (String name, BigDecimal balance) {
}

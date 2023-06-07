package it.bitprojects.store.dto;

import java.math.BigDecimal;

public record Product(int id, String name, BigDecimal price, String description) {
}

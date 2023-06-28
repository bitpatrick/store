package it.bitprojects.store.dto;

import java.math.BigDecimal;

import it.bitprojects.store.model.Category;

public record ProductDto(String name, Category category, BigDecimal price) {
}

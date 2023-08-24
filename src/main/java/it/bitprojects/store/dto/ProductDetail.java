package it.bitprojects.store.dto;

import java.math.BigDecimal;

import it.bitprojects.store.model.Category;

public record ProductDetail(String name, Category category, BigDecimal price,String description) {
}

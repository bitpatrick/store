package it.bitprojects.store.dto;

import java.math.BigDecimal;

import it.bitprojects.store.model.Category;

public record ProductInStockDto(int id, String name, Category category, BigDecimal price, int qty) {
}

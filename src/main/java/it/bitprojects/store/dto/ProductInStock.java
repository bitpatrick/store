package it.bitprojects.store.dto;

import java.math.BigDecimal;

import it.bitprojects.store.model.Category;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public final class ProductInStock {

	private final int id;
	private final String name;
	private final Category category;
	private final BigDecimal price;
	private final int qty;
	
}

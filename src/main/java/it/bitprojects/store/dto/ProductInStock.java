package it.bitprojects.store.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public final class ProductInStock {

	private final String category;
	private final int qty;

}

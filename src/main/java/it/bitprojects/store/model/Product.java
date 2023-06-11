package it.bitprojects.store.model;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Product {
	
	private Integer id;
	private String name;
	private Category category;
	private BigDecimal price;

}

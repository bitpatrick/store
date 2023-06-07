package it.bitprojects.store.model;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Order {

	private String productName;
	private int qty;
	private BigDecimal price;

}

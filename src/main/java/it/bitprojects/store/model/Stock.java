package it.bitprojects.store.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Stock {
	
	private String productName;
	private int productQty;

}

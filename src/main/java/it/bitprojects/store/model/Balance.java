package it.bitprojects.store.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Balance {

	private Currency currency;
	private double quantity;

	public void increment(Currency currencyEnum, Integer quantity2) {
		
		

	}

	
	
	public double getEURQuantity() {

		
		
		return 0;
	}

}
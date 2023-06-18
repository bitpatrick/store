package it.bitprojects.store.model;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order {

	private Integer id;
	private Integer qty;
	private BigDecimal price;
	

}

package it.bitprojects.store.model;

import java.util.HashMap;
import java.util.Map;

import it.bitprojects.store.dto.ProductDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class Cart {

	private Map<ProductDto, Integer> products = new HashMap<>(); // prodotti nel carrello

//	public Object[][] productInCarts() {
//
//		Object[][] array = products.entrySet().stream()
//				.map(entry -> new Object[] { generateProductDto(entry.getKey()), entry.getValue() })
//				.toArray(Object[][]::new);
//
//		return array;
//	}

//	private ProductDto generateProductDto(Product product) {
//
//		return new ProductDto(product.getId(), product.getName(), product.getCategory(), product.getPrice());
//	}

}

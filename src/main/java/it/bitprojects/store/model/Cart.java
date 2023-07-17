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
	public void removeProduct(Integer idProduct, Integer qtyToRemove) {
		ProductDto product = getProductById(idProduct);
		Integer currentQty = getQtyByProduct(product);
		if (currentQty >= qtyToRemove) {
			remove(product, currentQty, qtyToRemove);
		} else {
			throw new RuntimeException("Impossibile rimuovere una quantità maggiore di quella che si possiede");
		}

	}

	private void remove(ProductDto product, Integer currentQty, Integer qtyToRemove) {
		Integer newQty = currentQty - qtyToRemove;
		if (newQty == 0) {
			products.remove(product);
		}
		products.put(product, newQty);

	}

	private ProductDto getProductById(Integer idProduct) {

		for (ProductDto product : products.keySet()) {
			if (product.id() == idProduct) {

				return product;

			}

		}
		throw new RuntimeException("Prodotto non presente nel carello " + idProduct);

	}

	public Integer getQtyByProduct(ProductDto product) {
		if (products.containsKey(product)) {
			return products.get(product);
		}

		throw new RuntimeException("Prodotto non presente nel carello " + product.id());
	}

}

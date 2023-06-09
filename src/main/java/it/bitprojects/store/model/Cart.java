package it.bitprojects.store.model;

import java.util.ArrayList;
import java.util.List;

import it.bitprojects.store.dto.ProductInCart;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class Cart {

	private List<ProductInCart> products = new ArrayList<>(); // prodotti nel carrello

}

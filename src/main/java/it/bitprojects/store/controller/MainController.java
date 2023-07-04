package it.bitprojects.store.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import it.bitprojects.store.dto.ProductDto;
import it.bitprojects.store.model.Cart;
import it.bitprojects.store.service.StoreService;
import jakarta.inject.Provider;

@Controller
public class MainController {

	@Autowired
	private Provider<Cart> cartProvider;

	@Autowired
	private StoreService store;

//	@GetMapping("/login")
//	public String login() {
//
//		return "login";
//	}

	/**
	 * HOME
	 * 
	 * @param model
	 * @return
	 */
	@GetMapping("/home")
	public String home(Model model) {

		// recupero prodotti tramite il service
		List<ProductDto> products = store.getAllProducts();

		// aggiungo i prodotti al modello
		model.addAttribute("products", products);

		// il modello verrà reindirizzato alla view
		return "home";
	}

	@GetMapping("/purchase")
	public String purchase() {

		return "purchase";
	}

	@PostMapping(value = "/cart-addProduct/{idProduct}")
	public String addProduct(@PathVariable("idProduct") Integer idProduct, @RequestBody MultiValueMap<String, String> formParams) {

		String qty = formParams.getFirst("quantity");

		// convert strings to integers
		int qtyInt = Integer.parseInt(qty);

		// recupero carrello
		Cart cart = this.cartProvider.get();

		// verificare giacenza magazzino
		this.store.addProductToCart(idProduct, qtyInt, cart);

		// restituisci la lista
		return "redirect:/home";
	}

	@ModelAttribute("cart")
	public Cart getCart() {
		return cartProvider.get();
	}

}

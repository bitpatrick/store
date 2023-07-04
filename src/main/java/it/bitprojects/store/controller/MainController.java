package it.bitprojects.store.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
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

	@PostMapping(value = "/addProduct", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public String addProduct(@RequestBody MultiValueMap<String, String> formParams) {
		
		String idProduct = formParams.getFirst("idProduct");
	    String qty = formParams.getFirst("qty");

	    // convert strings to integers
	    int idProductInt = Integer.parseInt(idProduct);
	    int qtyInt = Integer.parseInt(qty);

	    // recupero carrello
	    Cart cart = this.cartProvider.get();
	    
	    //verificare giacenza magazzino
	    this.store.addProductToCart(idProductInt, qtyInt, cart);

	    // restituisci la lista
		return "home";
	}

	@ModelAttribute("cart")
	public Cart getCart() {
		return cartProvider.get();
	}

}

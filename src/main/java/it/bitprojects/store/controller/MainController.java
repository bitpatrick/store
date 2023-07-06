package it.bitprojects.store.controller;

import java.time.LocalDate;
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
import it.bitprojects.store.dto.ProductInCart;
import it.bitprojects.store.model.Cart;
import it.bitprojects.store.service.StoreService;
import jakarta.inject.Provider;

@Controller
public class MainController {

	@Autowired
	private Provider<Cart> cartProvider;

	@Autowired
	private StoreService store;

	@GetMapping("/home")
	public String home(Model model) {
		
		// recupero carrello
		Cart cart = this.cartProvider.get();

		// recupero prodotti tramite il service
		List<ProductDto> products = store.getAllProducts();
		
		// recupero prodotti nel carrello dell'utente
		List<ProductInCart> productsInCart = cart.getProducts();

		// aggiungo i prodotti al modello
		model.addAttribute("products", products);
		model.addAttribute("cart", productsInCart);

		// il modello verrà reindirizzato alla view
		return "home";
	}

	@GetMapping("/purchase")
	public String purchase() {

		return "purchase";
	}

	@PostMapping(value = "/cart-addProduct/{idProduct}")
	public String addProduct(
			@PathVariable("idProduct") Integer idProduct,
			@RequestBody MultiValueMap<String, String> formParams, 
			Model model
			) {

		int qty = Integer.parseInt(formParams.getFirst("quantity"));

		// recupero carrello
		Cart cart = this.cartProvider.get();
		
		LocalDate date = (LocalDate) model.getAttribute("date");
		System.out.println("data aggiunta carello: " + date);

		// aggiungi il prodotto al carrello con la relativa quantità
		this.store.addProductToCart(idProduct, qty, cart);

		// ritorna alla home
		return "redirect:/home";
	}

	/**
	 * Questo metodo viene richiamato prima che venga eseguito qualsiasi handler
	 * method della classe
	 * 
	 * @return
	 */
	@ModelAttribute("date")
	public LocalDate getDate() {
		return LocalDate.now();
	}

}

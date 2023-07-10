package it.bitprojects.store.controller;

import static org.springframework.http.MediaType.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

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

	@GetMapping("/home")
	public String home(Model model) {

		// recupero carrello
		Cart cart = this.cartProvider.get();

		// recupero prodotti tramite il service
		List<ProductDto> products = store.getAllProducts();

		// recupero prodotti nel carrello dell'utente
		Map<ProductDto, Integer> productsInCart = cart.getProducts();

		// aggiungo i prodotti al modello
		model.addAttribute("products", products);
		model.addAttribute("productsInCart", productsInCart);

		// il modello verrà reindirizzato alla view
		return "home";
	}

	@GetMapping("/purchase")
	public String purchase() {

		return "purchase";
	}

	@GetMapping(value = "/cart-addProduct/{idProduct}", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public void addProduct(@PathVariable("idProduct") Integer idProduct, @RequestParam("qty") Integer qty) {

		// recupero carrello
		Cart cart = this.cartProvider.get();

		// aggiungi il prodotto al carrello con la relativa quantità
		this.store.addProductToCart(idProduct, qty, cart);
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

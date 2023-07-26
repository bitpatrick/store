package it.bitprojects.store.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.nimbusds.jose.proc.SecurityContext;

import it.bitprojects.store.dto.ProductDto;
import it.bitprojects.store.model.Cart;
import it.bitprojects.store.service.ServiceProva;
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

	@GetMapping("/stampa")
	@ResponseStatus(HttpStatus.OK)
	public void stampa() {

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
	 * metodo per rimuovere un prodotto dal carello(quando faccio click sul pulsante
	 * meno)
	 * 
	 * @param idProduct
	 * @param qty
	 */

	@GetMapping(value = "/cart-removeProduct/{idProduct}", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public void removeProduct(@PathVariable("idProduct") Integer idProduct, @RequestParam("qty") Integer qty) {

		// recupero carrello
		Cart cart = this.cartProvider.get();

		// rimuovo la quantita e nel caso il prodotto dal carello
		this.store.removeProductToCart(idProduct, qty, cart);
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

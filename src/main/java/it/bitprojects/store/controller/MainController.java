package it.bitprojects.store.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import it.bitprojects.store.dto.ProductDto;
import it.bitprojects.store.service.StoreService;

@Controller
public class MainController {
	
	@Autowired
	private StoreService store;

//	@GetMapping("/login")
//	public String login() {
//
//		return "login";
//	}

	@GetMapping("/home")
	public String home(Model model) {
		
		// recupero prodotti tramite il service
		List<ProductDto> products = store.getAllProducts();
		
		// aggiungo i prodotti al modello
		model.addAttribute("products", products);
		
		return "home";
	}
	
	@GetMapping("/purchase")
    public String purchase() {
		
        return "purchase";
    }

}

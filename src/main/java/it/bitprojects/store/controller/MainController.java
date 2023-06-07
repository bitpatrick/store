package it.bitprojects.store.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

	@GetMapping(path = "/order")
	public String test(Model model) {

		return "orderPage";
	}

}

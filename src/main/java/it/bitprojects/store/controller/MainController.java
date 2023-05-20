package it.bitprojects.store.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

	@GetMapping(path = "/test")
	public String test(Model model) {

		model.addAttribute("nome", "Patrizio");

		return "test";
	}
}

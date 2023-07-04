package it.bitprojects.store.controller;

import org.springframework.web.bind.annotation.ExceptionHandler;

import it.bitprojects.store.exceptions.ProductNotAvailableException;

@org.springframework.web.bind.annotation.ControllerAdvice
public class ControllerAdvice {

	@ExceptionHandler
	public String handleException(ProductNotAvailableException ex) {
		return "/home";
	}
}

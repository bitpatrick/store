package it.bitprojects.store.controller;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

import it.bitprojects.store.exceptions.ProductNotAvailableException;

@org.springframework.web.bind.annotation.ControllerAdvice
public class ControllerAdvice {

	@ExceptionHandler
	public String handleException(ProductNotAvailableException ex) {
		return "/home";
	}

	 @ExceptionHandler(IOException.class)
	    public ResponseEntity<String> handleIOException(IOException ex) {
	        String errorMessage = "Errore durante la lettura/scrittura del file.";
	        return new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
	    }
}

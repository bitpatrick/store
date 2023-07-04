package it.bitprojects.store.exceptions;

public class ProductNotAvailableException extends Exception {

	public ProductNotAvailableException() {
		super("Product not available");
	}
}
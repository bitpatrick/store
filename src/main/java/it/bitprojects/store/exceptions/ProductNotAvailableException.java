package it.bitprojects.store.exceptions;

public class ProductNotAvailableException extends RuntimeException {

	public ProductNotAvailableException() {
		super("Product not available");
	}
}
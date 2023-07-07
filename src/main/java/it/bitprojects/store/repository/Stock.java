package it.bitprojects.store.repository;

import java.util.List;
import java.util.Map;

import it.bitprojects.store.dto.ProductInStock;
import it.bitprojects.store.model.Product;

public interface Stock {
	
	public <T> T getById(int id, Class<T> type);
	
	public <T> List<T> getAll(Class<T> type);
	
	public List<ProductInStock> getAllProductsInStock();
	
	/**
	 * This method creates an order and returns an id
	 * 
	 * @param products
	 * @return Number
	 */
	public Number createOrder(Map<Product, Integer> products);
	
	public void updateQty(int idProduct, int qty);

}

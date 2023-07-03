package it.bitprojects.store.service;

import java.nio.file.Path;
import java.util.List;

import org.springframework.core.io.Resource;

import it.bitprojects.store.dto.ProductDto;
import it.bitprojects.store.dto.ProductInCart;
import it.bitprojects.store.dto.Purchase;
import it.bitprojects.store.model.Cart;
import net.sf.jasperreports.engine.JRException;

public interface StoreService {

	public Number purchase(Purchase purchase);
	
	public Resource generateReportOfProductsInStock(String backgroundImage, Path output) throws JRException;
	
	public List<ProductDto> getAllProducts();



	public void addProductToCart(int idProduct, int qty, Cart cart);

}

package it.bitprojects.store.service;

import java.nio.file.Path;
import java.util.List;

import javax.money.NumberValue;

import org.springframework.core.io.Resource;

import it.bitprojects.store.dto.BalanceDto;
import it.bitprojects.store.dto.ProductDto;
import it.bitprojects.store.dto.ProductInStockDto;
import it.bitprojects.store.dto.Purchase;
import it.bitprojects.store.model.Cart;
import net.sf.jasperreports.engine.JRException;

public interface StoreService {

	public Number purchase(Purchase purchase);

	public Resource generateReportOfProductsInStock(String backgroundImage, Path output) throws JRException;
	
	public List<ProductDto> getAllProducts();

	public void addProductToCart(int idProduct, int qty, Cart cart);

	public void removeProductToCart(Integer idProduct, Integer qty, Cart cart);

	public List<ProductInStockDto> getProductsInStock();

	public int getNumeroFile();

	public void saveFile(String fileName);

	public BalanceDto getBalance();

	public NumberValue getTokensPrice(double tokenQty);

	}

package it.bitprojects.store.service;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import it.bitprojects.store.dto.ProductDto;
import it.bitprojects.store.dto.ProductInStock;
import it.bitprojects.store.dto.ProductInStockDto;
import it.bitprojects.store.dto.Purchase;
import it.bitprojects.store.model.Cart;
import it.bitprojects.store.model.Product;
import it.bitprojects.store.repository.Warehouse;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Service
public class Maximo implements StoreService {

	@Autowired
	private Warehouse warehouse;

	@Autowired
	private Report report;

	@Override
	public Number purchase(Purchase purchase) {

		Map<Product, Integer> products = new HashMap<>();
		for (Entry<Integer, Integer> entry : purchase.products().entrySet()) {

			int idProduct = entry.getKey();
			int qtyPurchase = entry.getValue();

//			warehouse.checkQty(idProduct, qtyPurchase);

			Product product = warehouse.getById(idProduct, Product.class);

			products.put(product, qtyPurchase);
		}

		return warehouse.createOrder(products);
	}

	@Override
	public Resource generateReportOfProductsInStock(String backgroundImage, Path output) throws JRException {
		// datas
		List<Product> products = warehouse.getAll(Product.class);
		List<ProductInStock> productsInStock = warehouse.getAllProductsInStock();

		JRDataSource reportData = new JRBeanCollectionDataSource(products);
		JRDataSource subReportData = new JRBeanCollectionDataSource(productsInStock);

		return report.generateReportProductsInStock(backgroundImage, output, reportData, subReportData);
	}

	@Override
	public List<ProductDto> getAllProducts() {
		return warehouse.getAll(Product.class).stream()
				.map(p -> new ProductDto(p.getId(), p.getName(), p.getCategory(), p.getPrice())).toList();
	}
	
	
	//TODO A
	@Override
	public List<ProductInStockDto> getProductsInStock() {
		return warehouse.getProductsInStock();
	}

	@Override
	public void addProductToCart(int idProduct, int qty, Cart cart) {

		// decurta qta prodotto dal magazzino
		this.warehouse.reduceQty(idProduct, qty);

		// recupera scheda prodotto
		Product product = this.warehouse.getById(idProduct, Product.class);

		// crea dto prodotto
		ProductDto productDto = new ProductDto(idProduct, product.getName(), product.getCategory(), product.getPrice());

		// inserisco il prodotto nel carrello
		cart.getProducts().compute(productDto, (p, qtyInCart) -> qtyInCart == null ? qty : qtyInCart + qty);
	}

	@Override
	public void removeProductToCart(Integer idProduct, Integer qty, Cart cart) {
		// chiedo al carello di eliminare il prodotto
		cart.removeProduct(idProduct, qty);

		// se tutto è andato bene verrà aggiornato lo stock e finisce
		this.warehouse.incrementQty(idProduct, qty);

	}

	@Override
	public int getNumeroFile() {
		
		return warehouse.getNumeroFile();
	}

	@Override
	public void saveFile(String fileName) {
		warehouse.saveFile(fileName);
		
	}

}

package it.bitprojects.store.repository;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import it.bitprojects.store.dto.ProductInStock;
import it.bitprojects.store.dto.ProductInStockDto;
import it.bitprojects.store.exceptions.ProductNotAvailableException;
import it.bitprojects.store.model.Category;
import it.bitprojects.store.model.Order;
import it.bitprojects.store.model.Product;

@Repository
public class Warehouse implements Stock {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public Number createOrder(Map<Product, Integer> products) {

		// Creazione di SimpleJdbcInsert
		SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("orders") // Il nome della
																										// tua tabella
				.usingGeneratedKeyColumns("id"); // Il nome della colonna ID autogenerata

		Map<String, Object> parameters = new HashMap<String, Object>();

		BigDecimal totalPrice = new BigDecimal(0);
		int totalQty = 0;
		for (Entry<Product, Integer> entry : products.entrySet()) {

			// retrive data
			BigDecimal price = entry.getKey().getPrice();
			int qty = entry.getValue();

			// operations
			totalPrice.add(price);
			totalQty += qty;
		}

		parameters.put("total_price", totalQty);
		parameters.put("total_qty", totalPrice);

		return simpleJdbcInsert.executeAndReturnKey(parameters);

	}

	@Override
	public <T> T getById(int id, Class<T> type) {
		String tableName = "";
		if (type.equals(Order.class)) {
			tableName = "ORDERS";
		} else if (type.equals(Product.class)) {
			tableName = "PRODUCTS";
		} else {
			// Se non sappiamo come gestire il tipo, ritorniamo null
			return null;
		}

		try {
			// Utilizziamo jdbcTemplate per fare una query al database
			return jdbcTemplate.queryForObject("SELECT * FROM " + tableName + " WHERE ID = ?",
					new BeanPropertyRowMapper<>(type), id);
		} catch (EmptyResultDataAccessException e) {
			// Se la query non ha restituito alcun risultato, ritorniamo null
			return null;
		}
	}

	@Override
	public <T> List<T> getAll(Class<T> type) {
		String tableName = "";
		if (type.equals(Order.class)) {
			tableName = "ORDERS";
		} else if (type.equals(Product.class)) {
			tableName = "PRODUCTS";
		} else {
			// Se non sappiamo come gestire il tipo, ritorniamo null
			return null;
		}

		// Utilizziamo jdbcTemplate per fare una query al database
		return jdbcTemplate.query("SELECT * FROM " + tableName, new BeanPropertyRowMapper<>(type));
	}

	@Override
	public List<ProductInStock> getAllProductsInStock() {

		String sql = "SELECT * FROM PRODUCTS p INNER JOIN STOCKS s ON p.id = s.id_product";
		List<ProductInStock> products = new ArrayList<>();

		jdbcTemplate.query(sql, new RowCallbackHandler() {
			@Override
			public void processRow(ResultSet rs) throws SQLException {
				// retrieve info product
				String category = Category.valueOf(rs.getString("category")).toString();

				// retrieve qty product in warehouse
				int qty = rs.getInt("qty");

				ProductInStock product = new ProductInStock(category, qty);

				// add product to list
				products.add(product);
			}
		});

		return products;
	}

	/**
	 * con questo metodo tolgo quantita del prodotto e poi chiamo il metodo per
	 * aggiornare
	 */
	public void reduceQty(int idProduct, int qty) {

		String selectSql = "SELECT qty FROM stocks WHERE id_product = " + idProduct + " AND qty >= " + qty;

		int qtyInStock;
		try {

			qtyInStock = jdbcTemplate.queryForObject(selectSql, Integer.class);

		} catch (Exception e) {

			qtyInStock = 0;
		}

		int newQty = qtyInStock - qty;

		if (newQty < 0) {

			throw new ProductNotAvailableException();
		}
		updateQty(idProduct, newQty);

	}

	/**
	 * devo fare metodo per aumentare la quantita e poi aggiornarla di nuovo nello
	 * stock
	 */

	public void incrementQty(int idProduct, int qty) {
		String selectSql = "SELECT qty FROM stocks WHERE id_product = " + idProduct;

		int qtyInStock = jdbcTemplate.queryForObject(selectSql, Integer.class);

		int newQty = qtyInStock + qty;

		updateQty(idProduct, newQty);

	}

	@Override
	public void updateQty(int idProduct, int newQty) {
		String updateSql = "UPDATE stocks SET qty = ? WHERE id_product = ?";

		jdbcTemplate.update(updateSql, newQty, idProduct);

	}

	public List<ProductInStockDto> getProductsInStock() {
		String sql = "SELECT * FROM PRODUCTS p INNER JOIN STOCKS s ON p.id = s.id_product";
		List<ProductInStockDto> products = new ArrayList<>();

		jdbcTemplate.query(sql, new RowCallbackHandler() {
			@Override
			public void processRow(ResultSet rs) throws SQLException {
				// retrieve info product

				int id = rs.getInt("id");
				String name=rs.getString("name");
				Category category = Category.valueOf(rs.getString("category".toUpperCase()));
				BigDecimal price=rs.getBigDecimal("price");
				
				// retrieve qty product in warehouse
				int qty = rs.getInt("qty");

				ProductInStockDto product = new ProductInStockDto(id, name, category, price, qty);

				// add product to list
				products.add(product);
			}
		});
		return products;
	}

}

package it.bitprojects.store.controller;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.ServletContextAware;

import it.bitprojects.store.dto.ProductInStockDto;
import it.bitprojects.store.service.StoreService;
import jakarta.servlet.ServletContext;
import net.sf.jasperreports.engine.JRException;

@RestController
@RequestMapping("/api")
public class MainRestController implements ServletContextAware {

	@Autowired
	private StoreService storeService;

	private ServletContext servletContext;

	@Override
	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}

	/**
	 * fai un endpoint da dove scarico in formato json tutti i prodotti didsponibili
	 * con le relative quantità
	 * 
	 * @param idProduct
	 * @param qty
	 */

	
	@GetMapping(value = "/catalogo", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<ProductInStockDto> getAllProductsInStock() {
		/*
		 * devo recuperare tutti i prodotti dallo stock
		 */
		List<ProductInStockDto> productsDto = storeService.getProductsInStock();

		return productsDto;
	}

	@GetMapping(value = "/products-in-stock", produces = MediaType.APPLICATION_PDF_VALUE)
	public ResponseEntity<Resource> report(@RequestParam(required = false) String imageBackground) {

		String realPath = servletContext.getRealPath("/WEB-INF/reports");
		Path outputPath = Paths.get(realPath);
//		String nameImageBackgroud = "office";

		Resource resource = null;
		try {
			resource = storeService.generateReportOfProductsInStock(imageBackground, outputPath);

		} catch (JRException e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(resource);
		}

		// build response header
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + outputPath.getFileName().toString());

		return ResponseEntity.ok().headers(headers).body(resource);
	}
}
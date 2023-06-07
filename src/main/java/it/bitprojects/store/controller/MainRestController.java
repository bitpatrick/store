package it.bitprojects.store.controller;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.ServletContextAware;

import it.bitprojects.store.dto.Product;
import jakarta.servlet.ServletContext;

@RestController
@RequestMapping("/api")
public class MainRestController implements ServletContextAware {
	
	private ServletContext servletContext;

	@Override
	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}

	@GetMapping("/products")
	@ResponseStatus(HttpStatus.OK)
	public List<Product> getOrders() {
		
		return createOrders();
	}
	
	private List<Product> createOrders() {
		
		Product order1 = new Product(1, "Product1", BigDecimal.ONE, "Description1");
		Product order2 = new Product(1, "Product2", BigDecimal.ONE, "Description2");
		Product order3 = new Product(1, "Product3", BigDecimal.ONE, "Description3");
		
		return Arrays.asList(order1, order2, order3);
	}
	
	@PostMapping("/download")
	public ResponseEntity<Resource> downloadOrderFile() {
		
		String realPath = servletContext.getRealPath("/WEB-INF/reports/orderreport.pdf");
		Resource resource = new FileSystemResource(realPath);
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=orderreport.pdf");

		return ResponseEntity.ok().headers(headers).body(resource);

	}

}

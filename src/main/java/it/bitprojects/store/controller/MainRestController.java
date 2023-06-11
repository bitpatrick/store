package it.bitprojects.store.controller;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
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

import it.bitprojects.store.dto.Purchase;
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

	@GetMapping("/generate/report/products-in-stock")
	public ResponseEntity<Resource> report() {

		String realPath = servletContext.getRealPath("/WEB-INF/reports");
		Path outputPath = Paths.get(realPath, "products_in_stock.pdf");
		String imageName = "inventory";

		Resource resource = null;
		try {
			resource = storeService.generateReportProductsInStock(imageName, outputPath);

		} catch (JRException e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(resource);
		}

		// build response header
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + outputPath.getFileName().toString());

		return ResponseEntity.ok().headers(headers).body(resource);

	}

	@PostMapping("/purchase")
	@ResponseStatus(HttpStatus.OK)
	public void purchase(Purchase purchase) {

	}

}

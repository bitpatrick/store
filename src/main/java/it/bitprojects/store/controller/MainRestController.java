package it.bitprojects.store.controller;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.ServletContextAware;

import it.bitprojects.store.dto.MessageDto;
import it.bitprojects.store.dto.ProductInStockDto;
import it.bitprojects.store.service.StoreService;
import it.bitprojects.store.utility.FileService;
import jakarta.servlet.ServletContext;
import net.sf.jasperreports.engine.JRException;

@RestController
@RequestMapping("/api")
public class MainRestController implements ServletContextAware {

	@Autowired
	private StoreService storeService;

	@Autowired
	private ResourceLoader resourceLoader;

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

	/**
	 * fai un endpoint dove con una chiamata post passo un messaggio e questo viene
	 * memorizzato in un file di testo
	 */
	@PostMapping("/message")
	public void saveMessage(@RequestBody MessageDto messageDto) {
		/*
		 * devo recuperare i nomi dei file nel db e poi incrementarlo e memorizzare 
		 * 
		 * chiamo il service e gli chiedo direttamente un intero  getNumeroFilePresenti
		 */
		int numeroFile=storeService.getNumeroFile();
		String fileName = String.format("file%02d", ++numeroFile);
		String  pathFile = servletContext.getRealPath("/WEB-INF/reports/"+fileName+".txt");

		try (FileWriter writer = new FileWriter(pathFile)) {
			writer.write(messageDto.message());
		} catch (IOException e) {
			System.out.println("impossibile scrivere su file");
		}
		storeService.saveFile(fileName);
	}

	/**
	 * poi un altro end point dove rimandi indietro il file in formato json usando
	 * 
	 */
	@GetMapping("/ask_message_file")
	public ResponseEntity<MessageDto> getMessageFile() {
		Resource resource = resourceLoader.getResource("file:C:/Users/anisoara.balauru/message_from_store.txt");
		String testoFile = null;
		try {
			testoFile = resource.getContentAsString(Charset.defaultCharset());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return ResponseEntity.ok(new MessageDto(testoFile));

	}
	
	
	/**
	 * endpoint per restituire tutti i nomi dei file in una directory
	 */
	@GetMapping("/paths")
	public ResponseEntity<List<String>> getAllPathFiles(){
		String path="C:/Users/anisoara.balauru/academy_workspace";
		
		Set<Path> paths=FileService.listAllFiles(path);
		
		List<String> s=paths.stream().map(p -> p.toString()).toList();
		
		
		
		return ResponseEntity.ok(s);
	}
	
	
	
	
	
	
	
	
	
	
	

}
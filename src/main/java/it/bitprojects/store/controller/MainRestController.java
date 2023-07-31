package it.bitprojects.store.controller;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.ServletContextAware;

import it.bitprojects.store.config.JwtUtils;
import it.bitprojects.store.dto.BalanceDto;
import it.bitprojects.store.dto.LoginRequest;
import it.bitprojects.store.dto.LoginResponse;
import it.bitprojects.store.dto.MessageDto;
import it.bitprojects.store.dto.ProductInStockDto;
import it.bitprojects.store.model.Currency;
import it.bitprojects.store.service.StoreService;
import it.bitprojects.store.utility.FileService;
import jakarta.servlet.ServletContext;
import net.sf.jasperreports.engine.JRException;

@RestController
@RequestMapping("/api")
public class MainRestController implements ServletContextAware {

	@Autowired
	private MessageSource messageSource;

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
		 * chiamo il service e gli chiedo direttamente un intero getNumeroFilePresenti
		 */
		int numeroFile = storeService.getNumeroFile();
		String fileName = String.format("file%02d", ++numeroFile);
		String pathFile = servletContext.getRealPath("/WEB-INF/reports/" + fileName + ".txt");
		/*
		 * C:\Users\anisoara.balauru\academy_workspace\.metadata\.plugins\org.eclipse.
		 * wst.server.core\tmp0\wtpwebapps\store\WEB-INF\reports
		 */

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
	 * @throws IOException
	 * 
	 */
	@GetMapping("/ask_message_file")
	public ResponseEntity<MessageDto> getMessageFile() throws IOException {
		Resource resource = resourceLoader.getResource("file:C:/Users/aisoara.balauru/message_from_store.txt");
		String testoFile = resource.getContentAsString(Charset.defaultCharset());

		return ResponseEntity.ok(new MessageDto(testoFile));

	}

	/**
	 * endpoint per restituire tutti i nomi dei file in una directory
	 */
//	@GetMapping("/paths")
//	public ResponseEntity<List<String>> getAllPathFiles() {
//
//		String path = "C:/Users/anisoara.balauru/academy_workspace";
//
//		Set<Path> paths = FileService.listAllFiles(path);
//
//		List<String> s = paths.stream().map(p -> p.toString()).toList();
//
//		return ResponseEntity.ok(s);
//	}
	@GetMapping("/paths")
	public ResponseEntity<List<String>> getAllPathFiles() {
		String relativePath = "/WEB-INF/reports/";
		Set<Path> paths = FileService.listAllFiles(servletContext.getRealPath(relativePath));
		/*
		 * interfaccia funzionale =implementazione di qualche funzione
		 */
//		implementazione della stessa cosa ma più concisa
//		List<String> stringhe=paths.stream().map(x -> x.toString() ).toList();
		List<String> stringhe = paths.stream().map(

				new Function<Path, String>() {

					@Override
					public String apply(Path t) {
						return t.toString();
					}

				}).toList();
//	        List<Resource> resources = paths.stream()
//	                .map(p -> {
//	                    try {
//	                        return new UrlResource(p.toUri());
//	                    } catch (Exception e) {
//	                        throw new RuntimeException("Issue in converting file to resource");
//	                    }
//	                })
//	                .collect(Collectors.toList());

		return ResponseEntity.ok(stringhe);
	}

	@GetMapping("hello")
	public ResponseEntity<String> helloWorld() {

		return ResponseEntity.ok("Hello World!");
	}

	@GetMapping("/home/wallet/{currency}")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<BalanceDto> incrementBalance(@PathVariable("currency") String currency,
			@RequestParam("quantity") Integer quantity) {

		Currency currencyEnum = null;
		try {
			currencyEnum = Currency.valueOf(currency.toUpperCase());
		} catch (IllegalArgumentException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
		}

		BalanceDto balanceDto = storeService.incrementBalance(currencyEnum, quantity);

		return ResponseEntity.ok(balanceDto);
	}

	@GetMapping("/test1")
	public String handleRequest1(Locale locale) {
		return String.format("Request received. Language: %s, Country: %s %n", locale.getLanguage(),
				locale.getDisplayCountry());
	}

	@GetMapping("/test2")
	public String handleRequest2(@AuthenticationPrincipal UserDetails user, Locale locale) {

		return messageSource.getMessage("app.name", new Object[] { user.getUsername() }, locale);
	}
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private JwtUtils jwtUtils;
	
	/**
	 * ENDPOINT di LOGIN
	 * 
	 * @param loginRequest
	 * @return ResponseEntity
	 */
	@PostMapping(value =  "/login", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<LoginResponse> authenticateUser(@RequestBody LoginRequest loginRequest) {
		
		
		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
		
		SecurityContextHolder.getContext().setAuthentication(authentication); 
		
		String jwt = jwtUtils.generateJwtToken(authentication);
		
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority()).toList();
		
		return ResponseEntity.ok(new LoginResponse(jwt, userDetails.getUsername(), roles));
	}
	
}
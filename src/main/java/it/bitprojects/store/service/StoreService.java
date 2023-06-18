package it.bitprojects.store.service;

import java.nio.file.Path;

import org.springframework.core.io.Resource;

import it.bitprojects.store.dto.Purchase;
import net.sf.jasperreports.engine.JRException;

public interface StoreService {

	public Number purchase(Purchase purchase);
	
	public Resource generateReportOfProductsInStock(String backgroundImage, Path output) throws JRException;

}

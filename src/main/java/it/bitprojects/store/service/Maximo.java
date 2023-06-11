package it.bitprojects.store.service;

import java.awt.Image;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.ImageIcon;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import it.bitprojects.store.dto.ProductInStock;
import it.bitprojects.store.dto.Purchase;
import it.bitprojects.store.model.Product;
import it.bitprojects.store.repository.Warehouse;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRPrintPage;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Service
public class Maximo implements StoreService {

	@Autowired
	private Warehouse warehouse;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	private static final String MASTER_PATH = "/templates/master.jrxml";
	private static final String PDF_PATH = "/templates/orderreport.jrxml";

	@Override
	public Number purchase(Purchase purchase) {

		Map<Product, Integer> products = new HashMap<>();
		for (Entry<Integer, Integer> entry : purchase.products().entrySet()) {

			int idProduct = entry.getKey();
			int qtyPurchase = entry.getValue();

			warehouse.checkQty(idProduct, qtyPurchase);

			Product product = warehouse.getById(idProduct, Product.class);

			products.put(product, qtyPurchase);
		}

		return warehouse.createOrder(products);
	}

	@Override
	public Resource generateReportProductsInStock(String nameImageBackgroud, Path outputPath) throws JRException {

		// insert images on db
		insertImageInDb(nameImageBackgroud);

		// create cover
		JasperPrint document = createCover(nameImageBackgroud);

		// create report
		JasperPrint report = createReport();

		// join pages
		join(document, report);

		// output path
//			new File(realPath).mkdirs(); // Crea la directory se non esiste

		// export
		JasperExportManager.exportReportToPdfFile(document, outputPath.toString());

		return new FileSystemResource(outputPath);
	}

	private JasperPrint createReport() throws JRException {

		// retrive products in stock
		List<ProductInStock> products = warehouse.getAllProductsInStock();

		// set data for jasper
		JRBeanCollectionDataSource data = new JRBeanCollectionDataSource(products);

		// build template
		InputStream reportInputStream = this.getClass().getResourceAsStream(PDF_PATH);
		JasperReport report = JasperCompileManager.compileReport(reportInputStream);
		JasperPrint reportPrinted = JasperFillManager.fillReport(report, null, data);

		return reportPrinted;
	}

	private JasperPrint createCover(String imageName) throws JRException {

		Map<String, Object> parameters = null;

		// check if image is present
		if (imageName != null || !imageName.trim().isEmpty()) {

			parameters = new HashMap<>(1);

			// retrieve image from db
			byte[] imageBytes = getImageFromDB(imageName);
			ImageIcon icon = new ImageIcon(imageBytes);
			Image image = icon.getImage();

			// build parameter image
			parameters.put("image", image);
		}

		// cover
		InputStream masterInputStream = this.getClass().getResourceAsStream(MASTER_PATH);
		JasperReport master = JasperCompileManager.compileReport(masterInputStream);
		JasperPrint masterPrinted = JasperFillManager.fillReport(master, parameters, new JREmptyDataSource());

		return masterPrinted;
	}

	private void insertImageInDb(String imageName) {

		String imageNameLowerCase = imageName.toLowerCase();

		try {
			Resource resource = new ClassPathResource("images/" + imageNameLowerCase);
			byte[] imageBytes = Files.readAllBytes(resource.getFile().toPath());

			jdbcTemplate.update("INSERT INTO images (name, blob) VALUES (?, ?)",
					new Object[] { imageNameLowerCase, imageBytes });

		} catch (Exception e) {
			// gestisci l'eccezione
			e.printStackTrace();
		}
	}

	private byte[] getImageFromDB(String imageName) {
		try {
			byte[] imageBytes = jdbcTemplate.queryForObject("SELECT blob FROM images WHERE name = ?",
					new Object[] { imageName }, byte[].class);
			return imageBytes;
		} catch (Exception e) {
			// gestisci l'eccezione
			e.printStackTrace();
			return null;
		}
	}

	private void join(JasperPrint cover, JasperPrint... reports) {

		if (reports == null) {
			return;
		}

		List<JRPrintPage> pages = new ArrayList<>();
		for (JasperPrint report : reports) {
			List<JRPrintPage> pagesReport = report.getPages();
			for (JRPrintPage page : pagesReport) {
				pages.add(page);
			}
		}

		for (JRPrintPage reportPage : pages) {
			cover.addPage(reportPage);
		}
	}

}

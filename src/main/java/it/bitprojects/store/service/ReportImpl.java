package it.bitprojects.store.service;

import java.awt.Image;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.ImageIcon;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.JdbcTemplate;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRPrintPage;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;

public class ReportImpl implements Report {
	
	private JasperPrint cover = null;

	private static final String MASTER_PATH = "/templates/cover.jrxml";
	private static final String REPORT_PATH = "/templates/orderreport.jrxml";
	private static final String SUBREPORT_PATH = "/templates/suborderreport.jrxml";

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public Resource generateReportProductsInStock(String nameImageBackgroud, Path outputPath, JRDataSource reportData,
			JRDataSource subReportData) throws JRException {
		
		// cover 
		createCover(nameImageBackgroud);

		// create report
		JasperPrint report = createReport(reportData, subReportData);

		// check if cover is present
		if ( cover != null ) {
			// join pages
			join(cover, report);
		}

		// Crea la directory se non esiste
//		outputPath.toFile().mkdirs();

		// export
		JasperExportManager.exportReportToPdfFile((cover != null) ? cover : report, outputPath.toString());

		return new FileSystemResource(outputPath);
	}

	private JasperPrint createReport(JRDataSource reportData, JRDataSource subReportData) throws JRException {

		// build sub report
		InputStream subReportInputStream = this.getClass().getResourceAsStream(SUBREPORT_PATH);
		JasperReport subReport = JasperCompileManager.compileReport(subReportInputStream);

		Map<String, Object> params = new HashMap<>();
		params.put("subreport", subReport);
		params.put("subdata", subReportData);

		// build report
		InputStream reportInputStream = this.getClass().getResourceAsStream(REPORT_PATH);
		JasperReport report = JasperCompileManager.compileReport(reportInputStream);
		JasperPrint reportPrinted = JasperFillManager.fillReport(report, params, reportData);

		return reportPrinted;
	}

	public void createCover(String imageNameBackground) throws JRException {

		Map<String, Object> parameters = null;

		// check if image is present
		if (imageNameBackground != null && !imageNameBackground.trim().isEmpty()) {

			parameters = new HashMap<>(1);

			// retrieve image from db
			byte[] imageBytes = getImageFromDB(imageNameBackground);
			ImageIcon icon = new ImageIcon(imageBytes);
			Image image = icon.getImage();

			// build parameter image
			parameters.put("image", image);
		}

		// build cover
		InputStream coverInputStream = this.getClass().getResourceAsStream(MASTER_PATH);
		JasperReport coverReport = JasperCompileManager.compileReport(coverInputStream);
		JasperPrint coverPrint = JasperFillManager.fillReport(coverReport, parameters, new JREmptyDataSource());

		cover = coverPrint;
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

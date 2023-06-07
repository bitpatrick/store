package it.bitprojects.store.listener;

import java.io.File;
import java.io.InputStream;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.bitprojects.store.model.Order;
import it.bitprojects.store.model.Stock;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRPrintPage;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@WebListener
public class JasperStartupListener implements ServletContextListener {

	private String fileMasterPath = "/templates/master.jrxml";
	private String filePath = "/templates/orderreport.jrxml";
	private Map<String, Object> parameters = new HashMap<>();
	
    @Override
    public void contextInitialized(ServletContextEvent sce) {
    	
    	fillParameters();
    	
    	try {
    		
    	    // cover
    	    InputStream masterInputStream = this.getClass().getResourceAsStream(fileMasterPath);
    	    JasperReport master = JasperCompileManager.compileReport(masterInputStream);
    	    JasperPrint masterPrinted = JasperFillManager.fillReport(master, null, new JREmptyDataSource());

    		// report
    		InputStream reportInputStream = this.getClass().getResourceAsStream(filePath);
    		JasperReport report = JasperCompileManager.compileReport(reportInputStream);
			JasperPrint reportPrinted = JasperFillManager.fillReport(report, parameters, new JREmptyDataSource());
			
			// join pages
			join(masterPrinted, reportPrinted);
			
			// output path
			String realPath = sce.getServletContext().getRealPath("/WEB-INF/reports");
	        new File(realPath).mkdirs(); // Crea la directory se non esiste
			
	        //export
	        JasperExportManager.exportReportToPdfFile(masterPrinted, realPath + "/orderreport.pdf");
	        System.out.println("Report Generated successfully");
	        
    	 } catch (JRException e) {
			e.printStackTrace();
		}
    }


	private void join(JasperPrint coverPrinted, JasperPrint reportPrinted) {
		
		List<JRPrintPage> reportPages = reportPrinted.getPages();
		
		for (JRPrintPage reportPage : reportPages) {
			coverPrinted.addPage(reportPage);
		}
	}


	private void fillParameters() {

		parameters.put("firstName", "Mario");
		parameters.put("lastName", "Rossi");
		LocalDate dateOfBirth = LocalDate.of(1987, 10, 19);
		parameters.put("dateOfBirth", dateOfBirth);
		parameters.put("age", getAge(dateOfBirth));
		
		JRBeanCollectionDataSource orderDataSet = new JRBeanCollectionDataSource(getOrders());
		parameters.put("orderDataSet", orderDataSet);
		
		JRBeanCollectionDataSource storeDataSet = new JRBeanCollectionDataSource(getStocks());
		parameters.put("storeDataSet", storeDataSet);
	}
	
	private List<Order> getOrders() {
		
		Order order1 = new Order("TV", 2, new BigDecimal(10));
		Order order2 = new Order("PHONE", 3, new BigDecimal(20));
		Order order3 = new Order("Stereo", 1, new BigDecimal(30));
		
		return Arrays.asList(order1, order2, order3);
	}
	
	private List<Stock> getStocks() {
		
		Stock stock1 = new Stock("TV", 10);
		Stock stock2 = new Stock("PHONE", 20);
		Stock stock3 = new Stock("Stereo", 22);
		
		return Arrays.asList(stock1, stock2, stock3);
	}
	
	private int getAge(LocalDate dateOfBirth) {
		
	    LocalDate today = LocalDate.now();
	    Period p = Period.between(dateOfBirth, today);
	    return p.getYears();
	}

}

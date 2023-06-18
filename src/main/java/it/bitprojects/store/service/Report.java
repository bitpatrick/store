package it.bitprojects.store.service;

import java.nio.file.Path;

import org.springframework.core.io.Resource;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;

public interface Report {

	public <T extends JRDataSource> Resource generateReportProductsInStock(String nameImageBackgroud, Path outputPath,
			T reportData, T subReportData) throws JRException;

	public void createCover(String imageNameBackground) throws JRException;
	

}

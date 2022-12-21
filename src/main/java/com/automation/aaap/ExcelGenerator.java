package com.automation.aaap;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import com.automation.aaap.models.TickerResult;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

@Service
public class ExcelGenerator {
	public static  ByteArrayInputStream excelGenerato(List<TickerResult> tickers) {

		Workbook workbook = new XSSFWorkbook();

		Sheet sheet = workbook.createSheet("Persons");
		sheet.setColumnWidth(0, 6000);
		sheet.setColumnWidth(1, 6000);
		sheet.setColumnWidth(2, 6000);
		sheet.setColumnWidth(3, 6000);
		sheet.setColumnWidth(4, 6000);
		sheet.setColumnWidth(5, 6000);

		Row header = sheet.createRow(0);

		CellStyle headerStyle = workbook.createCellStyle();
		headerStyle.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex());
		headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

		XSSFFont font = ((XSSFWorkbook) workbook).createFont();
		font.setFontName("Arial");
		font.setFontHeightInPoints((short) 12);
		font.setBold(true);
		headerStyle.setFont(font);

		Cell headerCell = header.createCell(0);
		headerCell.setCellValue("IDENTITY");
		headerCell.setCellStyle(headerStyle);

		headerCell = header.createCell(1);
		headerCell.setCellValue("BUYVALUE");
		headerCell.setCellStyle(headerStyle);
		
		headerCell = header.createCell(2);
		headerCell.setCellValue("BUYWALLET");
		headerCell.setCellStyle(headerStyle);
		
		headerCell = header.createCell(3);
		headerCell.setCellValue("SELLVALUE");
		headerCell.setCellStyle(headerStyle);
		
		headerCell = header.createCell(4);
		headerCell.setCellValue("SELLWALLET");
		headerCell.setCellStyle(headerStyle);

		headerCell = header.createCell(5);
		headerCell.setCellValue("PERCENTAGE");
		headerCell.setCellStyle(headerStyle);
		
		CellStyle style = workbook.createCellStyle();
		style.setWrapText(true);
		int rownum =2;
		for(TickerResult t : tickers) {
			Row row = sheet.createRow(rownum++);
			Cell cell = row.createCell(0);
			cell.setCellValue(t.getIdentity());
			cell.setCellStyle(style);
			
			
			Cell cell1 = row.createCell(1);
			cell1.setCellValue(t.getBuy());
			cell1.setCellStyle(style);
			
			Cell cell2 = row.createCell(2);
			cell2.setCellValue(t.getBuyWallet());
			cell2.setCellStyle(style);
			
			Cell cell3 = row.createCell(3);
			cell3.setCellValue(t.getSell());
			cell3.setCellStyle(style);
			
			Cell cell4 = row.createCell(4);
			cell4.setCellValue(t.getSellWallet());
			cell4.setCellStyle(style);
			
			Cell cell5 = row.createCell(5);
			cell5.setCellValue(t.getPercentage());
			cell5.setCellStyle(style);
			
		}
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		try {
			workbook.write(out);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new ByteArrayInputStream(out.toByteArray());
		

	}
}

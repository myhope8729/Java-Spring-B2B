package com.kpc.eos.controller.bizSetting.handler;

import java.io.FileInputStream;
import java.util.Collection;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.kpc.eos.core.handler.AbstractRowHandler;
import com.kpc.eos.core.util.ExcelUtil;
import com.kpc.eos.core.web.context.ApplicationSetting;

public class UserItemRowHandler extends AbstractRowHandler {

	public UserItemRowHandler(String title) {
		super(title);
	}
	
	public UserItemRowHandler(String title, Map<String, String> header){
		if(title == null || title.length() == 0) return;
		this.title = title;
		this.rowIndex 		= 1;
		this.m_nRowStart	= 0;
		this.dataNum		= 0;
		this.sheetNum		= 0;
		this.m_nColStart	= 0;
		this.m_nRowHeight	= 250;
		this.m_nSpaceRows	= 0;
		this.m_bRowCreatable= true;
		this.printAll	= true;
		
		this.excelMap 	= header;
		
		String strPath = ApplicationSetting.getConfig("webcontent-path") + "/printtemplates/";
		try {
			FileInputStream fin = new FileInputStream(strPath + title + ".xls");
			this.workbook = new HSSFWorkbook(fin, true);
			this.sheet	= this.workbook.getSheetAt(0);
			this.rowIndex 	= 1;
		}
		catch(Exception e) {
			this.rowIndex 	= 1;
			this.dataNum	= 0;
			this.sheetNum	= 0;
			
			this.workbook 	= new HSSFWorkbook();
			this.sheet 		= createSheet();
		}
	}
	
	@Override
	protected void generateExcelMap(Map<String, String> excelMap) {
		/*excelMap.put("编号", "itemId");
		excelMap.put("Brand", 				"brand");
		excelMap.put("Unit", 				"unit");
		excelMap.put("C1", 					"c1");
		excelMap.put("C2", 		    		"c2");
		excelMap.put("C3", 		    		"c3");*/
	}
	
	@Override
	protected void makeExcelFile(Object clazz) {

		HSSFCell cell = null;
		HSSFRow row = this.sheet.createRow(this.rowIndex++);
		row.setHeightInPoints((short) 17);
		
		HSSFCellStyle textStyle = this.workbook.createCellStyle();
		/*textStyle.setAlignment(CellStyle.ALIGN_CENTER);
        textStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		textStyle.setWrapText(true);*/

		Collection<String> attributes = this.excelMap.values();
		int j = 0;
		for (String attribute : attributes) {
			cell = row.createCell(j++);
			Object value = null;
			try {
				value = (String)PropertyUtils.getNestedProperty(clazz, attribute);		
			} catch(Exception e) {
				this.logger.error(attribute + "  property not exists", e);
			}
			
			ExcelUtil.setCellValue(cell, value, textStyle);
		}
		
	}

}



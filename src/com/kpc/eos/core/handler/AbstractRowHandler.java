
package com.kpc.eos.core.handler;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.imageio.ImageIO;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.util.CellRangeAddress;

import sun.awt.image.codec.JPEGImageEncoderImpl;

import com.ibatis.sqlmap.client.event.RowHandler;
import com.kpc.eos.core.util.MathUtil;
import com.kpc.eos.core.web.context.ApplicationSetting;

/**
 * AbstractRowHandler
 * =================
 * Description : excel download row handler
 * Methods :
 */
public abstract class AbstractRowHandler implements RowHandler {

	protected Logger logger = Logger.getLogger(this.getClass());
	
	protected int 			rowIndex;
	protected int           dataNum;
	protected int 			sheetNum;
	
	protected int			m_nRowStart;
	protected int			m_nColStart;
	protected int 			m_nRowHeight;
	protected int 			m_nSpaceRows;
//	protected int 			m_nFontSize;	
//	protected String		m_strFontName;
	protected boolean 		m_bRowCreatable;
	
	protected String		title;
	protected HSSFWorkbook 	workbook;
	protected HSSFSheet 	sheet;
	protected Map<String, String> excelMap;
	
	protected int[] m_nColWidths;
	
	protected boolean printAll = false;

	public AbstractRowHandler() {
		this("", "", 250, 0, 0);
	}
	
	public AbstractRowHandler(HSSFWorkbook wb) {
		this.workbook = wb;
		
		this.m_nRowHeight = 250;
		this.m_nSpaceRows = 0;
		this.m_nRowStart  = 1;
		this.rowIndex 		= 1;
		this.dataNum		= 0;
		this.sheetNum		= 0;
		this.m_nColStart	= 0;
		this.m_bRowCreatable= true;
	}
	
	/**
	 * ======================
	 * @param wb			: Workbook
	 * @param nRowStart		: 
	 * @param nColStart		: 
	 * @param nRowHeight	: 
	 * @param nSpaceRows	:  
	 * @param bRowCreatable	: 
	 */
	public AbstractRowHandler(HSSFWorkbook wb, 
							int nRowStart, 
							int nColStart, 
							int nRowHeight, 
							int nSpaceRows, 
							boolean bRowCreatable) {
		this(wb);
		
		if(wb != null) {
			this.m_nRowStart 	= nRowStart;
			this.rowIndex 	 	= nRowStart;
			this.m_nColStart 	= nColStart;
			this.m_nRowHeight	= nRowHeight;
			this.m_nSpaceRows	= nSpaceRows;
			this.m_bRowCreatable= bRowCreatable;
			
			this.sheet		= this.workbook.getSheetAt(0);
			this.excelMap 	= new LinkedHashMap<String, String>();
			generateExcelMap(this.excelMap);
		}
	}
	
	/**
	 * ==================
	 * @param wb			: Workbook
	 * @param nSheet		: Sheet number
	 * @param nRowStart		: 
	 * @param nColStart		: 
	 * @param nRowHeight	: 
	 * @param nSpaceRows	:  
	 * @param bRowCreatable	: 
	 */
	public AbstractRowHandler(HSSFWorkbook wb, 
								int nSheet, 
								int nRowStart, 
								int nColStart, 
								int nRowHeight, 
								int nSpaceRows, 
								boolean bRowCreatable) {
		this(wb);
		
		if(wb != null) {
			this.m_nRowStart 	= nRowStart;
			this.rowIndex 	 	= nRowStart;
			this.m_nColStart 	= nColStart;
			this.m_nRowHeight	= nRowHeight;
			this.m_nSpaceRows	= nSpaceRows;
			this.m_bRowCreatable= bRowCreatable;
			
			this.sheet		= this.workbook.getSheetAt(nSheet);
			this.excelMap 	= new LinkedHashMap<String, String>();
			generateExcelMap(this.excelMap);
		}
	}
	
	public AbstractRowHandler(String title) {
		this("", title, 250, 0, 0);
	}
	
	/**
	 * =====================
	 * @param relativePath	: relative path of Excel Template 
	 * @param title			: file name
	 * @param nRowStart		: start row index
	 * @param nRowHeight	: 
	 * @param nSpaceRows	: 
	 */
	public AbstractRowHandler(String relativePath, String title, int nRowHeight, int nRowStart, int nSpaceRows) {
		this(relativePath, title, nRowHeight, nRowStart, 0, nSpaceRows, true);
	}
	
	/**
	 * ===================
	 * @param relativePath	: relative path of Excel Template
	 * @param title			: 
	 * @param nRowStart		: 
	 * @param nRowHeight	: 
	 * @param nSpaceRows	: 
	 * @param pricePrintable: 
	 */
	public AbstractRowHandler(String relativePath, 
								String title, 
								int nRowHeight, 
								int nRowStart, 
								int nSpaceRows, 
								boolean pricePrintable) {
		this(relativePath, title, nRowHeight, nRowStart, 0, nSpaceRows, pricePrintable);
	}
	
	public AbstractRowHandler(
								String relativePath, 
								String title, 
								int nRowHeight, 
								int nRowStart, 
								int nColStart, 
								int nSpaceRows) {
		this(relativePath, title, nRowHeight, nRowStart, nColStart, nSpaceRows, true);
	}
	
	public AbstractRowHandler(
								String relativePath, 
								String title, 
								int nRowHeight, 
								int nRowStart, 
								int nColStart, 
								int nSpaceRows, 
								boolean pricePrintable) {
		if(title == null || title.length() == 0) return;
		this.rowIndex 		= 1;
		this.m_nRowStart	= nRowStart;
		this.dataNum		= 0;
		this.sheetNum		= 0;
		this.m_nColStart	= nColStart;
		this.m_nRowHeight	= nRowHeight;
		this.m_nSpaceRows	= nSpaceRows;
		this.m_bRowCreatable= true;
		this.printAll	= pricePrintable;
		
		this.title		= title;
		this.excelMap 	= new LinkedHashMap<String, String>();
		
		String strPath = ApplicationSetting.getConfig("webcontent-path") + "/printtemplates/" + relativePath;
		try {
			FileInputStream fin = new FileInputStream(strPath + title + ".xls");
			this.workbook = new HSSFWorkbook(fin, true);
			this.sheet	= this.workbook.getSheetAt(0);
			generateExcelMap(this.excelMap);
			this.rowIndex 	= 1;
		}
		catch(Exception e) {
			this.rowIndex 	= 1;
			this.dataNum	= 0;
			this.sheetNum	= 0;
			
			this.workbook 	= new HSSFWorkbook();
			generateExcelMap(this.excelMap);
			this.sheet 		= createSheet();
		}
	}
	
	/**
	 * createSheet
	 * ===================
	 * Creating sheet
	 * @param wb
	 * @param excelMap
	 * @return
	 */
	protected HSSFSheet createSheet() {
		HSSFRow header = null;
		HSSFCell cell = null;
		this.rowIndex = 1;
		String sheetTitle = StringUtils.isBlank(this.title)? "Sheet" + this.sheetNum : this.title + this.sheetNum;
		sheetNum++;
		
		this.sheet = this.workbook.createSheet(sheetTitle);
		this.sheet.setFitToPage(true);
		this.sheet.setHorizontallyCenter(true);
		
		if (StringUtils.isNotBlank(this.title)) {
			//Setting Title
			header = this.sheet.createRow(0);
			header.setHeightInPoints(40);
			cell = header.createCell(0);
			cell.setCellValue(sheetTitle);
			cell.setCellStyle(getTitleStyle());
			this.sheet.addMergedRegion(new CellRangeAddress(0,0,0,this.excelMap.size() - 1));
			
			rowIndex++; // space
		}

		//header Setting
	    int i = 0;
	    HSSFCellStyle headerStyle =  this.workbook.createCellStyle();
	    headerStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);

	    header = this.sheet.createRow(this.rowIndex++);

	    Set titles = excelMap.keySet();
	    Iterator iter = titles.iterator();
	    while (iter.hasNext()) {
	    	cell = header.createCell(i++);
	    	cell.setCellValue(iter.next().toString());
	    	cell.setCellStyle(headerStyle);
	    }

	    return sheet;
	}
	
	/**
	 * getTitleStyle
	 * ===================
	 * Return Title style
	 * @return
	 */
	private HSSFCellStyle getTitleStyle() {
        
		HSSFCellStyle style = this.workbook.createCellStyle();
        style.setAlignment(CellStyle.ALIGN_CENTER);
        style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        
        HSSFFont font = workbook.createFont();
        font.setFontHeightInPoints((short)18);
        font.setBoldweight(Font.BOLDWEIGHT_BOLD);
        style.setFont(font);
		
		return style;
	}

	/**
	 * generateExcelMap
	 * ===================
	 * Setting Header info And individual column info
	 * @param excelMap
	 */
	protected abstract void generateExcelMap(Map<String, String> excelMap);
	
	public void handleRow(Object rowObject) {
		this.dataNum++;
		if (this.dataNum%50000==0) {
			this.sheet = createSheet();
		}
		makeExcelFile(rowObject);
	}
	
	/**
	 * makeExcelFile
	 * ===================
	 * Making Excel File.
	 * @param rowObject
	 */
	protected abstract void makeExcelFile(Object clazz); 
	
	/**
	 * getWorkbook
	 * ===================
	 *  Return Excel workbook
	 * @return
	 */
	public HSSFWorkbook getWorkbook() {
		return this.workbook;
	}
	
	/**
	 * Setting a value to indicated cell
	 * ================================
	 * @param row
	 * @param col
	 * @param val
	 * @return
	 */
	protected boolean setCellValue(int nRow, int nCol, Object val, HSSFCellStyle style) {
		try {
			HSSFRow row = this.sheet.getRow(nRow);
			HSSFCell cell = row.getCell(nCol);
			if(cell == null) cell = row.createCell(nCol);
			
			com.kpc.eos.core.util.ExcelUtil.setCellValue(cell, val, style);
		}
		catch(Exception e) {
			return false;
		}
		
		return true;
	}
	
	/**
	 * setCellValue
	 * ===================
	 * Setting a value to indicated cell.
	 * output yes or no of zero value
	 * 
	 * @param nRow
	 * @param nCol
	 * @param value
	 * @param style
	 * @param zeroWritable
	 * @return
	 */
	protected boolean setCellValue(int nRow, int nCol, Object value, HSSFCellStyle style, boolean zeroWritable){
		if(value == null) value = "";
		
		if(!zeroWritable) {
			String temp = "";
			try{
				temp = MathUtil.round(value.toString(), 3);
				
			} catch(Exception e) {}
			
			if(temp.equalsIgnoreCase("0")) value = "";
		}
		
		return setCellValue(nRow, nCol, value, style);
	}
	
	/**
	 * Setting a value to indicated cell.
	 * ================================
	 * @param row
	 * @param col
	 * @param val
	 * @return
	 */
	protected boolean setCellValue(int nRow, int nCol, String val) {
		return setCellValue(nRow, nCol, val, null);
	}
	
	/**
	 * Setting a expression to indicated cell.
	 * ================================
	 * @param row
	 * @param col
	 * @param val
	 * @return
	 */
	protected boolean setCellFormula(int nRow, int nCol, String val, HSSFCellStyle style) {
		try {
			HSSFRow row = this.sheet.getRow(nRow);
			HSSFCell cell = row.getCell(nCol);
			if(cell == null) cell = row.createCell(nCol);
			cell.setCellFormula(val);
			
			if(style != null)
				cell.setCellStyle(style);
		}
		catch(Exception e) {
			return false;
		}
		
		return true;
	}
	
	/**
	 * Setting a expression to indicated cell.
	 * ================================
	 * @param row
	 * @param col
	 * @param val
	 * @return
	 */
	protected boolean setCellFormula(int nRow, int nCol, String val) {
		return setCellFormula(nRow, nCol, val, null);
	}
	
//	protected void setCellFont(int nRow, int nCol, String strFontName, int nFontSize, int nBoldWeight) {
//		try {
//			HSSFRow row = this.sheet.getRow(nRow);
//			HSSFCell cell = row.getCell(nCol);
//
//			HSSFCellStyle textStyle = cell.getCellStyle();
//			HSSFFont font = textStyle.getFont(this.workbook);
//			font.setFontName(strFontName);
//			font.setFontHeightInPoints((short)nFontSize);
//			font.setBoldweight((short)nBoldWeight);
//			textStyle.setFont(font);
//			
//			textStyle.setAlignment(CellStyle.ALIGN_CENTER);
//	        textStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
//			
//			cell.setCellStyle(textStyle);
//		}
//		catch(Exception e) {
//			;
//		}
//	}
	
	public void setColWidths(int[] widths) {
		this.m_nColWidths = widths;
	}
	
	/**
	 * Label of the given column
	 * 
	 * @param nCol
	 * @return
	 */
	public String getColLabel(int nCol) {
		int n = nCol / 26;
		int rest = nCol % 26;
		char ch = 'A';
		
		StringBuffer strBuf = new StringBuffer("");
		for(int i = 0; i < n; i++) {
			char chTmp = ch;
			chTmp += i;
			strBuf.append(chTmp);
		}
		
		ch += rest;
		strBuf.append(ch);
		
		return strBuf.toString();
	}
	
	/**
	 * Create a formula string like SUM(J10:J20)
	 * 
	 * @param nCol
	 * @param nStartRow
	 * @param nEndRow
	 * @return
	 */
	public String getColSumFormula(int nCol, int nStartRow, int nEndRow) {
		String strFormula = String.format("SUM(%s%d:%s%d)", getColLabel(nCol), nStartRow, getColLabel(nCol), nEndRow);
		return strFormula;
	}
	
	/**
	 * Create a formula string like SUM(J10,J20, J22)
	 * 
	 * @param nCol
	 * @param rows
	 * @return
	 */
	public String getColSumFormula(int nCol, ArrayList<Number> rows) {
		if(rows == null || rows.size() == 0) return "";
		
		String strColLabel = getColLabel(nCol);
		StringBuffer formula = new StringBuffer("SUM(");
		for(int i = 0; i < rows.size(); i++) {
			formula.append(i > 0 ? "," : "");
			
			int rowNo = rows.get(i).intValue();
			
			formula.append(strColLabel).append(rowNo + 1);
		}
		formula.append(")");
		
		return formula.toString();
	}
	
	/**
	 * Get the selected cell's string value.
	 * 
	 * @param nRow
	 * @param nCol
	 * @return
	 */
	public Object getCellValue(int nRow, int nCol) {
		if(this.sheet == null) return "";
		
		HSSFRow row = this.sheet.getRow(nRow);
		if(row == null) return "";
		
		HSSFCell cell = row.getCell(nCol);
		if(cell == null) return "";
		
		Object value = null;
		switch(cell.getCellType()) {
			case HSSFCell.CELL_TYPE_BOOLEAN:
				value = cell.getBooleanCellValue();
				break;
			case HSSFCell.CELL_TYPE_NUMERIC:
				value = cell.getNumericCellValue();
				break;
			case HSSFCell.CELL_TYPE_STRING:
				value = cell.getStringCellValue();
				break;
		}
		
		return value;
	}
	
	 
	public HSSFCellStyle getCellStyle(int nRow, int nCol) {
		if(this.sheet == null) return null;
		
		HSSFRow row = this.sheet.getRow(nRow);
		if(row == null) return null;
		
		HSSFCell cell = row.getCell(nCol);
		if(cell == null) return null;
		
		return cell.getCellStyle();
	}
	

	/**
	 *  Merge a cell
	 * 
	 * @param nCol
	 */
	public void mergeCellsByValue(int nCol) {
		if(this.sheet == null) return;
		
		int nStartRow = this.m_nRowStart;
		boolean bMerge = false;
		Object val = getCellValue(nStartRow, nCol);
		for(int i = this.m_nRowStart; i <= this.rowIndex; i++) {
			try {
				bMerge = !val.equals(getCellValue(i, nCol));
			}
			catch (Exception e) {
				
			}
			
			if(bMerge) {
				mergeCells(nStartRow, i - 1, nCol, nCol);
				val = getCellValue(i, nCol);
				nStartRow = i;
				bMerge = false;
			}
		}
	}
	
	/**
	 * Merge a cell
	 * 
	 * @param nCol
	 * @param nRowStart : 
	 */
	public void mergeCellsByValue(int nCol, int nRowStart) {
		if(this.sheet == null) return;
		
		int nStartRow = nRowStart;
		boolean bMerge = false;
		Object val = getCellValue(nStartRow, nCol);
		for(int i = nRowStart; i <= this.rowIndex; i++) {
			try {
				bMerge = !val.equals(getCellValue(i, nCol));
			}
			catch (Exception e) {
				
			}
			
			if(bMerge) {
				mergeCells(nStartRow, i - 1, nCol, nCol);
				val = getCellValue(i, nCol);
				nStartRow = i;
				bMerge = false;
			}
		}
	}
	
	/**
	 * Merge selected area.
	 * ========================
	 * 
	 * @param nStartRow
	 * @param nEndRow
	 * @param nStartCol
	 * @param nEndCol
	 */
	public void mergeCells(int nStartRow, int nEndRow, int nStartCol, int nEndCol) {
		if(this.sheet == null) return;
		
		this.sheet.addMergedRegion(new CellRangeAddress(nStartRow, nEndRow, nStartCol, nEndCol));
	}
	
	/**
	 * paint a indicated image file to excel 
	 * ====================================
	 * 
	 * @param strFullPath
	 * @return
	 */
	public boolean addPicture(String strFullPath) {
		boolean result = false;
		
		try {
			String strPath = strFullPath;
			
			int n = strPath.lastIndexOf(".");
			String strExt = strPath.substring(n + 1);
			
			File file = new File(strPath);
			File oFile = new File(strPath.substring(0, n) + ".jpg");
			
			boolean removable = false;
			if(!strExt.equalsIgnoreCase("jpg") && 
				!strExt.equalsIgnoreCase("jpeg") && 
				!strExt.equalsIgnoreCase("png")  ) {

				BufferedImage bi = ImageIO.read(file); 
				JPEGImageEncoderImpl jie = new JPEGImageEncoderImpl(new FileOutputStream(oFile));
				jie.encode(bi);	
				
				strPath = oFile.getAbsolutePath();
				n = strPath.lastIndexOf(".");
				strExt = strPath.substring(n + 1);
				
				file = new File(strPath);
				
				removable = true;
			}			
			
			int nType = HSSFWorkbook.PICTURE_TYPE_JPEG;
			if(strExt.equalsIgnoreCase("jpg") || strExt.equalsIgnoreCase("jpeg")) {
				nType = HSSFWorkbook.PICTURE_TYPE_JPEG;
			}
			else if(strExt.equalsIgnoreCase("png")) {
				nType = HSSFWorkbook.PICTURE_TYPE_PNG;
			}
			
			byte[] btPic = new byte[(int)file.length()];
			FileInputStream fIn = new FileInputStream(file);
			fIn.read(btPic);
			fIn.close();
			
			if(removable)
				oFile.delete();
			
			this.workbook.addPicture(btPic, nType);
		}
		catch(Exception e) {
			e.printStackTrace();
			result = false;
		}
		
		return result;
	}
	
	public void printData(List list) {
		if(list == null) return;
		
		for(int i = 0; i < list.size(); i++) {
			this.makeExcelFile(list.get(i));
		}
	}
}

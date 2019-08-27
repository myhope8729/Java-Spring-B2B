package com.kpc.eos.core.util;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;

public class ExcelUtil {
	public static void setCellValue(HSSFCell cell, Object value,HSSFCellStyle textStyle) {
		if (value == null){
			value = "";
		}
		if (canBeNumber(value)) {
//			value = MathUtil.round(value.toString(), String.valueOf(2));
			value = toNumber(value);
		}

		if (value instanceof Number) {
			cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
			double dValue = ((Number) value).doubleValue();
			cell.setCellValue(dValue);
		}

		else {
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			cell.setCellValue(value.toString());
		}
		
		if(textStyle != null)
			cell.setCellStyle(textStyle);
	}

	private static boolean canBeNumber(Object value) {
		try {
			Double.parseDouble(value.toString());
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	private static Double toNumber(Object value) {
		return Double.parseDouble(value.toString());
	}
}

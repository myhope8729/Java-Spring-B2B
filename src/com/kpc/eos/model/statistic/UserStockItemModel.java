
package com.kpc.eos.model.statistic;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.apache.commons.lang.StringUtils;

import lombok.Data;

import com.kpc.eos.core.Constants;
import com.kpc.eos.core.model.CommonModel;

@Data
public  class UserStockItemModel extends CommonModel {
	private static final long serialVersionUID = 1529113477684036050L;
	
	private String itemId;
	private String brand;
	private String c1;
	private String c2;
	private String c3;
	private String c4;
	private String c5;
	private String c6;
	private String c7;
	private String c8;
	private String c9;
	private String c10;
	private String c11;
	private String c12;
	private String c13;
	private String c14;
	private String c15;
	private String c16;
	private String c17;
	private String c18;
	private String c19;
	private String c20;
	private String d1;
	private String d2;
	private String d3;
	private String d4;
	private String d5;
	private String d6;
	private String dt1;
	
	
	private String storeId;
	private String storeName;
	private String vendorId;
	private String vendor;
	private String state;
	private String stateName;
	private String stockMark;
	private String stockMarkName;
	private String note;
	private String itemImgPath;
	private String priceIn;
	private String lastVendorId;
	private String lastVendorName;
	
	private String stockCost;
	private String stockQty;
	private String stockTot;
	private String jsQty;
	private String jsYn;
	
	private String deptId;
	private String deptName;
	
	// order
	/*bl.qty 						AS 		qty,  
	bl.price 					AS 		price,
	bl.tot 						AS 		total_amt,
	bl.note 					AS 		item_note,*/
	
	private Double qty;
	private Double qty2;
	private Double price;
	private Double price2;
	private Double total_amt;
	private Double total_amt2;
	private String item_note;
	
	public UserStockItemModel() {
		
	}
	
	public String getItemImgPath()
	{
		if (itemImgPath != null){
			return itemImgPath;
		}else{
			return "";
		}
	}
	
	public String getStockMarkName()
	{
		return StringUtils.equals(stockMark, Constants.CONST_Y)?"是":"否";
	}
	
	public String getStateName()
	{
		return StringUtils.equals(state, Constants.CONST_STATE_Y)?"正常":"禁用";
	}
	
	public String get(String paramName) throws Exception
	{
		String data = null;
		boolean oriAccessible = true;
        Field field = this.getClass().getDeclaredField(paramName);
        if(!field.isAccessible()){
            field.setAccessible(true);
            oriAccessible = false;
        }
        if(field.getType().equals(String.class)){
            data = (String)field.get(this);
        }
        if (!oriAccessible) {
            field.setAccessible(oriAccessible);
        }
        
        return (data != null)? data : "";
	}
}
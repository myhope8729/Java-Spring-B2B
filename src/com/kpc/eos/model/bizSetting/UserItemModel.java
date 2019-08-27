
package com.kpc.eos.model.bizSetting;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.apache.commons.lang.StringUtils;

import lombok.Data;

import com.kpc.eos.core.Constants;
import com.kpc.eos.core.model.CommonModel;
import com.kpc.eos.core.util.FileUtil;
import com.kpc.eos.core.util.FormatUtil;
import com.kpc.eos.core.validation.FormErrors;
import com.kpc.eos.core.validation.FormValidationUtils;

@Data
public  class UserItemModel extends CommonModel {
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
	
	private String firstpageItemId;
	
	private String itemNo;
	
	private	String chelp;
	
	public void setChelp(){
		StringBuilder strBuild = new StringBuilder();
		if (StringUtils.isNotEmpty(this.c1)){
			strBuild.append(this.c1).append(" ");
		}
		if (StringUtils.isNotEmpty(this.c2)){
			strBuild.append(this.c2).append(" ");
		}
		if (StringUtils.isNotEmpty(this.c3)){
			strBuild.append(this.c3).append(" ");
		}
		if (StringUtils.isNotEmpty(this.c4)){
			strBuild.append(this.c4).append(" ");
		}
		if (StringUtils.isNotEmpty(this.c5)){
			strBuild.append(this.c5).append(" ");
		}
		if (StringUtils.isNotEmpty(this.c6)){
			strBuild.append(this.c6).append(" ");
		}
		if (StringUtils.isNotEmpty(this.c7)){
			strBuild.append(this.c7).append(" ");
		}
		if (StringUtils.isNotEmpty(this.c8)){
			strBuild.append(this.c8).append(" ");
		}
		if (StringUtils.isNotEmpty(this.c9)){
			strBuild.append(this.c9).append(" ");
		}
		if (StringUtils.isNotEmpty(this.c10)){
			strBuild.append(this.c10).append(" ");
		}
		if (StringUtils.isNotEmpty(this.c11)){
			strBuild.append(this.c11).append(" ");
		}
		if (StringUtils.isNotEmpty(this.c12)){
			strBuild.append(this.c12).append(" ");
		}
		if (StringUtils.isNotEmpty(this.c13)){
			strBuild.append(this.c13).append(" ");
		}
		if (StringUtils.isNotEmpty(this.c14)){
			strBuild.append(this.c14).append(" ");
		}
		if (StringUtils.isNotEmpty(this.c15)){
			strBuild.append(this.c15).append(" ");
		}
		if (StringUtils.isNotEmpty(this.c16)){
			strBuild.append(this.c16).append(" ");
		}
		if (StringUtils.isNotEmpty(this.c17)){
			strBuild.append(this.c17).append(" ");
		}
		if (StringUtils.isNotEmpty(this.c18)){
			strBuild.append(this.c18).append(" ");
		}
		if (StringUtils.isNotEmpty(this.c19)){
			strBuild.append(this.c19).append(" ");
		}
		if (StringUtils.isNotEmpty(this.c20)){
			strBuild.append(this.c20).append(" ");
		}
		
		this.chelp = strBuild.toString();
	}
	
	
	private String storeId;
	private String vendorId;
	private String vendor;
	private String state;
	private String stateName;
	private String stockMark;
	private String stockMarkName;
	private String note;
	private String itemImgPath; // Original Image
	private String itemSmallImg; // Small Image - get from /useritem/small
	private String itemMediumImg; // Medium Image - get from /useritem/medium
	private String itemBigImg; // Medium Image - get from /useritem/big
	private String priceIn;
	private String lastVendorId;
	
	private String cost;
	private String jsQty;
	private String jsYn;
	
	// user defined value.
	private String stockQty;
	
	// add for shopcart
	private String itemCode;
	private String itemPrice;
	private String itemName;
	private String itemPackageCnt;
	private String itemUnit;
	private String cartQty;
	
	// ----- order -----------------
	private Double qty;
	private Double qty2;
	private Double price;
	private Double price2;
	private Double total_amt;
	private Double total_amt2;
	private String item_note;
	
	private Double js_qty;
	private String js_display;
	
	// ----------- Front End -------------------/
	private String priceCol;
	private String hcConnected;	// if host and cust are connected?
	
	private String picShowMode;	// show mode for an item picture.
	
	
	public String getJs_display()
	{
		if (js_display == null) return "";
		js_display = js_display.trim();
		if (js_display.length() > 1) {
			return js_display;
		}else {
			return "";
		}
	}
	
	// in eos_custprice_new table. This field will be override the default price value if it is not empty.
	private String custPrice;
	
	public UserItemModel() {
		
	}
	
	public String getItemImgPath()
	{
		if (StringUtils.isNotEmpty(itemImgPath) && FileUtil.checkFileExist("useritem", itemImgPath)){
			return itemImgPath;
		}else{
			return "";
		}
	}
	
	public String getItemBigImg()
	{
		return FileUtil.getItemImgUrl("big", itemImgPath);
	}
	
	public String getItemMediumImg()
	{
		return FileUtil.getItemImgUrl("medium", itemImgPath);
	}
	
	public String getItemSmallImg()
	{
		return FileUtil.getItemImgUrl("small", itemImgPath);
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
		if (StringUtils.isEmpty(paramName)) return "";
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
	
	public void set(String paramName, String paramValue) throws Exception
	{
		Method[] methods = this.getClass().getMethods();
		for (Method method : methods ) {
			if(method.getName().equals("set"+FormatUtil.toFirstUpperCase(paramName))) {
				Class<?> []ptype = method.getParameterTypes();
				if(ptype.length == 1) {
					if (ptype[0] == String.class) {
						method.invoke(this, paramValue);
					}
				}
			}
		}
	}
	
	public FormErrors validate()
	{
		FormErrors errors = new FormErrors(this, "target");
		FormValidationUtils fv = new FormValidationUtils(errors);
		return errors;
	}
}
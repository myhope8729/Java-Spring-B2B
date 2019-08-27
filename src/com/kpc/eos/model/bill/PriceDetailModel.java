
package com.kpc.eos.model.bill;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.apache.commons.lang.StringUtils;

import lombok.Data;

import com.kpc.eos.core.model.CommonModel;
import com.kpc.eos.core.util.FileUtil;
import com.kpc.eos.core.util.FormatUtil;

@Data
public class PriceDetailModel extends CommonModel 
{
	private static final long serialVersionUID = -754189837422039796L;
	
	private String billId;
	private String itemId;
	private String cost;
	private String price;
	private String qty;
	private String total;
	private String priceIn;
	private String d11;
	private String d12;
	private String d21;
	private String d22;
	private String d31;
	private String d32;
	private String d41;
	private String d42;
	private String d51;
	private String d52;
	private String note;
	
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
	private String itemImgPath;
	private String itemSmallImg;
	private String itemMediumImg;
	
	public String getItemImgPath()
	{
		if (StringUtils.isNotEmpty(itemImgPath) && FileUtil.checkFileExist("useritem", itemImgPath)){
			return itemImgPath;
		}else{
			return "";
		}
	}
	
	public String getItemMediumImg()
	{
		return FileUtil.getItemImgUrl("medium", itemImgPath);
	}
	
	public String getItemSmallImg()
	{
		return FileUtil.getItemImgUrl("small", itemImgPath);
	}
	
	public PriceDetailModel() 
	{
		
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

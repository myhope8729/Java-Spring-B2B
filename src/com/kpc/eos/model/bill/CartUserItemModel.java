/**
 * Filename		: CartUserItemModel.java
 * Description	:
 * 
 * 2017
 */
package com.kpc.eos.model.bill;

import lombok.Data;

import com.kpc.eos.core.model.BaseModel;
import com.kpc.eos.core.model.CommonModel;

/**
 * Filename		: CartUserItemModel.java
 * Description	:
 * 2017
 * @author		: RKRK
 */
@Data
public class CartUserItemModel extends BaseModel 
{
	private String itemId;
	
	private Double qty;
	
	private Double cost;
	
	private Double price;
	
	private Double total;
	
	private String note;
	
	private Double jsQty;
	private String jsDisplay;
}


package com.kpc.eos.model.billProcMng;

import lombok.Data;

import com.kpc.eos.core.model.CommonModel;

@Data
public class SupplyStatisticModel extends CommonModel 
{
	private static final long serialVersionUID = -7814456124397067987L;
	
	private String hostUserId;
	private String custShortName;
	private String custUserId;
	private String itemId;
	private String qty;
	private String total;
	private String arriveDate;
	private String salePrice;
	private String lastPriceIn;
	private String lastVendorName;
	
	private String groupName;
	
	private String itemNo;
	private String itemName;
	private String unit;
	
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
	
	public SupplyStatisticModel() {
		
	}
	
}
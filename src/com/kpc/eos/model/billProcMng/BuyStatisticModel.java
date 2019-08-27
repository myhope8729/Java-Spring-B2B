
package com.kpc.eos.model.billProcMng;

import java.util.List;

import lombok.Data;

import com.kpc.eos.core.model.CommonModel;

@Data
public class BuyStatisticModel extends CommonModel 
{
	private static final long serialVersionUID = -7814456124397067987L;
	
	private String itemId;
	private String itemNo;
	private String itemName;
	private String distributeName;
	private String custShortName;
	private String carNo;
	private String qty;
	private String unit;
	private String totalQty;
	
	private String inQty;
	private String inPrice;
	private String salePrice;
	
	private Integer groupCnt;
	
	private List<BuyStatisticModel> buyStatisticInfo;
	
	public BuyStatisticModel() {
		
	}
	
}
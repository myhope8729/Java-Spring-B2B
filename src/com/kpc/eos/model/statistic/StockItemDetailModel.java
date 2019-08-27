
package com.kpc.eos.model.statistic;

import org.apache.commons.lang.StringUtils;

import lombok.Data;

import com.kpc.eos.core.model.CommonModel;
import com.kpc.eos.core.util.CodeUtil;

@Data
public class StockItemDetailModel extends CommonModel 
{
	private static final long serialVersionUID = -399857995844056787L;
	
	private String billId;
	private String itemId;
	private String billType;
	private String billTypeName;
	private String hostUserId;
	private String hostUserName;
	private String custUserId;
	private String custUserName;
	private String price2;
	private String qty;
	private String qty2;
	private String tot;
	private String tot2;
	private String jsYn;
	private String jsQty;
	private String note;
	private String itemName;
	
	
	public String getBillTypeName() {
		if(StringUtils.isEmpty(this.billType)) return "";
		else return CodeUtil.getCodeName(this.billType);
	}

	public StockItemDetailModel() 
	{
		
	}
}

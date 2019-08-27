
package com.kpc.eos.model.bill;

import lombok.Data;

import com.kpc.eos.core.model.CommonModel;

@Data
public class SubPayTypeModel extends CommonModel 
{
	// modeling for eos_pdat_line_new table.
	private String userId;
	private String codeId;
	
	private String name;
	private String custtypeId;
	private String paytypeId;
	private String topupAmt;
	private String bonusAmt;
	private String seqNo;
	
	public SubPayTypeModel() 
	{
		
	}
}

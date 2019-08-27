
package com.kpc.eos.model.bizSetting;

import lombok.Data;

import com.kpc.eos.core.Constants;
import com.kpc.eos.core.model.BaseModel;
import com.kpc.eos.core.model.PagingModel;
import com.kpc.eos.model.common.DefaultSModel;

@Data
public class PayTypeSModel extends DefaultSModel {
	
	private String userId;
	private String custUserId;
	private String state = Constants.CONST_STATE_Y;
	private String privYn;
	private String prepayYn;
	private String brand;
	
	private String custtypeId;
	
	private String paytypeId;
	private String seqNo; // seqNo in eos_pdat_line table.
	
	private String isGroup;
	
	public PayTypeSModel(String userId, String custUserId)
	{
		this.userId = userId;
		this.custUserId = custUserId;
	}
}
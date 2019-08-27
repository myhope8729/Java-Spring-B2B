
package com.kpc.eos.model.bill;

import lombok.Data;

import com.kpc.eos.core.model.CommonModel;
import com.kpc.eos.model.common.DefaultSModel;

@Data
public class InfoDetailSModel extends DefaultSModel 
{
	private String userId;
	private String billId;
	private String lineId;
	private String showYn;
	
	public InfoDetailSModel()
	{
		
	}
	
	public InfoDetailSModel(InfoModel info)
	{
		this.userId = info.getUserId();
		this.billId = info.getBillId();
		this.lineId = info.getLineId();
	}
}

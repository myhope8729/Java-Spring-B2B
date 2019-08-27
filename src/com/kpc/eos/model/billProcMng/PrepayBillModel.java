
package com.kpc.eos.model.billProcMng;

import lombok.Data;

import com.kpc.eos.core.model.CommonModel;

@Data
public class PrepayBillModel extends CommonModel {
	private static final long serialVersionUID = 7486105554599469555L;
	
	private String payType2;
	private String totalAmt;
	
	public PrepayBillModel() {
		
	}
}
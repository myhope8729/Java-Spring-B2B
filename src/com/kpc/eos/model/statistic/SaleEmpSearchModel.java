package com.kpc.eos.model.statistic;

import lombok.Data;

import com.kpc.eos.core.model.BaseModel;

public @Data class SaleEmpSearchModel extends BaseModel {
	
	private static final long serialVersionUID = -4953841389355625417L;
	
	private String empName;
	private String empId;
	private String empNo;
	private String total2;
	private String cnt;
	
	public SaleEmpSearchModel() {
		
	}
	
}

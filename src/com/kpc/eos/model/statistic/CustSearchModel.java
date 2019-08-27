package com.kpc.eos.model.statistic;

import lombok.Data;

import com.kpc.eos.core.model.BaseModel;

public @Data class CustSearchModel extends BaseModel {
	
	private static final long serialVersionUID = -4953841389355625417L;
	
	private String custUserName;
	private String address;
	private String contactName;
	private String telNo;
	private String note;
	private String custTypeId;
	private String custTypeName;
	private String createDate;
	
	
	public CustSearchModel() {
		
	}
	
}

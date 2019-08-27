package com.kpc.eos.model.statistic;

import lombok.Data;

import com.kpc.eos.core.model.BaseModel;

public @Data class StatisticTotalModel extends BaseModel {
	
	private static final long serialVersionUID = -4953841389355625417L;
	
	private Integer totalCnt;
	private Integer totalRecords;
	private String totalAmt;
	private String amt1;
	private String amt2;
	
	public Integer getTotalCnt() {
		if(this.totalCnt == null) return 0;
		else return this.totalCnt;
	}
	
	public Integer getTotalRecords() {
		if(this.totalRecords == null) return 0;
		else return this.totalRecords;
	}	
	
	public StatisticTotalModel() {
		
	}
	
}

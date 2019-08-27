
package com.kpc.eos.model.bizSetting;

import lombok.Data;

import com.kpc.eos.core.model.CommonModel;

@Data
public class BizDataModel extends CommonModel {
	
	private static final long serialVersionUID = 2841055367814668819L;
	
	private String codeId;
	private String seqNo;
	
	private String bizDataName;
	private String c1;
	private String c2;
	private String c3;
	private String c4;
	private String c5;
	private String d1;
	private String d2;
	private String d3;
	private String d4;
	private String d5;
		
	public BizDataModel() {
		
	}
}
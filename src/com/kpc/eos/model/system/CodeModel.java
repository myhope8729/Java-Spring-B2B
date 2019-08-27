
package com.kpc.eos.model.system;

import lombok.Data;

import com.kpc.eos.core.model.BaseModel;

@Data
public class CodeModel extends BaseModel {

	private static final long serialVersionUID = 6030146558338278789L;

	private String updateStatus;
	private String codeId;
	private String codeGroupId;
	private String codeName;
	private String codeDesc;
	private String upperCodeId;
	private String upperCodeName;
	private String c1;
	private String c2;
	private String c3;
	private String c4;
	private String c5;
	private String c6;
	private String c7;
	private String c8;
	private String c9;
	private String c10;
	private String useYn;
	private String state;
    private String crud;
    
	public CodeModel() {}
	
}

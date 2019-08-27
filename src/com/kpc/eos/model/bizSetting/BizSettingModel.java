
package com.kpc.eos.model.bizSetting;

import lombok.Data;

import com.kpc.eos.core.model.CommonModel;

@Data
public class BizSettingModel extends CommonModel {
	
	private static final long serialVersionUID = 2841055367814668819L;
	
	private String userId;
	private String sysCode;
	private String sysType;
	private String sysValue;
	private String sysValueName;
	private String sysName;
	private String sysKindCd;
	private String sysKindName;
	
	public BizSettingModel() {
		
	}
}
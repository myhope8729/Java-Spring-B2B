
package com.kpc.eos.model.bizSetting;

import lombok.Data;

import com.kpc.eos.core.model.CommonModel;

@Data
public class CustomerModel extends CommonModel {
	
	private static final long serialVersionUID = -5568056808014410307L;
	
	private String custId;
	private String custShortName;
	private String checked;
	
	public CustomerModel() {
		
	}
}
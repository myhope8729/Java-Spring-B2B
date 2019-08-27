
package com.kpc.eos.model.bizSetting;

import lombok.Data;

import com.kpc.eos.core.model.CommonModel;

@Data
public class EmployeeModel extends CommonModel {
	
	private static final long serialVersionUID = -5568056808014410307L;
	
	private String empId;
	private String empName;
	private String checked;
	
	public EmployeeModel() {
		
	}
}
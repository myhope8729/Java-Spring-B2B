
package com.kpc.eos.model.bizSetting;

import lombok.Data;

import com.kpc.eos.core.model.CommonModel;
import com.kpc.eos.core.validation.FormErrors;
import com.kpc.eos.core.validation.FormValidationUtils;

@Data
public class StoreModel extends CommonModel {
	
	private static final long serialVersionUID = 5110546420068900605L;
	
	private String storeId;
	private String userId;
	private String deptId;
	private String deptNo;
	private String deptName;
	private String storeNo;
	private String storeName;
	private String note;
	
	public FormErrors validate()
	{
		FormErrors errors = new FormErrors(this, "target");
		FormValidationUtils fv = new FormValidationUtils(errors);
		
		fv.rejectIfEmptyOrWhitespace(new String[]{"storeNo"}, "system.common.valid.error.required", "Field");
				
		return errors;
	}
	
	private String deptFullName;
	public String getDeptFullName(){
		return "(" + this.deptNo + ") " + this.deptName;
	}
	public StoreModel() {
		
	}
}
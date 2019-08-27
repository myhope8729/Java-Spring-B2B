
package com.kpc.eos.model.bizSetting;

import java.util.List;

import lombok.Data;

import com.kpc.eos.core.model.CommonModel;
import com.kpc.eos.core.validation.FormErrors;
import com.kpc.eos.core.validation.FormValidationUtils;

@Data
public class CustTypeModel extends CommonModel {
	private static final long serialVersionUID = -3882405032578859577L;
	
	private String custTypeId;
	private String custTypeName;
	private String note;
	private String psql;
	
	public FormErrors validate()
	{
		FormErrors errors = new FormErrors(this, "target");
		FormValidationUtils fv = new FormValidationUtils(errors);
		
		fv.rejectIfEmptyOrWhitespace(new String[]{"custTypeName"}, "system.common.valid.error.required", "Field");
		
		return errors;
	}
	
	public CustTypeModel() {
		
	}
	
	// pre pay list
	private List<PayTypeModel> ppList = null;
	
	public void setPpList(List<PayTypeModel> ppList)
	{
		this.ppList = ppList;
	}
}
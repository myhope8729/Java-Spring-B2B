
package com.kpc.eos.model.dataMng;

import org.apache.commons.lang.StringUtils;

import lombok.Data;

import com.kpc.eos.core.Constants;
import com.kpc.eos.core.model.CommonModel;
import com.kpc.eos.core.validation.FormErrors;
import com.kpc.eos.core.validation.FormValidationUtils;

@Data
public class DepartmentModel extends CommonModel {

	private static final long serialVersionUID = -331200737922042027L;
	
	private String deptId;

	private String accountYn = "N";

	private String address;

	private String createBy;

	private String createDate;

	private String deptName;

	private String deptNo;

	private String note;

	private Integer oldDeptId;

	private String telNo;

	private String upperDeptId;

	private String userId;
	private String deptFullName;
	
	private String accountYnName;
	
	public String getAccountYnName(){
		return StringUtils.equals(accountYn, Constants.CONST_Y)?"是":"";
	}
	
	public String getDeptFullName(){
		return "(" + deptNo + ") " + deptName;
	}
	private String codeName;
	
	public DepartmentModel() {
		
	}
	
	public FormErrors validate()
	{
		FormErrors errors = new FormErrors(this, "target");
		FormValidationUtils fv = new FormValidationUtils(errors);
		
		fv.rejectIfEmptyOrWhitespace(new String[]{"deptNo"}, "system.common.valid.error.required", "Field");
		
		return errors;
	}
	
	
}
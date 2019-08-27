
package com.kpc.eos.model.bizSetting;

import lombok.Data;

import org.apache.commons.lang.StringUtils;

import com.kpc.eos.core.Constants;
import com.kpc.eos.core.model.CommonModel;
import com.kpc.eos.core.validation.FormErrors;
import com.kpc.eos.core.validation.FormValidationUtils;

@Data
public class PayTypeModel extends CommonModel {
	private static final long serialVersionUID = 6007803937496454729L;
	
	private String payTypeId;
	private String payTypeName;
	private String prePayYn;
	private String prePayYnName;
	private String privYn;
	private String privYnName;
	private String payTypeChecked;
	private String weixinYn;
	private String weixinYnName;
	
	private String note;
	private String brand;
	
	// eos_pdat_line fields
	private String c1;
	private String c2;
	private String c3;
	private String c4;
	private String c5;
	
	private String seqNo;
	
	private String payTypeNameDetail;
	
	public String getPrePayYnName(){
		return StringUtils.equals(prePayYn, Constants.CONST_Y)?"是":"否";
	}
	public String getPrivYnName(){
		return StringUtils.equals(privYn, Constants.CONST_Y)?"是":"否";
	}
	public String getWeixinYnName(){
		return StringUtils.equals(weixinYn, Constants.CONST_Y)?"是":"否";
	}
	
	public FormErrors validate()
	{
		FormErrors errors = new FormErrors(this, "target");
		FormValidationUtils fv = new FormValidationUtils(errors);
		
		fv.rejectIfEmptyOrWhitespace(new String[]{"payTypeName"}, "system.common.valid.error.required", "Field");
		
		return errors;
	}
	
	public PayTypeModel() {
		
	}
}
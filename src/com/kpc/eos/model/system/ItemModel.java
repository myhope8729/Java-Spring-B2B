
package com.kpc.eos.model.system;

import org.apache.commons.lang.StringUtils;

import lombok.Data;

import com.kpc.eos.core.model.CommonModel;
import com.kpc.eos.core.util.FileUtil;
import com.kpc.eos.core.validation.FormErrors;
import com.kpc.eos.core.validation.FormValidationUtils;

/**
 * Filename		: ItemModel.java
 * Description	: menu model
 * 2017
 * @author		: RKRK
 */

@Data
public class ItemModel extends CommonModel {
	private static final long serialVersionUID = 527002470234026920L;
	
	private String itemId;
	private String itemNo;
	private String itemName;
	private String cat1;
	private String cat2;
	private String barCode;
	private String unit;
	private String manufacturer;
	private String standard;
	
	private String itemImg;
	private String itemSmallImg; // Small Image - get from /useritem/small
	private String itemMediumImg; // Medium Image - get from /useritem/medium
	
	public ItemModel() {}
	
	public String getItemImg()
	{
		if (itemImg != null && FileUtil.checkFileExist("useritem", itemImg)){
			return itemImg;
		}else{
			return "";
		}
	}
	
	public FormErrors validate()
	{
		FormErrors errors = new FormErrors(this, "target");
		FormValidationUtils fv = new FormValidationUtils(errors);
		fv.rejectIfEmptyOrWhitespace(new String[]{"itemNo"}, "system.common.valid.error.required", "Field");
		return errors;
	}
	
	public String getItemMediumImg()
	{
		return FileUtil.getItemImgUrl("medium", itemImg);
	}
	
	public String getItemSmallImg()
	{
		return FileUtil.getItemImgUrl("small", itemImg);
	}
	
	private String chelp;
	
	public void setChelp(){
		StringBuilder strBuild = new StringBuilder();
		if (StringUtils.isNotEmpty(this.itemNo)){
			strBuild.append(this.itemNo).append(" ");
		}
		if (StringUtils.isNotEmpty(this.itemName)){
			strBuild.append(this.itemName).append(" ");
		}
		if (StringUtils.isNotEmpty(this.cat1)){
			strBuild.append(this.cat1).append(" ");
		}
		if (StringUtils.isNotEmpty(this.cat2)){
			strBuild.append(this.cat2).append(" ");
		}
		if (StringUtils.isNotEmpty(this.barCode)){
			strBuild.append(this.barCode).append(" ");
		}
		if (StringUtils.isNotEmpty(this.unit)){
			strBuild.append(this.unit).append(" ");
		}
		if (StringUtils.isNotEmpty(this.manufacturer)){
			strBuild.append(this.manufacturer).append(" ");
		}
		if (StringUtils.isNotEmpty(this.standard)){
			strBuild.append(this.standard).append(" ");
		}
		this.chelp = strBuild.toString();
	}
	
}
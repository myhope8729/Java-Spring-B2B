
package com.kpc.eos.model.bizSetting;

import org.apache.commons.lang.StringUtils;

import lombok.Data;

import com.kpc.eos.core.Constants;
import com.kpc.eos.core.model.CommonModel;
import com.kpc.eos.core.util.CodeUtil;

@Data
public  class UserItemPropertyModel extends CommonModel {
	private static final long serialVersionUID = -1113409083374973165L;
	
	private String userId;
	private String cno;
	private String seqNo;
	private String propertyName;
	private String propertyDesc;
	private String propertyTypeCd;
	private String propertyTypeName;
	private String propertyValue;
	private String propertyValueName;
	private String displayYn;
	private String displayYnName;
	private String printYn;
	private String printYnName;
	private String state;
	private String stateName;
	
	private String userData;
	
	public UserItemPropertyModel() {
		
	}
	
	public String getPropertyValueName()
	{
		return StringUtils.equals(propertyValue, Constants.CONST_Y)?"默认价格":StringUtils.equals(propertyTypeCd, "PT0002")?"价格":"";
	}
	
	public String getDisplayYnName()
	{
		return StringUtils.equals(displayYn, Constants.CONST_Y)?"是":"否";
	}
	
	public String getPrintYnName()
	{
		return StringUtils.equals(printYn, Constants.CONST_Y)?"是":"否";
	}
	
	public String getStateName()
	{
		return StringUtils.equals(state, Constants.CONST_STATE_Y)?"正常":"禁用";
	}
	
	public String getPropertyTypeName()
	{
		return StringUtils.isNotEmpty(propertyTypeCd)?CodeUtil.getCodeName(propertyTypeCd):"";
	}
}
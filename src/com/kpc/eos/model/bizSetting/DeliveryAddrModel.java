
package com.kpc.eos.model.bizSetting;

import lombok.Data;

import org.apache.commons.lang.StringUtils;

import com.kpc.eos.core.Constants;
import com.kpc.eos.core.model.CommonModel;

@Data
public class DeliveryAddrModel extends CommonModel {
	private static final long serialVersionUID = -3882405032578859577L;
	
	private String userId;
	private String addrId;
	
	private String locationId;
	private String provinceId;
	private String cityId;
	private String districtId;
	private String provinceLevel;
	private String locationName;
	
	private String address;
	private String contactName;
	private String telNo;
	private String defaultYn;
	private String defaultYnName;
	private String chelp;
	
	private String mobileNo;
	private String qqNo;
	
	private String note;
	
	public String getDefaultYnName(){
		return StringUtils.equals(defaultYn, Constants.CONST_Y)?"是":"";
	}
	public String getDefaultYn(){
		return StringUtils.isEmpty(defaultYn)?"N":defaultYn;
	}
	public String getChelp(){
		return address + contactName + telNo + note;
	}
	
	
	public DeliveryAddrModel() {
		
	}
}
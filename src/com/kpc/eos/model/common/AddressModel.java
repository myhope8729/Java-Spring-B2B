package com.kpc.eos.model.common;

import org.apache.commons.lang.StringUtils;

import lombok.Data;

import com.kpc.eos.core.model.CommonModel;

@Data
public class AddressModel extends CommonModel {
	private static final long serialVersionUID = 4530658521304698244L;
	private String addressId;
	
	private String addressName;
	
	private String upperAddress;
	
	private String addressLevel;
	
	public AddressModel() {}
	
	public String getAddressId() {
		return addressId;
	}
	
	public String getAddressName() {
		return addressName;
	}
	
	public String getAddressLevel(){
		return addressLevel;
	}
	
	public AddressModel clone()
	{
		AddressModel ret = new AddressModel();
		ret.locationId = this.locationId;
		
		ret.addressId = this.addressId;
		ret.addressLevel = this.addressLevel;
		ret.addressName = this.addressName;
		
		ret.fullLocationName = this.fullLocationName;
		ret.level0Id = this.level0Id;
		ret.level0Name = this.level0Name;
		ret.level1Id = this.level1Id;
		ret.level1Name = this.level1Name;
		ret.level2Id = this.level2Id;
		ret.level2Name = this.level2Name;
		
		ret.upperAddress = this.upperAddress;
		
		return ret;
	}
	
	public AddressModel reArrange()
	{
		AddressModel ret = this.clone();
		
		
		if (StringUtils.isEmpty(this.level2Id))
		{
			ret.setLevel2Id(this.level1Id);
			ret.setLevel2Name(this.level1Name);
			
			ret.setLevel1Id(this.level0Id);
			ret.setLevel1Name(this.level0Name);
			
			ret.setLevel0Id("");
			ret.setLevel0Name("");
		}
		
		if (StringUtils.isEmpty(ret.getLevel1Id()))
		{
			ret.setLevel1Id(this.level0Id);
			ret.setLevel1Name(this.level0Name);
			
			ret.setLevel0Id("");
			ret.setLevel0Name("");
		}
		
		// again
		if (StringUtils.isEmpty(ret.getLevel2Id()))
		{
			ret.setLevel2Id(ret.getLevel1Id());
			ret.setLevel2Name(ret.getLevel1Name());
			
			ret.setLevel1Id("");
			ret.setLevel1Name("");
			
			ret.setLevel0Id("");
			ret.setLevel0Name("");
		}
		
		return ret;
	}
	
	private String locationId;
	private String level2Id;
	private String level1Id;
	private String level0Id;
	
	private String level2Name;
	private String level1Name;
	private String level0Name;
	private String fullLocationName;
	
	public String getFullAddress()
	{
		StringBuilder sb = new StringBuilder();
		if (StringUtils.isNotEmpty(level2Name)) sb.append(level2Name);
		if (StringUtils.isNotEmpty(level1Name)) sb.append(level1Name);
		if (StringUtils.isNotEmpty(level0Name)) sb.append(level0Name);
		
		return sb.toString();
	}
}

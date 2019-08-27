package com.kpc.eos.model.bizSetting;

import lombok.Data;

import com.kpc.eos.model.common.DefaultSModel;


@Data
public class UserItemSModel extends DefaultSModel 
{
	private static final long serialVersionUID = 1L;

	private String category;
	private String catFieldName;
	private String chelp;
	private String userId;
	private String groupChelp;
	private String groupId;
	
	private String category2;
	private String catFieldName2;
	
	private String brand;
	
	private String psql;
	private String itype;
	private String paytypeId;
	private String paymentType;
	private String hbmark;
	
	// for custprice_new table. We will select the specific price from this table.
	// In order page, we may use the price in eos_custprice_new table if exists.
	private String custUserId;
	
}

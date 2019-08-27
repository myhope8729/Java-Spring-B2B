/**
 * Filename		: SubPayTypeSModel.java
 * Description	:
 * 
 * 2017
 */
package com.kpc.eos.model.bill;

import com.kpc.eos.model.common.DefaultSModel;

import lombok.Data;

/**
 * Filename		: SubPayTypeSModel.java
 * Description	:
 * 2017
 * @author		: RKRK
 */
@Data
public class SubPayTypeSModel
{
	private String name;
	private String custtypeId;
	private String paytypeId;
	private String seqNo;
	private String userId;
	
	
	public SubPayTypeSModel()
	{
		
	}
	
	public SubPayTypeSModel(String userId, String custtypeId)
	{
		this.userId = userId;
		this.custtypeId = custtypeId;
	}
}

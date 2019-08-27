/**
 * Filename		: BillWorkflowSModel.java
 * Description	:
 * 
 * 2017
 */
package com.kpc.eos.model.bill;

import lombok.Data;

/**
 * Filename		: BillWorkflowSModel.java
 * Description	:
 * 2017
 * @author		: RKRK
 */
@Data
public class BillWorkflowSModel
{
	private String custUserId;
	private String custWfType;
	private String hostUserId;
	private String hostWfType;
	
	public BillWorkflowSModel()
	{
		
	}
	
	public BillWorkflowSModel(String userId, String wfType, boolean isCust)
	{
		if (isCust)
		{
			this.custUserId = userId;
			this.custWfType = wfType;
		}
		else 
		{
			this.hostUserId = userId;
			this.hostWfType = wfType;
		}
	}
	
	public BillWorkflowSModel(String custUserId, String custWfType, String hostUserId, String hostWfType )
	{
		this.custUserId = custUserId;
		this.custWfType = custWfType;
		this.hostUserId = hostUserId;
		this.hostWfType = hostWfType;
	}
}

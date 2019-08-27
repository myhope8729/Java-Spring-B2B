/**
 * Filename		: PrepayBillSModel.java
 * Description	:
 * 
 * 2017
 */
package com.kpc.eos.model.billProcMng;

import lombok.Data;

import com.kpc.eos.core.Constants;
import com.kpc.eos.model.common.DefaultSModel;

/**
 * Filename		: PrepayBillSModel.java
 * Description	:
 * 2017
 * @author		: RKRK
 */
@Data
public class PrepayBillSModel extends DefaultSModel
{
	private String hostUserId;
	private String custUserId;
	private String paytypeId;		// paymentType Id
	private String paytype2;		// pre-payment name 
	
	public PrepayBillSModel()
	{
		super();
	}
	
	public PrepayBillSModel(String hostUserId, String custUserId, String paytypeId, String paytype2)
	{
		super();
		this.hostUserId = hostUserId;
		this.custUserId = custUserId;
		this.paytypeId = paytypeId;
		this.paytype2 = paytype2;
	}
}

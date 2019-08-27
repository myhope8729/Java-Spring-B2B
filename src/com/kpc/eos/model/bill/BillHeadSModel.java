/**
 * Filename		: OrderSModel.java
 * Description	:
 * 
 * 2017
 */
package com.kpc.eos.model.bill;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import lombok.Data;

import com.kpc.eos.model.common.DefaultSModel;

/**
 * Filename		: OrderSModel.java
 * Description	:
 * 2017
 * @author		: RKRK
 */

public @Data class BillHeadSModel  extends DefaultSModel
{
	private static final long serialVersionUID = -3746670981419820170L;

	
	// Search conditions in form
	private String billId;
	
	private String billType;
	
	private String createDateFrom;
	private String createDateTo;
	
	private String arriveDateFrom;
	private String arriveDateTo;
	
	private String state;
	
	// vendorName in order, custName in sale.
	private String vendorName;
	
	// search conditions
	private String inputorId;
	private String hostUserId;
	private String custUserId;
	
	// search condition for host or cust name in return bill
	private String hostCustName;
	
	// Search for an info bill
	private String itype;
	private String chelp;
	private String info;
	
	public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}

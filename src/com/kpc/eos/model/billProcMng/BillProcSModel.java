
package com.kpc.eos.model.billProcMng;

import lombok.Data;

import com.kpc.eos.model.common.DefaultSModel;

@Data
public class BillProcSModel extends DefaultSModel {
	
	private static final long serialVersionUID = -8530620942874064064L;
	
	private String checked;
	private String billType;
	private String billId;
	private String createDateFrom;
	private String createDateTo;
	private String createDateFrom1;
	private String createDateTo1;
	private String receiptFlag;
	
	private String compName;
	
	private String empId;
	private String itemId;
	private String itemNo;
	
	private String itemColNo;
	private String itemColName;
	
	private String distributeSeqNo;
	private String storeId;
	
	
	public BillProcSModel() {
		
	}
}
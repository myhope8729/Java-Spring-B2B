
package com.kpc.eos.model.bizSetting;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import lombok.Data;

import com.kpc.eos.core.model.CommonModel;

@Data
public class HostCustModel extends CommonModel {
	private static final long serialVersionUID = -3046828204690483170L;
	
	private List<String> payTypeId;
	private String hostUserId;
	private String hostUserNo;
	private String hostUserName;
	private String hostLocationName;
	private String hostContactName;
	private String hostTelNo;
	private String hostMobileNo;
	private String hostAddress;
	
	private String custUserId;
	private String custUserNo;
	private String custUserName;
	private String custUserFullName;
	private String custLocationName;
	private String custContactName;
	private String custTelNo;
	private String custMobileNo;
	private String custAddress;
	
	private String custTypeId;
	private String custTypeName;
	private String distributeSeqNo;
	private String distributeName;
	private String distributeNo;
	
	private String priceColSeqNo;
	private String priceColName;
	private String priceDesc;
	private String clerkNo;
	private String empId;
	private String carSeqNo;
	private String carNo;
	private String custShortName;
	private String copyCustId;
	
	private String selectedRows;
	
	private String bizAreaType;
	
	private String psql;
	
	private String qty;
	
	// psql in eos_custtype_new table.
	private String custtypePsql;
	
	public String getCustUserFullName(){
		return custUserName + " - " + custUserNo;
	}
	
	private boolean connection;
	public boolean getConnection(){
		return !(StringUtils.isEmpty(priceColName) || StringUtils.isEmpty(priceDesc) || StringUtils.isEmpty(custTypeId));
	}
	
	public HostCustModel() {
		
	}
}
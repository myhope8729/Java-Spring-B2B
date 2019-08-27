
package com.kpc.eos.model.statistic;

import org.apache.commons.lang.StringUtils;

import lombok.Data;

import com.kpc.eos.core.model.CommonModel;
import com.kpc.eos.core.util.CodeUtil;

@Data
public class BillSearchModel extends CommonModel 
{
	private static final long serialVersionUID = -8849164089541775073L;
	
	private String billId;
	private String billType;
	private String billTypeName;
	private String rbillType;
	private String rbillId;
	private String arriveDate;
	private String custUserId;
	private String custUserNo;
	private String custUserName;
	private String custShortName;
	private String custContactName;
	private String custTelNo;
	private String custQqNo;
	private String custMobileNo;
	private String hostUserId;
	private String hostUserNo;
	private String hostUserName;
	private String hostContactName;
	private String hostTelNo;
	private String hostQqNo;
	private String hostMobileNo;
	private String addrId;
	private String addrLocationId;
	private String addrLocationName;
	private String addrAddress;
	private String addrContactName;
	private String addrTelNo;
	private String addrQq;
	private String addrMobile;
	private String paytypeId;
	private String paytypeName;
	private String paymentType;
	private String payState;
	private String payStateName;
	private String weixinYn;
	private String itemAmt;
	private String freightAmt;
	private String totalAmt;
	private String itemamount2;
	private String freightamount2;
	private String total2;
	private String inputorId;
	private String inputorName;
	private String managerId;
	private String managerName;
	private String custtypeId;
	private String custtypeName;
	private String note;
	private String brand;
	private String bnoUser;
	private String deptId;
	private String deptNo;
	private String deptName;
	private String storeId;
	private String storeName;
	private String storeNote;
	private String deskno;
	private String rbnoUser;
	private String itemtype;
	private String itype;
	private String hostLocationName;
	private String hostAddress;
	private String hosttopic;
	private String epaytype;
	private String freighttype;
	private String freight;
	private String webuno;
	private String webstate;
	private String rettot;
	private String paytot;
	private String recnum;
	private String pricecol;
	private String pricedesc;
	private String custpricemark;
	private String hbmark;
	private String billProc;
	private String procMan;
	private String info;
	private String hostcustpsql;
	private String webno;
	private String webbno;
	private String groupmark;
	private String picnum;
	private String tmpc1;
	private String tmpc2;
	private String tmpc3;
	private String tmpc4;
	private String tmpc5;
	private String itemmark;
	private String billMonth;
	private String time1;
	private String time2;
	private String clerkno1;
	private String clerkno2;
	private String hostEno;
	private String date1;
	private String date2;
	private String topNum;
	private String nostockmark;

	
	public String getBillTypeName(){
		if(StringUtils.isEmpty(this.billType)) return "";
		return CodeUtil.getCodeName(this.billType);
	}
	
	public String getPayStateName() {
		if(StringUtils.isEmpty(this.payState)) return "";
		return CodeUtil.getCodeName(this.payState);
	}
	
	public BillSearchModel() 
	{
		
	}
}

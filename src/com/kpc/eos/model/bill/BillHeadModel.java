
package com.kpc.eos.model.bill;

import org.apache.commons.lang.StringUtils;

import lombok.Data;

import com.kpc.eos.core.Constants;
import com.kpc.eos.core.model.CommonModel;
import com.kpc.eos.core.util.CodeUtil;
import com.kpc.eos.model.bizSetting.DeliveryAddrModel;
import com.kpc.eos.model.bizSetting.PayTypeModel;
import com.kpc.eos.model.dataMng.DepartmentModel;
import com.kpc.eos.model.dataMng.UserModel;

@Data
public class BillHeadModel extends CommonModel 
{
	private static final long serialVersionUID = -4549949687759311641L;
	
	private String billId;
	private String billType;
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
	
	private String clerkName;
	private String clerkTelNo;
	private String hostFaxNo;
	private String rmbTotalAmt;
	
	/*private ArrayList<HashMap> items = new ArrayList<HashMap>();
	
	public void setItems(ArrayList<HashMap> items)
	{
		items.add(new HashMap());
		items.add(new HashMap());
		items.add(new HashMap());
		items.add(new HashMap());
		this.items = items;
	}
	
	public ArrayList<HashMap> getItems()
	{
		return this.items;
	}*/
	
	private String billTypeName;
	public String getBillTypeName(){
		if(StringUtils.isNotEmpty(billType)) {
			return CodeUtil.getCodeName(billType);
		}else {
			return "";
		}
		
	}
	private String rbillTypeName;
	public String getRbillTypeName(){
		return CodeUtil.getCodeName(this.rbillType);
	}
	
	private String payStateName;
	public String getPayStateName(){
		if (StringUtils.isEmpty(this.payState)) return "";
		return CodeUtil.getCodeName(this.payState);
	}
	
	private String processDetail;
	public String getProcessDetail(){
		if (Constants.BILL_STATE_COMPLETED.equals(getState())) {
			return "";
		}
		StringBuilder st = new StringBuilder();
		if (StringUtils.isNotEmpty(billProc)) {
			st.append(billProc);
		}
		if (StringUtils.isNotEmpty(procMan)) {
			st.append("<br />");
			st.append(procMan);
		}
		return st.toString();
	}
	
	public BillHeadModel() 
	{
		
	}
	
	boolean editableByState;
	public boolean getEditableByState()
	{
		return isEditableByState();
	}
	
	boolean deletableByState;
	public boolean getDeletableByState()
	{
		return isDeletable();
	}
	
	public boolean isViewable(UserModel loginUser)
	{
		if (loginUser == null) return false;
		
		if (Constants.CONST_BILL_TYPE_DING.equals(billType))
		{
			if ( ! (loginUser.isHostYn() && loginUser.getUserId().equals(hostUserId))  && ! loginUser.getEmpId().equals(inputorId) ) 
			{
				return false;
			}
		} 
		else if (Constants.CONST_BILL_TYPE_SALE.equals(billType)
				//|| Constants.CONST_BILL_TYPE_PAYMENT.equals(billType)
		)
		{
			if ( ! (loginUser.isHostYn() && loginUser.getUserId().equals(hostUserId))  && ! loginUser.getEmpId().equals(inputorId) ) 
			{
				return false;
			}
		}
		
		return true;
	}
	
	public boolean isEditableByState()
	{
		String state = this.getState();
		if (state == null) return false;
		
		if ( Constants.CONST_BILL_TYPE_PAYMENT.equals(billType) )
		{
			if ( Constants.BILL_STATE_DRAFT.equals(state)  
					|| Constants.BILL_STATE_RETURNED.equals(state)
			)
			{
				return true;
			}
			else
			{
				return false;
			}
		} 
		else
		{
			if ( Constants.BILL_STATE_COMPLETED.equals(state)  
				|| Constants.BILL_STATE_IN_PROCESS.equals(state)
				|| Constants.BILL_STATE_IN_PROCESS_BY_SELLER.equals(state) )
			{
				return false;
			}
			else
			{
				return true;
			}
		}
	}
	
	public boolean isDeletable()
	{
		String state = this.getState();
		if (state == null) return false;
		
		if ( Constants.BILL_STATE_COMPLETED.equals(state)  
				|| Constants.BILL_STATE_IN_PROCESS.equals(state)
				|| Constants.BILL_STATE_IN_PROCESS_BY_SELLER.equals(state) )
		{
			return false;
		}
		else
		{
			return true;
		}
	}
	
	public void setDeliveryAddress(DeliveryAddrModel dAddr)
	{
		if (dAddr == null) return;
		
		addrAddress = dAddr.getAddress();
		addrContactName = dAddr.getContactName();
		addrLocationId = dAddr.getLocationId();
		addrLocationName = dAddr.getLocationName();
		addrMobile = dAddr.getMobileNo();
		addrTelNo = dAddr.getTelNo();
		addrQq = dAddr.getQqNo();
	}
	
	public void setPaytype(PayTypeModel p)
	{
		if (p == null) return;
		paytypeName = p.getPayTypeName();
		weixinYn = p.getWeixinYn();
		hbmark = p.getPrePayYn();
	}
	
	public void setDept(DepartmentModel d)
	{
		if (d == null) return;
		deptId = d.getDeptId();
		deptName = d.getDeptName();
		deptNo = d.getDeptNo();
	}
	
	public String getRmbTotalAmt()
	{
		String numList = Constants.CONST_NUM_LIST;
		String rmbList = Constants.CONST_RMB_LIST;
		String result = "";
		if ( StringUtils.isNotEmpty(total2) && Double.parseDouble(total2) != 0){
			if (Double.parseDouble(total2) > 9999999999999.99){
				return Constants.CONST_RMB_EXCEED;
			}
			String numStr = Long.toString(Math.round(Double.parseDouble(total2) * 100));
			int numLen = numStr.length();
			int numChar;
			for (int i=0; i<numLen; i++){
				numChar = Integer.parseInt(numStr.substring(i, i+1));
				String n1 = numList.substring(numChar, numChar + 1);
				String n2 = rmbList.substring(numLen-i - 1, numLen - i);
				int rmbIndex = numLen - i - 1;
				if (numChar != 0){
					result = result + n1 + n2;
				}else{
					if (rmbIndex == 10 || rmbIndex == 6 || rmbIndex == 2){
						while (result.substring(result.length() - 1, result.length()).equals("零")){
							result = result.substring(0, result.length() - 1);
						}
					}
					String lastStr = result.substring(result.length() - 1, result.length());
					if (rmbIndex == 10 || (rmbIndex == 6 && lastStr.equals("亿")) || n2.equals("元")){
						result = result + n2;
					}else{
						if (result.substring(result.length() - 2, result.length() - 1).equals("零") || result.substring(result.length() - 1, result.length()).equals("亿")){
							result = result + n1;
						}
					}
				}
			}
			
			while (result.substring(result.length() - 1, result.length()).equals("零")){
				result = result.substring(0, result.length() - 1);
			}
			if (result.substring(result.length() - 1, result.length()).equals("元")){
				result = result + "整";
			}
		}
		
		return result;
	}
}

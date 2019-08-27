
package com.kpc.eos.model.billProcMng;

import java.util.List;

import lombok.Data;
import org.json.JSONArray;

import org.apache.axis.utils.StringUtils;

import com.kpc.eos.core.model.CommonModel;
import com.kpc.eos.core.util.CodeUtil;
import com.kpc.eos.model.bill.BillHeadModel;
import com.kpc.eos.model.bill.BillLineModel;
import com.kpc.eos.model.bill.PriceDetailModel;
import com.kpc.eos.model.bizSetting.EmployeeModel;

@Data
public class BillProcModel extends CommonModel 
{
	private static final long serialVersionUID = -7814456124397067987L;
	
	private String billProcId;
	private String billId;
	private String procDatId;
	private String empId;
	private String procTypeCd;
	private String procSeqNo;
	private String billProcName;
	private String qtyYn;
	private String priceYn;
	private String shipPriceYn;
	private String distributeYn;
	private String itemYn;
	private String minCost;
	private String maxCost;
	private String empName;
	private String empNameok;
	private String readmark;
	private String userId;
	private String userNo;
	private String userName;
	
	private String procNote;
	
	private String lineTotal1;
	private String lineTotal2;
	private String procUserId;
	private String workFlowId;
	
	private BillHeadModel billHead;
		
	private List<EmployeeModel> empList;
	private List<BillLineModel> billLineList;
	private List<PriceDetailModel> priceDetailList;
	
	private String saveFlag;
	private String priceMark;
	private String priceCols;
	
	private String billProcFullName;
	public String getBillProcFullName(){
		String rtn = CodeUtil.getCodeName(this.billProcName);
		if (rtn.equals("NA")) rtn = this.billProcName;
		return rtn;
	}
	
	private String processor;
	public String getProcessor(){
		String rtn = getBillProcFullName();
		if (StringUtils.isEmpty(rtn)) rtn = "";
		
		if (this.billHead == null) return rtn;
		
		rtn += "<br/>" + this.billHead.getProcMan();
		
		return rtn;
	}
	
	public BillProcModel() {
		
	}
	
}
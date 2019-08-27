
package com.kpc.eos.model.bizSetting;

import java.util.List;

import lombok.Data;

import org.apache.commons.lang.StringUtils;

import com.kpc.eos.core.Constants;
import com.kpc.eos.core.model.CommonModel;
import com.kpc.eos.core.util.CodeUtil;
import com.kpc.eos.core.util.MathUtil;
import com.kpc.eos.model.bill.BillHeadModel;
import com.kpc.eos.model.dataMng.UserModel;

@Data
public class WorkFlowModel extends CommonModel {
	private static final long serialVersionUID = 7766071269868339527L;
	
	private String workFlowId;
	private String workFlowType;
	private String workFlowTypeName;
	private String workFlowName;
	private String seqNo;
	private String groupYn;
	private String groupYnName;
	private String priceYn;
	private String priceYnName;
	private String qtyYn;
	private String qtyYnName;
	private String shipPriceYn;
	private String shipPriceYnName;
	private String itemYn;
	private String distributeYn;
	private String minCost;
	private String maxCost;
	private String note;
	private String seqData;
	
	
	private String userName;
	private String userNo;
	
	private List<String> empList;
	private List<String> empIdList;
	
	public String getWorkFlowTypeName(){
		return CodeUtil.getCodeName(this.workFlowType);
	}
	public String getGroupYnName(){
		return StringUtils.equals(this.groupYn, Constants.CONST_Y)?"是":"否";
	}
	public String getPriceYnName(){
		return StringUtils.equals(this.priceYn, Constants.CONST_Y)?"能":"不能";
	}
	public String getQtyYnName(){
		return StringUtils.equals(this.qtyYn, Constants.CONST_Y)?"能":"不能";
	}
	public String getShipPriceYnName(){
		return StringUtils.equals(this.shipPriceYn, Constants.CONST_Y)?"能":"不能";
	}
	
	public WorkFlowModel() {
		
	}
	
	public boolean isValidWorkflowForBill( BillHeadModel bill )
	{
		double total2 = MathUtil.getDouble(bill.getTotal2(), true);
		double min = MathUtil.getDouble(minCost, true);
		double max = MathUtil.getDouble(maxCost, true);
		
		return ( total2 >= min && total2 < max ); 
	}
	
	public boolean isGroupWorkflow()
	{
		return Constants.CONST_Y.equals( this.getGroupYn() );
	}
}
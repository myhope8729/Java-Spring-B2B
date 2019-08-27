
package com.kpc.eos.model.bill;

import lombok.Data;

import com.kpc.eos.core.model.CommonModel;
import com.kpc.eos.core.util.CodeUtil;
import com.kpc.eos.core.util.MathUtil;
import com.kpc.eos.model.bizSetting.PayTypeModel;
import com.sun.corba.se.spi.orbutil.fsm.State;

@Data
public class PayBillDetailModel extends CommonModel 
{
	// bill head table.
	private String billId;
	private String brand;
	private String billType;
	private String paymentType;
	private String total2;
	
	// paybilldetail table.
	private String id;
	private String userId;
	private String deptId;
	private String custUserId;
	private String pname;
	private String paytype1;
	private String paytype2;
	private String ration1;
	private String ration;
	private String tot1;
	private String tot;
	private String custtypeId;
	
	private String paytypeName;
	
	private String billTypeName;
	public String getBillTypeName(){
		return CodeUtil.getCodeName(this.billType);
	}
/*
/*
		SELECT  
				p.id				 AS id,
				p.bill_id			 AS billId,
				p.bill_type			 AS billType,
				p.user_id			 AS userId,
				p.dept_id			 AS deptId,
				p.cust_user_id		 AS custUserId,
				p.pname				 AS pname,
				p.paytype1			 AS paytype1,
				p.paytype2			 AS paytype2,
				p.ration1			 AS ration1,
				p.ration			 AS ration,
				p.tot1				 AS tot1,
				p.tot				 AS tot,
				p.custtype_id		 AS custtypeId,
				p.brand				 AS brand,
				p.state				 AS state,
				p.create_date		 AS createDate,
				p.create_by			 AS createBy,
				p.update_date		 AS updateDate,
				p.update_by			 AS updateBy,
		  FROM  eos_paybill_detail_new p
				p.id				 = #id#

*//*
		INSERT INTO eos_paybill_detail_new
		(
			id,
			bill_id,
			bill_type,
			user_id,
			dept_id,
			cust_user_id,
			pname,
			paytype1,
			paytype2,
			ration1,
			ration,
			tot1,
			tot,
			custtype_id,
			brand,
			state,
			create_date,
			create_by,
			update_date,
			update_by,
		)
		VALUES
		(
			#id#,
			#billId#,
			#billType#,
			#userId#,
			#deptId#,
			#custUserId#,
			#pname#,
			#paytype1#,
			#paytype2#,
			#ration1#,
			#ration#,
			#tot1#,
			#tot#,
			#custtypeId#,
			#brand#,
			#state#,
			#createDate#,
			#createBy#,
			#updateDate#,
			#updateBy#,
		)
*/

/*		UPDATE  eos_paybill_detail_new p
		   SET  
				p.id				 = #id#,
				p.bill_id			 = #billId#,
				p.bill_type			 = #billType#,
				p.user_id			 = #userId#,
				p.dept_id			 = #deptId#,
				p.cust_user_id		 = #custUserId#,
				p.pname				 = #pname#,
				p.paytype1			 = #paytype1#,
				p.paytype2			 = #paytype2#,
				p.ration1			 = #ration1#,
				p.ration			 = #ration#,
				p.tot1				 = #tot1#,
				p.tot				 = #tot#,
				p.custtype_id		 = #custtypeId#,
				p.brand				 = #brand#,
				p.state				 = #state#,
				p.create_date		 = #createDate#,
				p.create_by			 = #createBy#,
				p.update_date		 = #updateDate#,
				p.update_by			 = #updateBy#,
		 WHERE  
				p.id				 = #id#

*/

	public PayBillDetailModel() 
	{
		
	}
	
	public PayBillDetailModel(BillHeadModel bill, SubPayTypeModel pt, boolean isBonus) 
	{
		if (bill == null) return;
		
		billId = bill.getBillId();
		billType = bill.getBillType();
		userId = bill.getHostUserId();
		custUserId = bill.getCustUserId();
		custtypeId = bill.getCusttypeId();
		setState(bill.getState()); 
		
		String inputorId = bill.getInputorId();
		setCreateBy(inputorId);
		setUpdateBy(inputorId);
		
		paytype1 = pt.getPaytypeId();
		paytype2 = pt.getName();
		
		if (isBonus )
		{
			double baseAmt = MathUtil.getDouble(pt.getTopupAmt(), true);
			double dblRation1 = MathUtil.getDouble(pt.getBonusAmt(), true);
			
			if (baseAmt > 0)
			{
				ration1 = Double.toString( dblRation1 / baseAmt );
				tot1 = Double.toString(dblRation1 * MathUtil.getDouble(bill.getTotal2(), true) / baseAmt);
			}
		}
		else
		{
			ration1 = "100";
			tot1 = bill.getTotal2();
		}
		ration = ration1;
		tot = tot1;
	}
	
}

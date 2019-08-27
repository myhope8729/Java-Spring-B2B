
package com.kpc.eos.model.bill;

import org.apache.commons.lang.StringUtils;

import lombok.Data;

import com.kpc.eos.core.model.CommonModel;
import com.kpc.eos.core.util.FileUtil;
import com.kpc.eos.core.util.MathUtil;

@Data
public class BillLineModel extends CommonModel 
{
	private static final long serialVersionUID = -6984616053070602987L;
	
	private String billId;
	private String itemId;
	private String rbillId;
	private String storeId;
	private String cost;
	private String priceIn;
	private String price;
	private String price2;
	private String qty;
	private String qty2;
	private String tot;
	private String tot2;
	private String jsDisplay;
	private String jsQty;
	private String vendorId;
	private String note;
	private String weight;
	private String volumn;
	
	private String c1;
	private String c2;
	private String c3;
	private String c4;
	private String c5;
	private String c6;
	private String c7;
	private String c8;
	private String c9;
	private String c10;
	private String c11;
	private String c12;
	private String c13;
	private String c14;
	private String c15;
	private String c16;
	private String c17;
	private String c18;
	private String c19;
	private String c20;
	
	private String itemImgPath;
	private String itemSmallImg; // Small Image - get from /useritem/small
	private String itemMediumImg; // Medium Image - get from /useritem/medium
	
	private String stockItemId;
	
	private String itemsTotalQty;
	
	private String itemsTotalAmt;
	
	private String priceUnion;
	public String getPriceUnion(){
		if (StringUtils.isEmpty(price)) return "0";
		
		if (price.equals(price2))
		{
			return MathUtil.round(price2, 2);
		}
		else
		{
			return "<font color='#808080'>(" + MathUtil.round(price, 2) + ") </font>" + MathUtil.round(price2, 2);
		}
	}
	
	private String priceUnionForView;
	public String getPriceUnionForView(){
		if (StringUtils.isEmpty(price)) return "0";
		
		if (price.equals(price2))
		{
			return MathUtil.round(price2, 2);
		}
		else if (Double.valueOf(price) < Double.valueOf(price2))
		{
			
			return "<font color='#808080'>(" + MathUtil.round(price, 2) + ") </font>" + "<font color='#ff00000'>" + MathUtil.round(price2, 2) + "</font>";
		}else{
			return "<font color='#808080'>(" + MathUtil.round(price, 2) + ") </font>" + MathUtil.round(price2, 2);
		}
	}
	
	private String qtyUnion;
	public String getQtyUnion(){
		if (StringUtils.isEmpty(qty)) return "0";
		if (qty.equals(qty2))
		{
			return MathUtil.round(qty, 2);
		}
		else
		{
			return "<font color='#808080'>(" + MathUtil.round(qty, 2) + ") </font>" + MathUtil.round(qty2, 2); 
		}
	}
	
	public String getItemImgPath()
	{
		if (StringUtils.isNotEmpty(itemImgPath) && FileUtil.checkFileExist("useritem", itemImgPath)){
			return itemImgPath;
		}else{
			return "";
		}
	}
	
	public String getItemMediumImg()
	{
		return FileUtil.getItemImgUrl("medium", itemImgPath);
	}
	
	public String getItemSmallImg()
	{
		return FileUtil.getItemImgUrl("small", itemImgPath);
	}
	
/*
		INSERT INTO eos_bill_line_new
		(
			bill_id,
			item_id,
			rbill_id,
			store_id,
			cost,
			last_price_in,
			price,
			price2,
			qty,
			qty2,
			tot,
			tot2,
			js_display,
			js_qty,
			vendor_id,
			note,
			create_date,
			create_by,
			update_date,
			update_by,
			old_bill_line_id,
		)
		VALUES
		(
			#billId#,
			#itemId#,
			#rbillId#,
			#storeId#,
			#cost#,
			#lastPriceIn#,
			#price#,
			#price2#,
			#qty#,
			#qty2#,
			#tot#,
			#tot2#,
			#jsDisplay#,
			#jsQty#,
			#vendorId#,
			#note#,
			#createDate#,
			#createBy#,
			#updateDate#,
			#updateBy#,
			#oldBillLineId#,
		)
*/

/*		UPDATE  eos_bill_line_new
		   SET  
				bill_id				= #billId#,
				item_id				= #itemId#,
				rbill_id			= #rbillId#,
				store_id			= #storeId#,
				cost				= #cost#,
				last_price_in		= #lastPriceIn#,
				price				= #price#,
				price2				= #price2#,
				qty					= #qty#,
				qty2				= #qty2#,
				tot					= #tot#,
				tot2				= #tot2#,
				js_display			= #jsDisplay#,
				js_qty				= #jsQty#,
				vendor_id			= #vendorId#,
				create_date			= #createDate#,
				create_by			= #createBy#,
				update_date			= #updateDate#,
				update_by			= #updateBy#,
				old_bill_line_id	= #oldBillLineId#,
		 WHERE  
				bill_id				= #billId#

*/

	public BillLineModel() 
	{
		
	}
}

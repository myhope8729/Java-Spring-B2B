
package com.kpc.eos.model.bill;

import lombok.Data;

import com.kpc.eos.core.model.CommonModel;

@Data
public class BillLineNoStockModel extends CommonModel 
{
	private String billId;
	private String itemId;
	private String rbillId;
	private String storeId;
	private String cost;
	private String price;
	private String price2;
	private String qty;
	private String qty2;
	private String tot;
	private String tot2;
	private String jsYn;
	private String jsQty;
	private String note;
	
/*
		INSERT INTO eos_bill_line_nostock_new
		(
			bill_id,
			item_id,
			rbill_id,
			store_id,
			cost,
			price,
			price2,
			qty,
			qty2,
			tot,
			tot2,
			jsqty,
			note,
			create_date,
			create_by,
			update_date,
			update_by,
			old_line_nostock_id,
		)
		VALUES
		(
			#billId#,
			#itemId#,
			#rbillId#,
			#storeId#,
			#cost#,
			#price#,
			#price2#,
			#qty#,
			#qty2#,
			#tot#,
			#tot2#,
			#jsqty#,
			#note#,
			#createDate#,
			#createBy#,
			#updateDate#,
			#updateBy#,
			#oldLineNostockId#,
		)
*/

/*		UPDATE  eos_bill_line_nostock_new
		   SET  
				bill_id				= #billId#,
				item_id				= #itemId#,
				rbill_id			= #rbillId#,
				store_id			= #storeId#,
				cost				= #cost#,
				price				= #price#,
				price2				= #price2#,
				qty					= #qty#,
				qty2				= #qty2#,
				tot					= #tot#,
				tot2				= #tot2#,
				jsqty				= #jsqty#,
				note				= #note#,
				create_date			= #createDate#,
				create_by			= #createBy#,
				update_date			= #updateDate#,
				update_by			= #updateBy#,
				old_line_nostock_id	= #oldLineNostockId#,
		 WHERE  
				bill_id				= #billId#

*/

	public BillLineNoStockModel() 
	{
		
	}
}

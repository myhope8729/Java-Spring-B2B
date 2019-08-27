
package com.kpc.eos.model.bill;

import lombok.Data;

import com.kpc.eos.core.model.CommonModel;

@Data
public class ItemStockModel extends CommonModel 
{
	private static final long serialVersionUID = 5477635656270761965L;
	
	private String storeId;
	private String itemId;
	private String qty;
	private String cost;
	
	private String stockItemId;
	private String billQty;
	private String billPrice;
	
	private String totalQty;
	private String totalCost;
/*
		INSERT INTO eos_item_stock_new
		(
			user_id,
			store_id,
			item_id,
			qty,
			cost,
			create_date,
			create_by,
			update_date,
			update_by,
			old_item_stock_id,
		)
		VALUES
		(
			#userId#,
			#storeId#,
			#itemId#,
			#qty#,
			#cost#,
			#createDate#,
			#createBy#,
			#updateDate#,
			#updateBy#,
			#oldItemStockId#,
		)
*/

/*		UPDATE  eos_item_stock_new
		   SET  
				user_id				= #userId#,
				store_id			= #storeId#,
				item_id				= #itemId#,
				qty					= #qty#,
				cost				= #cost#,
				create_date			= #createDate#,
				create_by			= #createBy#,
				update_date			= #updateDate#,
				update_by			= #updateBy#,
				old_item_stock_id	= #oldItemStockId#,
		 WHERE  
				user_id				= #userId#

*/

	public ItemStockModel() 
	{
		
	}
}

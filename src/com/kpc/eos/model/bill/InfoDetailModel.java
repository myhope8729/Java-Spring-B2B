
package com.kpc.eos.model.bill;

import lombok.Data;

import com.kpc.eos.core.model.CommonModel;

@Data
public class InfoDetailModel extends CommonModel 
{
	private String id;
	private String userId;
	private String billId;
	private String lineId;
	private String dno;
	private String itemid;
	private String showYn;
	private String urlType;
	private String url;
/*
/*
		SELECT  
				d.id				 AS id,
				d.user_id			 AS userId,
				d.bill_id			 AS billId,
				d.line_id			 AS lineId,
				d.dno				 AS dno,
				d.itemid			 AS itemid,
				d.show_yn			 AS showYn,
				d.url_type			 AS urlType,
				d.url				 AS url,
		  FROM  eos_info_detail_new d
				d.id				 = #id#

*//*
		INSERT INTO eos_info_detail_new
		(
			id,
			user_id,
			bill_id,
			line_id,
			dno,
			itemid,
			show_yn,
			url_type,
			url,
		)
		VALUES
		(
			#id#,
			#userId#,
			#billId#,
			#lineId#,
			#dno#,
			#itemid#,
			#showYn#,
			#urlType#,
			#url#,
		)
*/

/*		UPDATE  eos_info_detail_new d
		   SET  
				d.id				 = #id#,
				d.user_id			 = #userId#,
				d.bill_id			 = #billId#,
				d.line_id			 = #lineId#,
				d.dno				 = #dno#,
				d.itemid			 = #itemid#,
				d.show_yn			 = #showYn#,
				d.url_type			 = #urlType#,
				d.url				 = #url#,
		 WHERE  
				d.id				 = #id#

*/

	public InfoDetailModel() 
	{
		
	}
}


package com.kpc.eos.model.bill;

import lombok.Data;

import com.kpc.eos.core.model.CommonModel;

@Data
public class InfoAttachmentModel extends CommonModel 
{
	private String id;
	private String billId;
	private String attachName;
	private String createDate;
/*
/*
		SELECT  
				info.id				 AS id,
				info.bill_id		 AS billId,
				info.attach_name	 AS attachName,
				info.create_date	 AS createDate,
		  FROM  eos_info_attachment_new info
				info.id				 = #id#

*//*
		INSERT INTO eos_info_attachment_new
		(
			id,
			bill_id,
			attach_name,
			create_date,
		)
		VALUES
		(
			#id#,
			#billId#,
			#attachName#,
			#createDate#,
		)
*/

/*		UPDATE  eos_info_attachment_new info
		   SET  
				info.id				 = #id#,
				info.bill_id		 = #billId#,
				info.attach_name	 = #attachName#,
				info.create_date	 = #createDate#,
		 WHERE  
				info.id				 = #id#

*/

	public InfoAttachmentModel() 
	{
		
	}
}

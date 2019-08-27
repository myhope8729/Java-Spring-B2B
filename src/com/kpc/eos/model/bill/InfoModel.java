
package com.kpc.eos.model.bill;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

import com.kpc.eos.core.model.CommonModel;

@Data
public class InfoModel extends CommonModel 
{
	private String id;
	private String lineId;
	private String userId;
	private String billId;
	private String dno;
	private String dtype;
	private String pnote;
	private String urltype;
	private String url;
	private String showmark;
	private String fontsize;
	private String fontcolor;
	private String bgcolor;
	
	private List<InfoDetailModel> detailList = new ArrayList<InfoDetailModel>();
	
	@Deprecated
	public void addDetail(InfoDetailModel obj)
	{
		detailList.add(obj);
	}
	
	public void setDetailList(List<InfoDetailModel> list)
	{
		detailList = list;
	}
	
	public List<InfoDetailModel> getDetailList()
	{
		return detailList;
	}
	
	
/*
/*
		SELECT  
				info.id				 AS id,
				info.line_id		 AS lineId,
				info.user_id		 AS userId,
				info.bill_id		 AS billId,
				info.dno			 AS dno,
				info.dtype			 AS dtype,
				info.pnote			 AS pnote,
				info.urltype		 AS urltype,
				info.url			 AS url,
				info.showmark		 AS showmark,
				info.fontsize		 AS fontsize,
				info.fontcolor		 AS fontcolor,
				info.bgcolor		 AS bgcolor,
				info.create_date	 AS createDate,
				info.update_date	 AS updateDate,
				info.created_by		 AS createdBy,
				info.updated_by		 AS updatedBy,
		  FROM  eos_info_new info
				info.id				 = #id#

*//*
		INSERT INTO eos_info_new
		(
			id,
			line_id,
			user_id,
			bill_id,
			dno,
			dtype,
			pnote,
			urltype,
			url,
			showmark,
			fontsize,
			fontcolor,
			bgcolor,
			create_date,
			update_date,
			created_by,
			updated_by,
		)
		VALUES
		(
			#id#,
			#lineId#,
			#userId#,
			#billId#,
			#dno#,
			#dtype#,
			#pnote#,
			#urltype#,
			#url#,
			#showmark#,
			#fontsize#,
			#fontcolor#,
			#bgcolor#,
			#createDate#,
			#updateDate#,
			#createdBy#,
			#updatedBy#,
		)
*/

/*		UPDATE  eos_info_new info
		   SET  
				info.id				 = #id#,
				info.line_id		 = #lineId#,
				info.user_id		 = #userId#,
				info.bill_id		 = #billId#,
				info.dno			 = #dno#,
				info.dtype			 = #dtype#,
				info.pnote			 = #pnote#,
				info.urltype		 = #urltype#,
				info.url			 = #url#,
				info.showmark		 = #showmark#,
				info.fontsize		 = #fontsize#,
				info.fontcolor		 = #fontcolor#,
				info.bgcolor		 = #bgcolor#,
				info.create_date	 = #createDate#,
				info.update_date	 = #updateDate#,
				info.created_by		 = #createdBy#,
				info.updated_by		 = #updatedBy#,
		 WHERE  
				info.id				 = #id#

*/

	public InfoModel() 
	{
		
	}
}

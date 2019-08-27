
package com.kpc.eos.model.bill;

import lombok.Data;

import com.kpc.eos.core.model.CommonModel;

@Data
public class ProcGroupModel extends CommonModel 
{
	private String procDatId;
	private String seqNo;
	private String groupName;
	private String condition;
	private String note;
	private String state;
	
/*
		INSERT INTO eos_proc_group_new
		(
			proc_dat_id,
			seq_no,
			group_name,
			condition,
			note,
			state,
			create_date,
			create_by,
			update_date,
			update_by,
			old_proc_group_id,
		)
		VALUES
		(
			#procDatId#,
			#seqNo#,
			#groupName#,
			#condition#,
			#note#,
			#state#,
			#createDate#,
			#createBy#,
			#updateDate#,
			#updateBy#,
			#oldProcGroupId#,
		)
*/

/*		UPDATE  eos_proc_group_new
		   SET  
				proc_dat_id			= #procDatId#,
				seq_no				= #seqNo#,
				group_name			= #groupName#,
				condition			= #condition#,
				note				= #note#,
				state				= #state#,
				create_date			= #createDate#,
				create_by			= #createBy#,
				update_date			= #updateDate#,
				update_by			= #updateBy#,
				old_proc_group_id	= #oldProcGroupId#,
		 WHERE  
				proc_dat_id			= #procDatId#

*/

	public ProcGroupModel() 
	{
		
	}
}

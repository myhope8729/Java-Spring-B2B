
package com.kpc.eos.model.bizSetting;

import java.util.List;

import lombok.Data;

import com.kpc.eos.core.model.CommonModel;
import com.kpc.eos.core.util.CodeUtil;

@Data
public class WorkFlowGroupModel extends CommonModel {
	private static final long serialVersionUID = -2423668031410677127L;

	private String workFlowId;
	private String seqNo;
	private String workFlowName;
	private String workFlowType;
	private String workFlowTypeName;
	private String workFlowGroupName;
	private List<String> custShortNameList;
	private List<String> empList;
	private String cond;
	private String note;
	
	public String getWorkFlowTypeName(){
		return CodeUtil.getCodeName(this.workFlowType);
	}
	
	public WorkFlowGroupModel() {
		
	}
}
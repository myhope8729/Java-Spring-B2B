
package com.kpc.eos.model.system;

import com.kpc.eos.core.model.CommonModel;

public class CodeGroupModel extends CommonModel {

	private static final long serialVersionUID = 4548435486974023359L;
	
	private String updateStatus;
	private String codeGroupId;
	private String codeGroupName;
	private String codeGroupDesc;
	private String lastUpdateTime;
	private String lastUpdateBy;	
	
	public CodeGroupModel() {}

	public String getUpdateStatus() {
		return updateStatus;
	}

	public void setUpdateStatus(String updateStatus) {
		this.updateStatus = updateStatus;
	}
	
	public String getCodeGroupId() {
		return codeGroupId;
	}

	public void setCodeGroupId(String codeGroupId) {
		this.codeGroupId = codeGroupId;
	}

	public String getCodeGroupName() {
		return codeGroupName;
	}

	public void setCodeGroupName(String codeGroupName) {
		this.codeGroupName = codeGroupName;
	}

	public String getCodeGroupDesc() {
		return codeGroupDesc;
	}

	public void setCodeGroupDesc(String codeGroupDesc) {
		this.codeGroupDesc = codeGroupDesc;
	}
	
	public String getLastUpdateTime() {
		return lastUpdateTime;
	}

	public void setLastUpdateTime(String lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}
	
	public String getLastUpdateBy() {
		return lastUpdateBy;
	}

	public void setLastUpdateBy(String lastUpdateBy) {
		this.lastUpdateBy = lastUpdateBy;
	}
	
}

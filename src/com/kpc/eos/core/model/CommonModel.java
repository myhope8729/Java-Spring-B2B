package com.kpc.eos.core.model;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import lombok.Data;

import com.kpc.eos.core.util.CodeUtil;

@Data
public class CommonModel extends BaseModel {

	private static final long serialVersionUID = -8776679654951373501L;
	
	private String updateStatus;
	private String codeId;	
	private String codeGroupId;
	private String codeGroupName;
	private String codeGroupDesc;
	private String createDate;
	private String createBy;
	private String createUserName;
	private String updateDate;
	private String updateBy;
	private String updateUserName;
	private Integer totalCount;
	private Integer rowNo;
	private String	clientIp;
	private String parentId;
	private String state;
	private String stateName;
	
	public String getStateName(){
		if(StringUtils.isEmpty(this.state)) {
			return this.stateName;
		}else {
			return CodeUtil.getCodeName(this.state);
		}
	}
	
	private List<String> childIdList;
		
	public CommonModel() {}

}

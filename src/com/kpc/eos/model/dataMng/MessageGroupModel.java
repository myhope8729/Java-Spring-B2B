package com.kpc.eos.model.dataMng;

import lombok.Data;

import com.kpc.eos.core.model.CommonModel;

public @Data class MessageGroupModel extends CommonModel {

	 /**
	 * 
	 */
	private static final long serialVersionUID = -1338799674613919257L;

	private String msgGroupId;
	private String msgGroupName;
	private String msgGroupDesc;
	
	public MessageGroupModel(){}
}

package com.kpc.eos.model.common;

import lombok.Data;

import com.kpc.eos.core.model.BaseModel;

/**
 * AuthorityGroupByURL
 * =================
 * Description : url authority model
 * Methods :
 */
@Data
public class AuthorityGroupByURL extends BaseModel {

	private static final long serialVersionUID = -6518754342403288170L;
	
	private String connUrl;
	private String menuName;
	private String privIds;

	public AuthorityGroupByURL() {}

}

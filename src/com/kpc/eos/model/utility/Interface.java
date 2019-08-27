package com.kpc.eos.model.utility;

import lombok.Data;

import com.kpc.eos.core.model.CommonModel;

@Data
public class Interface extends CommonModel{

	private static final long serialVersionUID = -5194883671637798115L;

	private String ifId;
	private String ifName;
	private String ifDesc;
	private String ifPath;
	private String reqType;
	
	public Interface(){
		
	}
}

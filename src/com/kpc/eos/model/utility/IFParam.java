package com.kpc.eos.model.utility;

import java.util.List;

import lombok.Data;

import com.kpc.eos.core.model.CommonModel;
import com.kpc.eos.model.system.CodeModel;

@Data
public class IFParam extends CommonModel{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2794013738129355959L;

	private String ifId;
	private String paramName;
	private String paramDesc;
	private String groupCode;
	private String commonYn;
	private String ordering;
	private String validationYn;
	private String defaultValue;
	private List<CodeModel> codeList;
	
	private String value;
	public IFParam(){
		
	}

}

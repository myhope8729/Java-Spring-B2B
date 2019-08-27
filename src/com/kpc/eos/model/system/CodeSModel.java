
package com.kpc.eos.model.system;

import lombok.Data;

import com.kpc.eos.model.common.DefaultSModel;

/**
 * CodeSModel
 * =================
 * Description :  
 * Methods :
 */
@Data
public class CodeSModel extends DefaultSModel {

	private static final long serialVersionUID = 4469552112575313120L;

	private String scUpperCode;
	private String scCodeName;
	private String scValidYn;
	
	public CodeSModel() {}
}

/**
 * Filename		: EmpSModel.java
 * Description	:
 * 2017
 * @author		: RKRK
 */
package com.kpc.eos.model.dataMng;

import lombok.Data;

import com.kpc.eos.model.common.DefaultSModel;

@Data
public class EmpSModel extends DefaultSModel
{
	private static final long serialVersionUID = -7775116169492841557L;
	
	private String userYn;
	
	private String codeId;
}

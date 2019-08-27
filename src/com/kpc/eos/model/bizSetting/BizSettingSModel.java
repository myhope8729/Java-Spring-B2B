
package com.kpc.eos.model.bizSetting;

import lombok.Data;

import com.kpc.eos.core.model.PagingModel;
import com.kpc.eos.model.common.DefaultSModel;

@Data
public class BizSettingSModel extends DefaultSModel {
	private static final long serialVersionUID = -6579804872592259581L;
	
	private String sysKindCd;
	private String sysCode;
	private String chelp;
	
	private PagingModel page;
	
	public BizSettingSModel() {
		this.page = new PagingModel();
	}
}
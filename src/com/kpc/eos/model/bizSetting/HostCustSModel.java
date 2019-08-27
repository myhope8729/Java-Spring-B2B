
package com.kpc.eos.model.bizSetting;

import lombok.Data;

import com.kpc.eos.core.model.BaseModel;
import com.kpc.eos.core.model.PagingModel;
import com.kpc.eos.model.common.DefaultSModel;

@Data
public class HostCustSModel extends DefaultSModel {
	private static final long serialVersionUID = 6916427904196766135L;
	
	private String hostId;
	private String custId;
	
	private String bizAreaTypeCd;
	private String custTypeId;
	private String empId;
	private String chelp;
	
}
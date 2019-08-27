
package com.kpc.eos.model.bizSetting;

import lombok.Data;

import com.kpc.eos.core.model.PagingModel;
import com.kpc.eos.model.common.DefaultSModel;

@Data
public class WorkFlowSModel extends DefaultSModel {
	
	private static final long serialVersionUID = -2348173386832746554L;
	
	private String workFlowType;
	private String workFlowId;
	private String pagingYn;
	private String state;
	
}
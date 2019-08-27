
package com.kpc.eos.model.system;

import lombok.Data;

import com.kpc.eos.core.model.BaseModel;
import com.kpc.eos.model.common.DefaultSModel;

@Data
public class MenuSModel extends DefaultSModel {

	private static final long serialVersionUID = 2573337875239423263L;
	
	private String menuName;
	private String menuTypeCd;
	private String menuId;
	
	private String validYn;
	
	public MenuSModel() { }
}

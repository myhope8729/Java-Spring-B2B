
package com.kpc.eos.model.dataMng;

import lombok.Data;
import com.kpc.eos.core.model.CommonModel;

public @Data class UserMenuRightModel extends CommonModel{

	private static final long serialVersionUID = -238240335038594350L;
	
	private String empId;
	
	public String menuId;
	public String menuUserName;
	public String state;
	private String codeId;
	
	public UserMenuRightModel() {
		
	}
}
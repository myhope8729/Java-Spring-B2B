/**
 * Filename		: EmpMenuRightModel.java
 * Description	:
 * 
 * 2017
 */
package com.kpc.eos.model.dataMng;

import lombok.Data;

import com.kpc.eos.core.model.CommonModel;

/**
 * Filename		: EmpMenuRightModel.java
 * Description	:
 * 2017
 * @author		: RKRK
 */
@Data
public class EmpMenuRightModel extends CommonModel
{
	private static final long serialVersionUID = -7150830299996150316L;
	
	private String menuId;
	private Integer level;
	private String menuName;
	private Integer sortNo;
	private String upperMenuId;
	private String menuUserName;
	private String assigned;
}

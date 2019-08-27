
package com.kpc.eos.model.dataMng;

import lombok.Data;

import com.kpc.eos.core.model.CommonModel;

/**
 * Priv
 * =================
 * Description : 권한 model
 * Methods :
 */
@Data
public class PrivModel extends CommonModel {

	private static final long serialVersionUID = 3303525368030988256L;
	
	/**
	 * 권한ID
	 */
	private String privId;
	/**
	 * 권한명
	 */
	private String privName;
	/**
	 * 설명
	 */
	private String privDesc;
	/**
	 * 운영자 수
	 */
	private Integer adminCount;
	/**
	 * 메뉴 수
	 */
	private Integer menuCount;

	private String refMenuIds;

	private String refAdminIds;
	
	public PrivModel() {}

}

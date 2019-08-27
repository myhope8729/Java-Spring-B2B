
package com.kpc.eos.model.dataMng;

import lombok.Data;

import org.apache.commons.lang.StringUtils;

import com.kpc.eos.core.model.BaseModel;
import com.kpc.eos.core.model.PagingModel;

/**
 * PrivSModel
 * =================
 * Description : 권한그룹 검색 model
 * Methods :
 */
@Data
public class PrivSModel extends BaseModel {

	private static final long serialVersionUID = 4711265016833431725L;
	/**
	 * 권한그룹명
	 */
	private String privName;

	/**
	 * 사용자NO
	 */
	private String adminId;
	/**
	 * 사용자명
	 */
	private String adminName;
	/**
	 * 메뉴ID
	 */
	private String smenuId;
	/**
	 * 메뉴명
	 */
	private String smenuName;
	/**
	 * 사용여부
	 */
	private String validYn;
	/**
	 * 페이징 객체
	 */
	private PagingModel page;
	
	private String[] adminIds;
	
	private String[] menuIds;
	
	public PrivSModel() {
		this.page = new PagingModel();
	}

	public void setAdminId(String adminId) {
		this.adminId = adminId;
		if (StringUtils.isNotBlank(this.adminId)) {
			this.adminIds = this.adminId.split(";");
		}
	}
	public void setSmenuId(String smenuId) {
		this.smenuId = smenuId;
		if (StringUtils.isNotBlank(this.smenuId)) {
			this.menuIds = this.smenuId.split(";");
		}
	}
}

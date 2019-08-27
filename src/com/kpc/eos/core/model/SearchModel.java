
package com.kpc.eos.core.model;

/**
 * SearchModel
 * =================
 * Description : 
 * Methods :
 */
public class SearchModel extends BaseModel {

	private static final long serialVersionUID = 1600835099496861678L;

	private String systemName;
	
	private String loginUserNo;

	private String clientIp;

	public SearchModel() {}

	public String getSystemName() {
		return systemName;
	}

	public void setSystemName(String systemName) {
		this.systemName = systemName;
	}

	public String getLoginUserNo() {
		return loginUserNo;
	}

	public void setLoginUserNo(String loginUserNo) {
		this.loginUserNo = loginUserNo;
	}

	public String getClientIp() {
		return clientIp;
	}

	public void setClientIp(String clientIp) {
		this.clientIp = clientIp;
	}

}

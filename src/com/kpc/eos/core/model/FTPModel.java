
package com.kpc.eos.core.model;


/**
 * FTPModel
 * =================
 * Description : 
 * Methods :
 */
public class FTPModel extends BaseModel {

	private static final long serialVersionUID = -103351071887261326L;
	
	private String host;
	private String username;
	private String password;
	private String encoding;
	
	public FTPModel() { }
	
	public FTPModel(String host, String username, String password, String encoding) {
		this.host 	  = host;
		this.username = username;
		this.password = password;
		this.encoding = encoding;
	}
	
	public String getHost() {
		return host;
	}
	
	public void setHost(String host) {
		this.host = host;
	}
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getEncoding() {
		return encoding;
	}
	
	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}
	

}

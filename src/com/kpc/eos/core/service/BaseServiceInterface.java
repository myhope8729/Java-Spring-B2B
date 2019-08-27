/**
 * Filename		: BaseServiceInterface.java
 * Description	:
 * 
 * 2017
 */
package com.kpc.eos.core.service;

import com.ibatis.sqlmap.client.SqlMapClient;

/**
 * Filename		: BaseServiceInterface.java
 * Description	:
 * 2017
 * @author		: RKRK
 */
public interface BaseServiceInterface
{
	public void startTrans() throws Exception;
	
	public void rollbackTrans() throws Exception;
	
	public void commitTran() throws Exception;
	
	public void endTran() throws Exception;
	
	public SqlMapClient getSQLMap() throws Exception;
	
	
}


package com.kpc.eos.core.service;

import java.util.UUID;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.kpc.eos.core.dao.BaseDAO;

/**
 * 
 * BaseService
 * =================
 * Description : 
 * Methods :
 */
public abstract class BaseService {

    protected BaseDAO baseDAO;

	public void setBaseDAO(BaseDAO baseDAO) {
		this.baseDAO = baseDAO;
	}
	
	public String getUUID(){
		String s = UUID.randomUUID().toString();
		return s.substring(0,8)+s.substring(9,13)+s.substring(14,18)+s.substring(19,23)+s.substring(24); 
	}
	
	public void startTrans() throws Exception
	{
		baseDAO.getSqlMapClient().startTransaction();
	}

	public void rollbackTrans() throws Exception
	{
		baseDAO.getSqlMapClient().endTransaction();
	}

	public void commitTran() throws Exception
	{
		baseDAO.getSqlMapClient().commitTransaction();
		baseDAO.getSqlMapClient().endTransaction();
	}
	public void endTran() throws Exception
	{
		baseDAO.getSqlMapClient().endTransaction();
	}
	
	public SqlMapClient getSQLMap()
	{
		return baseDAO.getSqlMapClient();
	}
}

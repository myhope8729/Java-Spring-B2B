package com.kpc.eos.core.dao;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.ibatis.sqlmap.client.event.RowHandler;


public abstract class AbstractDAO extends SqlMapClientDaoSupport {

    protected Log log = LogFactory.getLog(this.getClass());

    /**
     * Return as list type a query result
     * 
     * @param queryId   statementName
     * @param param     parameterObject
     * @return          List 
     * @throws SQLException
     */
    @SuppressWarnings("unchecked")
    public List queryForList(String queryId, Object param) throws SQLException  {
        
        List rv = getSqlMapClientTemplate().queryForList(queryId, param);

        if (this.log.isDebugEnabled()) {
            int size = rv.size();
            this.log.debug((size == 0)? "selected list empty." : "selected list size " + size);
        }
        return rv;
    }

    /**
     *  Return as a Object a query result
     * 
     * @param queryId   statementName
     * @param param     parameterObject
     * @return
     * @throws SQLException
     */
    public Object queryForObject(String queryId, Object param) throws SQLException {
        
        Object rv = getSqlMapClientTemplate().queryForObject(queryId, param);
        
        if (this.log.isDebugEnabled()) {
            this.log.debug("selected result is " + rv);
        }
        return rv;
    }

    /**
     * return query result as resultObject
     * 
     * @param queryId   statementName
     * @param param     parameterObject
     * @param result    resultObject
     * @return
     * @throws SQLException
     */
    public Object queryForObject(String queryId, Object param, Object result) throws SQLException {
        
        Object rv = getSqlMapClientTemplate().queryForObject(queryId, param, result);
        
        if (this.log.isDebugEnabled()) {
            this.log.debug("selected result is " + rv);
        }
        return rv;
    }

    /**
     * delegate to rowhandler with the search result of fetch size
     * when Large Capacity  SQL.
     * 
     * @param queryId       statementName
     * @param param         parameterObject
     * @param resultMaker   rowHandler
     * @throws SQLException
     */
    public void queryWithRowHandler(String queryId, Object param, Object resultMaker) throws SQLException {
        getSqlMapClientTemplate().queryWithRowHandler(queryId, param, (RowHandler) resultMaker);
    }

    /**
     * Execute insert SQL
     * 
     * @param  queryId   statementName
     * @param  param     parameterObject
     * @return Row PK to insert
     * @throws SQLException
     */
    public Object insert(String queryId, Object param) throws SQLException {
        
        Object rv = getSqlMapClientTemplate().insert(queryId, param);
        
        if (this.log.isDebugEnabled()) {
            this.log.debug("inserted key is " + rv);
        }
        return rv;
    }

    /**
     * Execute update SQL
     * 
     * @param  queryId   statementName
     * @param  param     parameterObject
     * @return row number to update
     * @throws SQLException
     */
    public int update(String queryId, Object param) throws SQLException {
    	
        int rv = getSqlMapClientTemplate().update(queryId, param);
        
        if (this.log.isDebugEnabled()) {
            this.log.debug("updated count is " + rv);
        }
        return rv;
    }

    /**
     * Execute delete SQL
     * 
     * @param queryId   statementName
     * @param param     parameterObject
     * @return row number to delete
     * @throws SQLException
     */
    public int delete(String queryId, Object param) throws SQLException {
        
        int rv = getSqlMapClientTemplate().delete(queryId, param);
        
        if (this.log.isDebugEnabled()) {
            this.log.debug("delete count is " + rv);
        }
        return rv;
    }

}

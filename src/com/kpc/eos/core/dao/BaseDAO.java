package com.kpc.eos.core.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.orm.ibatis.SqlMapClientCallback;

import com.ibatis.sqlmap.client.SqlMapExecutor;

public class BaseDAO extends AbstractDAO {

	/**
	 * Batch insert.
	 *
	 * @param sqlId the sql id
	 * @param paramList the param list
	 * @return the list
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List batchInsert(final String sqlId, final List<?> paramList) {

		List resultList = (List) getSqlMapClientTemplate().execute(new SqlMapClientCallback() {

			@Override
			public Object doInSqlMapClient(SqlMapExecutor executor) throws SQLException {

				List resultList = new ArrayList();
				int processedCount = 0;

				executor.startBatch();

				for (Object param : paramList) {
					resultList.add(executor.insert(sqlId, param));
					processedCount++;
				}

				executor.executeBatch();

				if (log.isDebugEnabled()) {
					log.debug("Inserted count = " + processedCount);
				}

				return resultList;
			}
		});

		return resultList;
	}

	/**
	 * Batch update.
	 *
	 * @param sqlId the sql id
	 * @param paramList the param list
	 * @return the int
	 */
	public int batchUpdate(final String sqlId, final List<?> paramList) {

		Integer batchCount = (Integer) getSqlMapClientTemplate().execute(new SqlMapClientCallback() {

			@Override
			public Object doInSqlMapClient(SqlMapExecutor executor) throws SQLException {

				int processedCount = 0;

				executor.startBatch();

				for (Object param : paramList) {
					executor.update(sqlId, param);
					processedCount++;
				}

				executor.executeBatch();

				if (log.isDebugEnabled()) {
					log.debug("Updated count = " + processedCount);
				}

				return processedCount;
			}
		});

		return batchCount.intValue();
	}

	/**
	 * Batch delete.
	 *
	 * @param sqlId the sql id
	 * @param paramList the param list
	 * @return the int
	 */
	public int batchDelete(final String sqlId, final List<?> paramList) {

		Integer batchCount = (Integer) getSqlMapClientTemplate().execute(new SqlMapClientCallback() {

			@Override
			public Object doInSqlMapClient(SqlMapExecutor executor) throws SQLException {

				int processedCount = 0;

				executor.startBatch();

				for (Object param : paramList) {
					executor.delete(sqlId, param);
					processedCount++;
				}

				executor.executeBatch();

				if (log.isDebugEnabled()) {
					log.debug("Deleted count = " + processedCount);
				}

				return processedCount;
			}
		});

		return batchCount.intValue();
	}
}

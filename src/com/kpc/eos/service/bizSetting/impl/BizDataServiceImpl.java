
package com.kpc.eos.service.bizSetting.impl;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.axis.utils.StringUtils;
import org.springframework.transaction.annotation.Transactional;

import com.kpc.eos.core.Constants;
import com.kpc.eos.core.service.BaseService;
import com.kpc.eos.model.bizSetting.BizDataModel;
import com.kpc.eos.service.bizSetting.BizDataService;

@Transactional
public class BizDataServiceImpl extends BaseService implements BizDataService {
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<BizDataModel> getBizDataByBizCode(String userId, String codeId, String seqNo) throws Exception{
		
		Map map = new HashMap();
		map.put("userId", userId);
		map.put("codeId", codeId);
		map.put("seqNo", seqNo);
		
		List<BizDataModel> bizDataList = baseDAO.queryForList("bizData.getBizDataByCode", map);
		
		return bizDataList;
	}
	
	@SuppressWarnings("unchecked")
	public List<BizDataModel> getBizDataMainList() throws Exception
	{
		List<BizDataModel> list = baseDAO.queryForList("bizData.getBizDataMainList", null);
		return list;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<BizDataModel> getBizDataList(String userId, String codeId) throws Exception
	{
		Map map = new HashMap();
		map.put("userId", userId);
		map.put("codeId", codeId);
		
		List<BizDataModel> list = baseDAO.queryForList("bizData.getBizDataList", map);
		return list;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void saveBizData(String userId, String empId, String codeId, String[] c1, String[] c2, String[] c3, String[] c4, String[] c5) throws SQLException
	{
		Map map = new HashMap();
		map.put("userId", userId);
		map.put("codeId", codeId);
		
		baseDAO.getSqlMapClient().startBatch();
		baseDAO.delete("bizData.delBizDataByCodeId", map);
		
		for (int i = 0; i < c1.length; i++){
			if (StringUtils.isEmpty(c1[i]) && StringUtils.isEmpty(c1[i]) && StringUtils.isEmpty(c3[i]) && StringUtils.isEmpty(c4[i]) && StringUtils.isEmpty(c5[i])){
				continue;
			}
			
			// validation.
			if ( Constants.BIZDATA_PREPAY.equals(codeId) )
			{
				if (StringUtils.isEmpty(c2[i]) || StringUtils.isEmpty(c3[i])) {
					continue;
				}
			}
			map.clear();
			map.put("userId", userId);
			map.put("codeId", codeId);
			map.put("empId", empId);
			map.put("seqNo", i + 1);
			map.put("c1", c1[i]);
			map.put("c2", c2[i]);
			map.put("c3", c3[i]);
			map.put("c4", c4[i]);
			map.put("c5", c5[i]);
			
			// if prepay setting?
			if ( Constants.BIZDATA_PREPAY.equals(codeId) )
			{
				if (StringUtils.isEmpty(c1[i])) 
				{
					map.put("c1", c4[i] + " 送 " + c5[i]);
				}
			}
			
			
			baseDAO.insert("bizData.insertBizDataByCodeId", map);
		}
		baseDAO.getSqlMapClient().executeBatch();
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void deleteBizData(String userId, String codeId, String[] seqNo) throws Exception
	{
		Map map = new HashMap();
		map.put("userId", userId);
		map.put("codeId", codeId);
		map.put("seqNo", seqNo);
		
		baseDAO.delete("bizData.delBizData", map);
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public BizDataModel getBizDataForHostCust(Map map) throws Exception
	{
		return (BizDataModel) baseDAO.queryForObject("bizData.getBizDataForHostCust", map);
	}
}

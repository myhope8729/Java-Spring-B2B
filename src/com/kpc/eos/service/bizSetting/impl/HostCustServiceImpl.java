
package com.kpc.eos.service.bizSetting.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.transaction.annotation.Transactional;

import com.kpc.eos.core.service.BaseService;
import com.kpc.eos.model.bizSetting.HostCustModel;
import com.kpc.eos.model.bizSetting.HostCustSModel;
import com.kpc.eos.model.bizSetting.PayTypeModel;
import com.kpc.eos.service.bizSetting.HostCustService;

@Transactional
public class HostCustServiceImpl extends BaseService implements HostCustService {
	
	@SuppressWarnings("unchecked")
	@Override
	public List<HostCustModel> getCustSettingList(HostCustSModel sc) throws Exception {
		List<HostCustModel> custSettingList = baseDAO.queryForList("hostcust.getCustSettingList", sc);
		return custSettingList;
	}
	
	@Override
	public Integer getTotalCountCustSettingList(HostCustSModel sc) throws Exception
	{
		return (Integer)baseDAO.queryForObject("hostcust.getTotalCountCustSettingList", sc);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List<PayTypeModel> getPayTypeListByUser(String hostId, String custId) throws Exception {
		Map map = new HashMap();
		map.put("hostId", hostId);
		map.put("custId", custId);
		List<PayTypeModel> payTypeList = baseDAO.queryForList("hostcust.getPayTypeList", map);
		return payTypeList;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public HostCustModel getCustSetting(String hostId, String custId) throws Exception {
		Map map = new HashMap();
		map.put("hostId", hostId);
		map.put("custId", custId);
		
		HostCustModel custSetting =  (HostCustModel) baseDAO.queryForObject("hostcust.getCustSetting", map);
		return custSetting;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public HostCustModel getHostCustSetting(String hostId, String custId) throws Exception {
		Map map = new HashMap();
		map.put("hostId", hostId);
		map.put("custId", custId);
		
		HostCustModel custSetting =  (HostCustModel) baseDAO.queryForObject("hostcust.getHostCustSetting", map);
		return custSetting;
	}
	
	@SuppressWarnings("unchecked")
	public List<HostCustModel> getCustListByHostIdForCopy(String hostId) throws Exception {
		List<HostCustModel> custList = baseDAO.queryForList("hostcust.getCustListForCopymark", hostId);
		return custList;
	}
	
	@Override
	public void saveCustSetting(HostCustModel custSetting) throws Exception {
		baseDAO.delete("hostcust.deleteCustPayType", custSetting);
		if (custSetting.getPayTypeId() != null && custSetting.getPayTypeId().size() > 0)
		{
			baseDAO.insert("hostcust.insertCustPayType", custSetting);
		}
		baseDAO.update("hostcust.updateCustSetting", custSetting);
		
		if (StringUtils.isNotEmpty(custSetting.getCopyCustId()))
		{
			baseDAO.delete("workflow.deleteProcGroupCust", custSetting.getCustUserId());
			baseDAO.insert("workflow.insertProcGroupCust", custSetting);
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<HostCustModel> gethostSettingList(HostCustSModel sc) throws Exception {
		List<HostCustModel> hostSettingList = baseDAO.queryForList("hostcust.getHostSettingList", sc);
		return hostSettingList;
	}
	
	public Integer getTotalCountHostSettingList(HostCustSModel sc) throws Exception
	{
		return (Integer) baseDAO.queryForObject("hostcust.getTotalCountHostSettingList", sc);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<HostCustModel> getSupplyList(HostCustSModel sc) throws Exception{
		List<HostCustModel> supplyList = baseDAO.queryForList("hostcust.getHostListForSet", sc);
		return supplyList;
	}
	
	@Override
	public Integer getTotalSupplyList(HostCustSModel sc) throws Exception
	{
		return (Integer) baseDAO.queryForObject("hostcust.getTotalHostList", sc);
	}
	@Override
	public void insertHostList(HostCustModel sc) throws Exception{
		JSONArray jsonArr = new JSONArray(sc.getSelectedRows());
		
		baseDAO.getSqlMapClient().startBatch();
		for (int i=0; i< jsonArr.length(); i++){
			JSONObject obj =  (JSONObject)jsonArr.get(i);
			sc.setHostUserId(obj.getString("hostUserId"));
			baseDAO.insert("hostcust.insertHostList", sc);
		}
		baseDAO.getSqlMapClient().executeBatch();
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void deleteHost(String hostId, String custId) throws Exception{
		Map map = new HashMap();
		map.put("hostId", hostId);
		map.put("custId", custId);
		baseDAO.delete("hostcust.deleteHost", map);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<HostCustModel> getHostList(String custId) throws Exception {
		List<HostCustModel> hostList = baseDAO.queryForList("hostcust.getHostList", custId);
		return hostList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<HostCustModel> getHostListForOrder(HostCustSModel sc)
			throws Exception
	{
		List<HostCustModel> hostList = baseDAO.queryForList("hostcust.getHostListForOrder", sc);
		return hostList;
	}

	@Override
	public Integer countHostListForOrder(HostCustSModel sc) throws Exception
	{
		return (Integer)baseDAO.queryForObject("hostcust.countHostListForOrder", sc);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<HostCustModel> getHostCustList(HostCustSModel sc)
	throws Exception
	{
		List<HostCustModel> hostList = baseDAO.queryForList("hostcust.getHostCustList", sc);
		return hostList;
	}
	
	@Override
	public Integer countHostCustList(HostCustSModel sc) throws Exception
	{
		return (Integer)baseDAO.queryForObject("hostcust.countHostCustList", sc);
	}
}

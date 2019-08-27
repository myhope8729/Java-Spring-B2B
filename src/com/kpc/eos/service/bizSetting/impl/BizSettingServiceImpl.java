
package com.kpc.eos.service.bizSetting.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.transaction.annotation.Transactional;

import com.kpc.eos.core.service.BaseService;
import com.kpc.eos.model.bizSetting.BizSettingModel;
import com.kpc.eos.model.bizSetting.BizSettingSModel;
import com.kpc.eos.service.bizSetting.BizSettingService;

@Transactional
public class BizSettingServiceImpl extends BaseService implements BizSettingService {
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public BizSettingModel getBizSettingBySysType(String userId, String sysCode) throws Exception{
		
		Map map = new HashMap();
		map.put("userId", userId);
		map.put("sysCode", sysCode);
		
		BizSettingModel bisSettingData = (BizSettingModel) baseDAO.queryForObject("bizSetting.getBizSettingList", map);
		
		return bisSettingData;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<BizSettingModel> getBizSettingList(BizSettingSModel sc) throws Exception{
		List<BizSettingModel> bizSettingList =  baseDAO.queryForList("bizSetting.getBizSettingList", sc);
		return bizSettingList;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<BizSettingModel> getBizSettingDetailInfo() throws Exception{
		List<BizSettingModel> detailList =  baseDAO.queryForList("bizSetting.getBizSettingDetailList", null);
		return detailList;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void saveBizSetting(String bizSettingStr, String userId) throws Exception{
		JSONArray jsonArr = new JSONArray(bizSettingStr);
		
		baseDAO.getSqlMapClient().startBatch();
		baseDAO.delete("bizSetting.deleteBizSettingByUser", userId);
		for (int i = 0; i < jsonArr.length(); i++){
			JSONObject obj =  (JSONObject)jsonArr.get(i);
			Map map = new HashMap();
			map.put("userId", userId);
			map.put("sysCode", obj.getString("sysCode"));
			map.put("sysValue", obj.getString("sysValue"));
			baseDAO.insert("bizSetting.insertBizSetting", map);
		}
		baseDAO.getSqlMapClient().executeBatch();
	}

	@SuppressWarnings("unchecked")
	@Override
	public HashMap<String, String> getUserBizSettingMap(String userId,  String sysKindId) throws Exception
	{
		HashMap<String, String> ret = new HashMap<String, String>();
		BizSettingSModel sc = new BizSettingSModel();
		sc.setUserId(userId);
		sc.setSysKindCd(sysKindId);
		List<BizSettingModel> bizSettingList =  baseDAO.queryForList("bizSetting.getUserBizSettingList", sc);
		if (bizSettingList != null)
		{
			for (BizSettingModel item : bizSettingList)
			{
				ret.put(item.getSysCode(), item.getSysValueName());	
			}
		}
		return ret;
	}
}

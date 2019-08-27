
package com.kpc.eos.service.bizSetting.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.transaction.annotation.Transactional;

import com.kpc.eos.core.service.BaseService;
import com.kpc.eos.model.bizSetting.StoreModel;
import com.kpc.eos.model.common.DefaultSModel;
import com.kpc.eos.service.bizSetting.StoreService;

@Transactional
public class StoreServiceImpl extends BaseService implements StoreService {
	
	@SuppressWarnings("unchecked")
	@Override
	public List<StoreModel> getStoreList(DefaultSModel sc) throws Exception {
		List<StoreModel> storeList = baseDAO.queryForList("store.getStoreList", sc);
		return storeList;
	}
	
	@Override
	public Integer getTotalCountStoreList(DefaultSModel sc) throws Exception{
		return (Integer) baseDAO.queryForObject("store.getTotalCountStoreList", sc);
	}
	
	@Override
	public StoreModel getStore(String storeId) throws Exception {
		StoreModel store =  (StoreModel) baseDAO.queryForObject("store.getStore", storeId);
		return store;
	}
	
	@Override
	public void saveStore(StoreModel store) throws Exception {
		if (StringUtils.isNotEmpty(store.getStoreId()))
		{
			baseDAO.update("store.updateStore", store);
		}
		else
		{
			baseDAO.insert("store.insertStore", store);
		}
	}
	
	@Override
	public Integer existStoreNo(StoreModel store) throws Exception{
		return (Integer)baseDAO.queryForObject("store.checkExistStore", store);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<StoreModel> getAllStoreList(String userId) throws Exception {
		List<StoreModel> storeList = baseDAO.queryForList("store.getAllStoreList", userId);
		return storeList;
	}
}


package com.kpc.eos.service.bizSetting;

import java.util.List;

import com.kpc.eos.model.bizSetting.StoreModel;
import com.kpc.eos.model.common.DefaultSModel;

public interface StoreService {
	
	public List<StoreModel> getStoreList(DefaultSModel sc) throws Exception;
	
	public Integer getTotalCountStoreList(DefaultSModel sc) throws Exception;
	
	public StoreModel getStore(String storeId) throws Exception;
	
	public void saveStore(StoreModel store) throws Exception;
	
	public Integer existStoreNo(StoreModel store) throws Exception;
	
	public List<StoreModel> getAllStoreList(String userId) throws Exception;
}

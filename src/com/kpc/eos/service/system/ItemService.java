
package com.kpc.eos.service.system;

import java.util.List;

import com.kpc.eos.model.common.DefaultSModel;
import com.kpc.eos.model.system.ItemModel;

public interface ItemService {
	
	public Integer countItemList(DefaultSModel sc) throws Exception;

	public List<ItemModel> getItemList(DefaultSModel sc) throws Exception;
	
	public ItemModel getItem(String itemId) throws Exception;
	
	public void saveItem(ItemModel item) throws Exception;
	
	public List<String> getCategoryList(String userId) throws Exception;
	
	public Integer checkItemNoExists(ItemModel item) throws Exception;
	
}

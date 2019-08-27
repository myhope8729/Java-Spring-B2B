
package com.kpc.eos.service.system.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.axis.utils.StringUtils;
import org.springframework.transaction.annotation.Transactional;

import com.kpc.eos.core.service.BaseService;
import com.kpc.eos.model.common.DefaultSModel;
import com.kpc.eos.model.system.ItemModel;
import com.kpc.eos.service.system.ItemService;

@Transactional
public class ItemServiceImpl extends BaseService implements ItemService {

	@Override
	public Integer countItemList(DefaultSModel sc) throws Exception {
		return  (Integer)baseDAO.queryForObject("item.countItemList", sc);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ItemModel> getItemList(DefaultSModel sc) throws Exception {
		return baseDAO.queryForList("item.getItemList", sc);
	}
	
	@Override
	public ItemModel getItem(String itemId) throws Exception {
		return (ItemModel)baseDAO.queryForObject("item.getItem", itemId);
	}
	
	@Override
	public void saveItem(ItemModel item) throws Exception {
		item.setChelp();
		if (StringUtils.isEmpty(item.getItemId()))
		{
			baseDAO.update("item.insertItem", item);
		}
		else
		{
			baseDAO.update("item.updateItem", item);
			baseDAO.update("item.updateItemProperty", item);
		}
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<String> getCategoryList(String userId) throws Exception{
		Map map = new HashMap();
		map.put("userId", userId);
		return baseDAO.queryForList("item.getCategoryList", map);
	}
	
	@Override
	public Integer checkItemNoExists(ItemModel item) throws Exception{
		return (Integer) baseDAO.queryForObject("item.checkExistsItemNo", item);
	}
}

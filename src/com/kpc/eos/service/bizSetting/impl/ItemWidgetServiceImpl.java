
package com.kpc.eos.service.bizSetting.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.util.StringUtil;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.transaction.annotation.Transactional;

import com.kpc.eos.core.service.BaseService;
import com.kpc.eos.model.bizSetting.ItemWidgetModel;
import com.kpc.eos.model.bizSetting.UserItemModel;
import com.kpc.eos.model.bizSetting.UserItemPropertyModel;
import com.kpc.eos.model.bizSetting.UserItemSModel;
import com.kpc.eos.model.common.DefaultSModel;
import com.kpc.eos.service.bizSetting.ItemWidgetService;
import com.kpc.eos.service.bizSetting.UserItemService;

@Transactional
public class ItemWidgetServiceImpl extends BaseService implements ItemWidgetService {
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ItemWidgetModel> getUserItemWidgetList(ItemWidgetModel sc) throws Exception {
		List<ItemWidgetModel> itemWidgetList = baseDAO.queryForList("itemWidget.getUserItemWidgetList", sc);
		return itemWidgetList;
	}

	/* (non-Javadoc)
	 * @see com.kpc.eos.service.bizSetting.ItemWidgetService#SaveItemWidget(java.util.List)
	 */
	@Override
	public void SaveItemWidget(List<ItemWidgetModel> itemModelList, String userId, String itemId) throws Exception {
		Map map = new HashMap();
		map.put("userId", userId);
		map.put("itemId", itemId);
		
		baseDAO.getSqlMapClient().startBatch();
		baseDAO.delete("itemWidget.deleteItemWidget", map);
		for (int i=0; i< itemModelList.size(); i++){
			baseDAO.insert("itemWidget.insertItemWidget", itemModelList.get(i));
		}
		baseDAO.getSqlMapClient().executeBatch();
	}

	/* (non-Javadoc)
	 * @see com.kpc.eos.service.bizSetting.ItemWidgetService#getDeleteImageList(java.lang.String, java.lang.String, java.util.List)
	 */
	@Override
	public List<String> getDeleteImageList(String userId, String itemId, List<String> saveImgList) throws Exception {

		Map map = new HashMap();
		map.put("userId", userId);
		map.put("itemId", itemId);
		map.put("imgNames", saveImgList);
		
		List<String> deleteImageList = baseDAO.queryForList("itemWidget.getDeleteImageList", map);
		return deleteImageList;
	}
	
}

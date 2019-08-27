
package com.kpc.eos.service.bizSetting;

import java.util.List;

import com.kpc.eos.model.bizSetting.ItemWidgetModel;
import com.kpc.eos.model.bizSetting.UserItemPropertyModel;
import com.kpc.eos.model.bizSetting.UserItemModel;
import com.kpc.eos.model.bizSetting.UserItemSModel;
import com.kpc.eos.model.common.DefaultSModel;

public interface ItemWidgetService {
	
	public List<ItemWidgetModel> getUserItemWidgetList(ItemWidgetModel sc) throws Exception;
	
	public void SaveItemWidget(List<ItemWidgetModel> itemModelList, String userId, String itemId) throws Exception;
	
	public List<String> getDeleteImageList(String userId, String itemId, List<String> saveImgList) throws Exception ;
	
}

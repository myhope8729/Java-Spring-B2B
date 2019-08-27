
package com.kpc.eos.service.bizSetting;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.kpc.eos.controller.bizSetting.handler.UserItemRowHandler;
import com.kpc.eos.core.service.BaseServiceInterface;
import com.kpc.eos.model.bill.BillHeadModel;
import com.kpc.eos.model.bizSetting.UserItemModel;
import com.kpc.eos.model.bizSetting.UserItemPropertyModel;
import com.kpc.eos.model.bizSetting.UserItemSModel;

public interface UserItemService extends BaseServiceInterface{
	
	public List<UserItemModel> getUserItemList(UserItemSModel sc) throws Exception;
	
	public Integer countUserItemList(UserItemSModel sc) throws Exception;
	
	public List<String> getItemCategoryList(String userId, String categoryFieldName) throws Exception;
	
	@SuppressWarnings("rawtypes")
	public List<HashMap> getUserItemCategoryWithCountList(String userId, String categoryFieldName) throws Exception;
	
	@SuppressWarnings("rawtypes")
	public List<HashMap> getUserItemCategoryWithCountList(String userId, String categoryFieldName, Map map) throws Exception;
	
	@SuppressWarnings("rawtypes")
	public List<HashMap> getUserItemCategoryWithCountList(String userId, String categoryFieldName, String parentCatFieldName, String parentCatFieldValue) throws Exception;
	
	@SuppressWarnings("rawtypes")
	public List<HashMap> getUserItemCategoryWithCountList(String userId, String categoryFieldName, String parentCatFieldName, String parentCatFieldValue, Map map) throws Exception;
	
	public List<UserItemPropertyModel> getItemFieldPropertyByUser(String userId, String propertyTypeCd) throws Exception;
	
	public List<UserItemPropertyModel> getItemFieldPropertyByUser(String userId, String propertyTypeCd, boolean checkState) throws Exception;
	
	public UserItemModel getUserItem(String userId, String itemId) throws Exception;
	
	public void saveUserItem(UserItemModel userItem) throws Exception;

	public List<UserItemPropertyModel> getFieldList(String userId, String state) throws Exception;
	
	public void saveUserItemProperty(UserItemPropertyModel sc) throws Exception;
	
	public void saveItems(Map map) throws Exception;
	
	public Integer countUserItemByGroupList(UserItemSModel sc) throws Exception;
	
	public List<UserItemModel> getUserItemByGroupList(UserItemSModel sc) throws Exception;
	
	public Integer countUserItemExceptGroupList(UserItemSModel sc) throws Exception;
	
	public List<UserItemModel> getUserItemExceptGroupList(UserItemSModel sc) throws Exception;
	
	public void saveGroupItems(String userId, String[] itemIdArray, String groupId) throws Exception;
	
	public void deleteGroupItem(String userId, String itemId, String groupId) throws Exception;
	
	// order form.
	public List<UserItemPropertyModel> getUserNNItemPropertyList(String userId) throws Exception;
	
	//Bill proc form
	public List<UserItemPropertyModel> getUserItemPropertyNoPriceList(String userId) throws Exception;
	public List<UserItemPropertyModel> getUserItemPropertyNameList(String userId) throws Exception;
	
	public List<UserItemModel> getUserItemListInOrder(UserItemSModel sc) throws Exception;
	
	public List<UserItemModel> getBilledUserItemList(BillHeadModel bill) throws Exception;
	
	public Integer countUserItemListInOrder(UserItemSModel sc) throws Exception;
	
	@SuppressWarnings("rawtypes")
	public List<UserItemModel> getUserItemListByIds(String userId, List<String> ids, Map params) throws Exception;
	
	public List<UserItemPropertyModel> getAllUserItemPropertyList(String userId) throws Exception;
	
	public List<UserItemPropertyModel> getUserItemPricePropertyList(String userId) throws Exception;
	
	public void downloadUserItemList(UserItemSModel sc,	UserItemRowHandler handler) throws Exception;

    // product type
	public List<String> getCategory2List(UserItemSModel sc) throws Exception;

	
	
	// --------------------------------------------------------------------------------//
	// ---------------------------- User Page -----------------------------------------//
	// --------------------------------------------------------------------------------//
	@SuppressWarnings("rawtypes")
	public List getItemClassesWithCountList(String userId, String categoryFieldName, String parentCategoryFieldName, Map map) throws Exception;
	
	@SuppressWarnings("rawtypes")
	public List getItemClassWithCountList(String userId, String categoryFieldName, Map map) throws Exception;
	
	@SuppressWarnings("rawtypes")
	public List getItemClassWithCountList(String userId, String categoryFieldName, String parentCatFieldName, String parentCatFieldValue, Map map) throws Exception;
	
	
	public List<UserItemModel> getUserItemListInFront(UserItemSModel sc) throws Exception;
	
	public List<UserItemModel> getBilledUserItemListInFront(BillHeadModel bill) throws Exception;
	
	public Integer countUserItemListInFront(UserItemSModel sc) throws Exception;
	
	// ---------------------------- End of User Page -----------------------------------//
	

	
	public List<UserItemPropertyModel> getUserItemPrintPropertyList(String userId) throws Exception;
	
	public void saveUserItemPropertyModel(UserItemPropertyModel model) throws Exception;
	
	public List<UserItemPropertyModel> getDefaultPriceField(UserItemPropertyModel model) throws Exception;
	
	public Integer existsUserItemNo(UserItemModel userItem, String itemNoField) throws Exception;
	
	public List<UserItemModel> getUserItemListOnConfigPage(UserItemSModel sc) throws Exception;
	
	public Integer countUserItemListOnConfigPage(UserItemSModel sc) throws Exception;

}

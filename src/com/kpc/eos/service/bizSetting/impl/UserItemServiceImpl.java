
package com.kpc.eos.service.bizSetting.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import com.kpc.eos.controller.bizSetting.handler.UserItemRowHandler;
import com.kpc.eos.core.service.BaseService;
import com.kpc.eos.model.bill.BillHeadModel;
import com.kpc.eos.model.bizSetting.UserItemModel;
import com.kpc.eos.model.bizSetting.UserItemPropertyModel;
import com.kpc.eos.model.bizSetting.UserItemSModel;
import com.kpc.eos.service.bizSetting.UserItemService;

public class UserItemServiceImpl extends BaseService implements UserItemService 
{
	
	@SuppressWarnings("unchecked")
	@Override
	public List<UserItemModel> getUserItemList(UserItemSModel sc) throws Exception {
		List<UserItemModel> userItemList = baseDAO.queryForList("userItem.getUserItemList", sc);
		return userItemList;
	}
	
	@Override
	public Integer countUserItemList(UserItemSModel sc) throws Exception
	{
		return  (Integer)baseDAO.queryForObject("userItem.countUserItemList", sc);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<UserItemPropertyModel> getItemFieldPropertyByUser(String userId, String propertyTypeCd) throws Exception{
		Map map = new HashMap();
		map.put("userId", userId);
		map.put("propertyTypeCd", propertyTypeCd);
		map.put("state", "ST0001");
		List<UserItemPropertyModel> priceList = baseDAO.queryForList("userItem.getFieldList", map);
		return priceList;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<UserItemPropertyModel> getItemFieldPropertyByUser(String userId, String propertyTypeCd, boolean checkState) throws Exception{
		Map map = new HashMap();
		map.put("userId", userId);
		map.put("propertyTypeCd", propertyTypeCd);
		if (checkState){
			map.put("state", "ST0001");
		}
		List<UserItemPropertyModel> priceList = baseDAO.queryForList("userItem.getFieldList", map);
		return priceList;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public UserItemModel getUserItem(String userId, String itemId) throws Exception {
		Map map = new HashMap();
		map.put("userId", userId);
		map.put("itemId", itemId);
		UserItemModel userItem =  (UserItemModel) baseDAO.queryForObject("userItem.getUserItem", map);
		return userItem;
	}
	
	@Override
	public void saveUserItem(UserItemModel userItem) throws Exception {
		userItem.setChelp();
		
		if (StringUtils.isNotEmpty(userItem.getItemId()))
		{
			baseDAO.update("userItem.updateUserItem", userItem);
		}
		else
		{
			//baseDAO.insert("userItem.insertUserItem", userItem);
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<UserItemPropertyModel> getFieldList(String userId, String state) throws Exception {
		Map map = new HashMap();
		map.put("userId", userId);
		map.put("state", state);
		List<UserItemPropertyModel> itemPropertyList = baseDAO.queryForList("userItem.getFieldList", map);
		return itemPropertyList;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List<String> getItemCategoryList(String userId, String categoryFieldName) throws Exception {
		Map map = new HashMap();
		map.put("userId", userId);
		map.put("catFieldName", categoryFieldName);
		List<String> itemCategoryList = baseDAO.queryForList("userItem.getCategoryList", map);
		return itemCategoryList;
	}

	@Override
	public void saveUserItemProperty(UserItemPropertyModel sc) throws Exception {
		String userDataStr = sc.getUserData();
		JSONArray jsonArr = new JSONArray(userDataStr);
		
		baseDAO.getSqlMapClient().startBatch();
		baseDAO.delete("userItem.deleteUserItemProperty", sc.getUserId());
		for (int i=0; i< jsonArr.length(); i++){
			JSONObject obj =  (JSONObject)jsonArr.get(i);
			
			sc.setSeqNo(String.valueOf(i+1));
			sc.setPropertyName(obj.getString("propertyName"));
			sc.setPropertyDesc(obj.getString("propertyDesc"));
			sc.setPropertyTypeCd(obj.getString("propertyTypeCd"));
			sc.setPropertyValue(obj.getString("propertyValue"));
			sc.setDisplayYn(obj.getString("displayYn"));
			sc.setPrintYn(obj.getString("printYn"));
			sc.setState(obj.getString("state"));
			
			baseDAO.insert("userItem.insertUserItemProperty", sc);
		}
		baseDAO.getSqlMapClient().executeBatch();
		
	}

	/* (non-Javadoc)
	 * @see com.kpc.eos.service.bizSetting.UserItemService#saveItems(java.lang.String, java.lang.String[])
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void saveItems(Map map) throws Exception {
		baseDAO.insert("userItem.insertItems", map);
	}

	/* (non-Javadoc)
	 * @see com.kpc.eos.service.bizSetting.UserItemService#getUserItemByGroupList(com.kpc.eos.model.bizSetting.UserItemSModel)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<UserItemModel> getUserItemByGroupList(UserItemSModel sc) throws Exception {
		List<UserItemModel> itemList = baseDAO.queryForList("userItem.getUserItemByGroupList", sc);
		return itemList;
	}

	/* (non-Javadoc)
	 * @see com.kpc.eos.service.bizSetting.UserItemService#getUserItemExceptGroupList(com.kpc.eos.model.bizSetting.UserItemSModel)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<UserItemModel> getUserItemExceptGroupList(UserItemSModel sc) throws Exception {
		List<UserItemModel> itemList = baseDAO.queryForList("userItem.getUserItemExceptGroupList", sc);
		return itemList;
	}

	/* (non-Javadoc)
	 * @see com.kpc.eos.service.bizSetting.UserItemService#saveGroupItems(java.lang.String, java.lang.String[], java.lang.String)
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void saveGroupItems(String userId, String[] itemIdArray, String groupId) throws Exception {
		Map map = new HashMap();
		map.put("userId", userId);
		map.put("itemIds", itemIdArray);
		map.put("groupId", groupId);
		baseDAO.insert("userItem.insertGroupItems", map);		
	}

	/* (non-Javadoc)
	 * @see com.kpc.eos.service.bizSetting.UserItemService#deleteGroupItem(java.lang.String, java.lang.String)
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void deleteGroupItem(String userId, String itemId, String groupId) throws Exception {
		Map map = new HashMap();
		map.put("userId", userId);
		map.put("itemId", itemId);
		map.put("groupId", groupId);
		baseDAO.delete("userItem.deleteGroupItem", map);		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<UserItemPropertyModel> getUserNNItemPropertyList(String userId) throws Exception
	{
		List<UserItemPropertyModel> itemPropertyList = baseDAO.queryForList("userItem.getUserNNItemPropertyList", userId);
		return itemPropertyList;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<UserItemPropertyModel> getUserItemPropertyNoPriceList(String userId) throws Exception
	{
		return baseDAO.queryForList("userItem.getUserItemPropertyList", userId); 
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<UserItemPropertyModel> getUserItemPropertyNameList(String userId) throws Exception
	{
		return baseDAO.queryForList("userItem.getUserItemNamePropertyList", userId); 
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<UserItemModel> getUserItemListInOrder(UserItemSModel sc) throws Exception {
		List<UserItemModel> userItemList = baseDAO.queryForList("userItem.getUserItemListInOrder", sc);
		return userItemList;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<UserItemModel> getBilledUserItemList(BillHeadModel bill) throws Exception {
		List<UserItemModel> userItemList = baseDAO.queryForList("userItem.getBilledUserItemList", bill);
		return userItemList;
	}
	
	@Override
	public Integer countUserItemListInOrder(UserItemSModel sc) throws Exception
	{
		return  (Integer)baseDAO.queryForObject("userItem.countUserItemListInOrder", sc);
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public List<HashMap> getUserItemCategoryWithCountList(String userId, String categoryFieldName) throws Exception
	{
		return getUserItemCategoryWithCountList(userId, categoryFieldName, null, null);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<HashMap> getUserItemCategoryWithCountList(String userId, String categoryFieldName, Map map) throws Exception
	{
		if (map == null)
		{
			map = new HashMap();
		}
		map.put("userId", userId);
		map.put("catFieldName", categoryFieldName);
		
		List<HashMap> ret = (List<HashMap>) baseDAO.queryForList("userItem.getUserItemCategoryWithCountList", map);  
		return ret;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List<HashMap> getUserItemCategoryWithCountList(String userId, String categoryFieldName, String parentCatFieldName, String parentCatFieldValue) throws Exception
	{
		Map map = new HashMap();
		map.put("userId", userId);
		map.put("catFieldName", categoryFieldName);
		map.put("parentCatFieldName", parentCatFieldName);
		map.put("parentCatFieldValue", parentCatFieldValue);
		
		List<HashMap> ret = (List<HashMap>) baseDAO.queryForList("userItem.getUserItemCategoryWithCountList", map);  
		return ret;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List<HashMap> getUserItemCategoryWithCountList(String userId, String categoryFieldName, String parentCatFieldName, String parentCatFieldValue, Map map) throws Exception
	{
		if (map == null)
		{
			map = new HashMap();
		}
		map.put("userId", userId);
		map.put("catFieldName", categoryFieldName);
		map.put("parentCatFieldName", parentCatFieldName);
		map.put("parentCatFieldValue", parentCatFieldValue);
		
		List<HashMap> ret = (List<HashMap>) baseDAO.queryForList("userItem.getUserItemCategoryWithCountList", map);  
		return ret;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List<UserItemModel> getUserItemListByIds(String userId, List<String> ids, Map params)
			throws Exception
	{
		if (params == null) 
		{
			params = new HashMap();
		}
		params.put("userId", userId);
		params.put("ids", ids);
		List<UserItemModel> userItemList = baseDAO.queryForList("userItem.getUserItemListByIds", params);
		return userItemList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<UserItemPropertyModel> getAllUserItemPropertyList(String userId) throws Exception
	{
		List<UserItemPropertyModel> itemPropertyList = baseDAO.queryForList("userItem.getAllUserItemPropertyList", userId);
		return itemPropertyList;
	}

	/* (non-Javadoc)
	 * @see com.kpc.eos.service.bizSetting.UserItemService#countUserItemByGroupList(com.kpc.eos.model.bizSetting.UserItemSModel)
	 */
	@Override
	public Integer countUserItemByGroupList(UserItemSModel sc) throws Exception {
		return (Integer)baseDAO.queryForObject("userItem.countUserItemByGroupList", sc);
	}

	/* (non-Javadoc)
	 * @see com.kpc.eos.service.bizSetting.UserItemService#countUserItemExceptGroupList(com.kpc.eos.model.bizSetting.UserItemSModel)
	 */
	@Override
	public Integer countUserItemExceptGroupList(UserItemSModel sc) throws Exception {
		return (Integer)baseDAO.queryForObject("userItem.countUserItemExceptGroupList", sc);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<UserItemPropertyModel> getUserItemPricePropertyList(String userId) throws Exception
	{
		return baseDAO.queryForList("userItem.getUserItemPricePropertyList", userId);
	}

	/* (non-Javadoc)
	 * @see com.kpc.eos.service.bizSetting.UserItemService#downloadUserItemList(com.kpc.eos.model.common.DefaultSModel, com.kpc.eos.controller.bizSetting.handler.UserItemRowHandler)
	 */
	@Override
	public void downloadUserItemList(UserItemSModel sc,	UserItemRowHandler handler) throws Exception {
		baseDAO.queryWithRowHandler("userItem.downLoadUserItemList", sc, handler);
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> getCategory2List(UserItemSModel sc) throws Exception {
		return (List<String>)baseDAO.queryForList("userItem.getCategory2List", sc);
	}

	
	// --------------------------------------------------------------------------------//
	// ---------------------------- User Page -----------------------------------------//
	// --------------------------------------------------------------------------------//
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List getItemClassesWithCountList(String userId, String parentCategoryFieldName, String categoryFieldName,  Map map) throws Exception
	{
		if (map == null)
		{
			map = new HashMap();
		}
		map.put("userId", userId);
		map.put("parentCatFieldName", parentCategoryFieldName);
		map.put("catFieldName", categoryFieldName);
		
		return baseDAO.queryForList("userItem.getItemClassesWithCountList", map);  
	}
	
	@Override
	public List getItemClassWithCountList(String userId, String categoryFieldName, Map map) throws Exception
	{
		if (map == null)
		{
			map = new HashMap();
		}
		map.put("userId", userId);
		map.put("catFieldName", categoryFieldName);
		
		List<HashMap> ret = (List<HashMap>) baseDAO.queryForList("userItem.getItemClassWithCountList", map);  
		return ret;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List getItemClassWithCountList(String userId, String categoryFieldName, String parentCatFieldName, String parentCatFieldValue, Map map) throws Exception
	{
		if (map == null)
		{
			map = new HashMap();
		}
		map.put("userId", userId);
		map.put("catFieldName", categoryFieldName);
		map.put("parentCatFieldName", parentCatFieldName);
		map.put("parentCatFieldValue", parentCatFieldValue);
		
		List<HashMap> ret = (List<HashMap>) baseDAO.queryForList("userItem.getItemClassWithCountList", map);  
		return ret;
	}
	
	
	@Override
	public Integer countUserItemListInFront(UserItemSModel sc) throws Exception
	{
		return  (Integer)baseDAO.queryForObject("userItem.countUserItemListInFront", sc);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<UserItemModel> getUserItemListInFront(UserItemSModel sc) throws Exception {
		List<UserItemModel> userItemList = baseDAO.queryForList("userItem.getUserItemListInFront", sc);
		return userItemList;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<UserItemModel> getBilledUserItemListInFront(BillHeadModel bill) throws Exception {
		List<UserItemModel> userItemList = baseDAO.queryForList("userItem.getBilledUserItemListInFront", bill);
		return userItemList;
	}
	
	// ---------------------------- End of User Page -----------------------------------//

	
	@SuppressWarnings("unchecked")
	@Override
	public List<UserItemPropertyModel> getUserItemPrintPropertyList(String userId) throws Exception
	{
		List<UserItemPropertyModel> itemPropertyList = baseDAO.queryForList("userItem.getUserItemPrintPropertyList", userId);
		return itemPropertyList;
	}

	/* (non-Javadoc)
	 * @see com.kpc.eos.service.bizSetting.UserItemService#saveUserItemPropertyModel()
	 */
	@Override
	public void saveUserItemPropertyModel(UserItemPropertyModel model) throws Exception {
		baseDAO.update("userItem.updateUserItemProperty", model);
	}

	/* (non-Javadoc)
	 * @see com.kpc.eos.service.bizSetting.UserItemService#getDefaultPriceField(com.kpc.eos.model.bizSetting.UserItemPropertyModel)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<UserItemPropertyModel> getDefaultPriceField(UserItemPropertyModel model) throws Exception {
		List<UserItemPropertyModel> itemPropertyList = baseDAO.queryForList("userItem.getUserItemDefaultPriceList", model);
		return itemPropertyList;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Integer existsUserItemNo(UserItemModel userItem, String itemNoField) throws Exception{
		Map map = new HashMap();
		map.put("itemNoCol", itemNoField);
		map.put("itemNo", userItem.get(itemNoField));
		map.put("itemId", userItem.getItemId());
		map.put("userId", userItem.getUserId());
		
		return (Integer) baseDAO.queryForObject("userItem.checkExistsUserItemNo", map);
	}

	/* (non-Javadoc)
	 * @see com.kpc.eos.service.bizSetting.UserItemService#getUserItemListOnConfigPage(com.kpc.eos.model.bizSetting.UserItemSModel)
	 */
	@Override
	public List<UserItemModel> getUserItemListOnConfigPage(UserItemSModel sc) throws Exception {
		List<UserItemModel> userItemList = baseDAO.queryForList("userItem.getUserItemListOnConfigPage", sc);
		return userItemList;
	}

	/* (non-Javadoc)
	 * @see com.kpc.eos.service.bizSetting.UserItemService#countUserItemListOnConfigPage(com.kpc.eos.model.bizSetting.UserItemSModel)
	 */
	@Override
	public Integer countUserItemListOnConfigPage(UserItemSModel sc) throws Exception {
		return  (Integer)baseDAO.queryForObject("userItem.countUserItemListOnConfigPage", sc);
	}
}

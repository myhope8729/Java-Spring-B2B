
package com.kpc.eos.service.statistic.impl;

import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.transaction.annotation.Transactional;

import com.kpc.eos.core.service.BaseService;
import com.kpc.eos.model.billProcMng.BillProcSModel;
import com.kpc.eos.model.bizSetting.HostCustSModel;
import com.kpc.eos.model.statistic.BillSearchModel;
import com.kpc.eos.model.statistic.CommonStatisticSModel;
import com.kpc.eos.model.statistic.CustSearchModel;
import com.kpc.eos.model.statistic.StatisticTotalModel;
import com.kpc.eos.model.statistic.StockItemDetailModel;
import com.kpc.eos.model.statistic.StockSearchSModel;
import com.kpc.eos.model.statistic.UserStockItemModel;
import com.kpc.eos.service.statistic.StatisticService;

@Transactional
public class StatisticServiceImpl extends BaseService implements StatisticService {
	
	/*-- -----------------------------------------BillSearch Start-----------------------------------------------*/
	@Override
	public Integer countBillSearchList(BillProcSModel params) throws Exception {
		return (Integer)baseDAO.queryForObject("statistic.countBillSearchList", params);
	}
    
    @SuppressWarnings("unchecked")
    @Override
    public List<BillSearchModel> getBillSearchList(BillProcSModel params) throws Exception {
    	List<BillSearchModel> billSearchList = baseDAO.queryForList("statistic.getBillSearchList", params);
    	return billSearchList;
    }

	@Override
	public String getPrintTypeByUser(String userId) throws Exception {
		return (String)baseDAO.queryForObject("statistic.getPrintTypeByUser", userId);
	}
	/*-- -----------------------------------------BillSearch End-----------------------------------------------*/
	/*-- -----------------------------------------Stock Search Start-----------------------------------------------*/
	@SuppressWarnings("unchecked")
	@Override
	public List<HashMap<String,String>> getStoreComboListByUser(String userId) throws Exception {
		return (List<HashMap<String, String>>)baseDAO.queryForList("statistic.getStoreComboListByUser", userId);
	}
    
	@Override
	public String getItemCat1FieldByUser(String userId, String PropType) throws Exception { 
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("propType", PropType);
		params.put("userId", userId);
		return (String)baseDAO.queryForObject("statistic.getItemCat1FieldByUser", params);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<HashMap<String,String>> getItemCategory1ListByUser(String userId, String PropType, String catField) throws Exception {
		if(StringUtils.isEmpty(catField)) {
			catField = getItemCat1FieldByUser(userId, PropType);
		}
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("propertyName", catField);
		params.put("userId", userId);
		return (List<HashMap<String, String>>)baseDAO.queryForList("statistic.getItemCategory1ListByUser", params);
	}

	@Override
	public StatisticTotalModel countStockItemListByUser(StockSearchSModel params) throws Exception {
		return (StatisticTotalModel)baseDAO.queryForObject("statistic.countStockItemListByUser", params);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<UserStockItemModel> getStockItemListByUser(StockSearchSModel params) throws Exception {
		List<UserStockItemModel> stockSearchList = baseDAO.queryForList("statistic.getStockItemListByUser", params);
    	return stockSearchList;
	}

	@Override
	public Integer countStockDetailSearchList(BillProcSModel params) throws Exception {
		return (Integer)baseDAO.queryForObject("statistic.countStockDetailSearchList", params);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<StockItemDetailModel> getStockDetailSearchList(BillProcSModel params) throws Exception {
		List<StockItemDetailModel> stockItemDetailModel = baseDAO.queryForList("statistic.getStockDetailSearchList", params);
    	return stockItemDetailModel;
	}

	/*-- -----------------------------------------Stock Search End-----------------------------------------------*/
	
	/*-- -----------------------------------------Cust Info Search Start-----------------------------------------------*/
	
	@Override
	public Integer countCustInfoSearchList(HostCustSModel params) throws Exception {
		return (Integer)baseDAO.queryForObject("statistic.countCustInfoSearchList", params);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<CustSearchModel> getCustInfoSearchList(HostCustSModel params) throws Exception {
		List<CustSearchModel> custSearchModel = baseDAO.queryForList("statistic.getCustInfoSearchList", params);
    	return custSearchModel;
	}
	/*-- -----------------------------------------Cust Info Search End-----------------------------------------------*/

	/*-- -----------------------------------------Sale Customer Statistic Start---------------------------------------------*/
	@Override
	public Integer countSaleCustStatisticList(BillProcSModel params) throws Exception {
		return (Integer)baseDAO.queryForObject("statistic.countSaleCustStatisticList", params);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<BillSearchModel> getSaleCustStatisticList(BillProcSModel params) throws Exception {
		List<BillSearchModel> saleCustSearchModel = baseDAO.queryForList("statistic.getSaleCustStatisticList", params);
    	return saleCustSearchModel;
	}
	
	/*--   Sale Customer Statistic by Day Start   --*/
	@Override
	public Integer countSaleCustStatisticByDayList(CommonStatisticSModel params) throws Exception {
		return (Integer)baseDAO.queryForObject("statistic.countSaleCustStatisticByDayList", params);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<BillSearchModel> getSaleCustStatisticByDayList(CommonStatisticSModel params) throws Exception {
		List<BillSearchModel> saleCustSearchModel = baseDAO.queryForList("statistic.getSaleCustStatisticByDayList", params);
    	return saleCustSearchModel;
	}
	/*-- -----------------------------------------Sale Customer Statistic End---------------------------------------------*/
	
	
}

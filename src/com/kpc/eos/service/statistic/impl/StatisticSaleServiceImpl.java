
package com.kpc.eos.service.statistic.impl;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.kpc.eos.core.service.BaseService;
import com.kpc.eos.model.statistic.BillSearchModel;
import com.kpc.eos.model.statistic.CommonStatisticSModel;
import com.kpc.eos.model.statistic.SaleEmpSearchModel;
import com.kpc.eos.model.statistic.StatisticTotalModel;
import com.kpc.eos.model.statistic.StockItemDetailModel;
import com.kpc.eos.service.statistic.StatisticSaleService;

@Transactional
public class StatisticSaleServiceImpl extends BaseService implements StatisticSaleService {
	

	/*-- -----------------------------------------Sale Customer Statistic Start---------------------------------------------*/
	@Override
	public StatisticTotalModel countSaleDaySearchList(CommonStatisticSModel params) throws Exception {
		return (StatisticTotalModel)baseDAO.queryForObject("statisticsale.countSaleDayStatisticList", params);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<BillSearchModel> getSaleDaySearchList(CommonStatisticSModel params) throws Exception {
		List<BillSearchModel> saleCustSearchModel = baseDAO.queryForList("statisticsale.getSaleDayStatisticList", params);
    	return saleCustSearchModel;
	}
	
	/*--   Sale Customer Statistic by Day Start   --*/
	@Override
	public Integer countSaleDayDetailSearchList(CommonStatisticSModel params) throws Exception {
		return (Integer)baseDAO.queryForObject("statisticsale.countSaleDayDetailStatisticList", params);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<BillSearchModel> getSaleDayDetailSearchList(CommonStatisticSModel params) throws Exception {
		List<BillSearchModel> saleCustSearchModel = baseDAO.queryForList("statisticsale.getSaleDayDetailStatisticList", params);
    	return saleCustSearchModel;
	}
	/*-- -----------------------------------------Sale Customer Statistic End---------------------------------------------*/
	
	/*-- -----------------------------------------Sale Item Statistic Start---------------------------------------------*/
	@Override
	public StatisticTotalModel countSaleItemSearchList(CommonStatisticSModel params) throws Exception {
		return (StatisticTotalModel)baseDAO.queryForObject("statisticsale.countSaleItemSearchList", params);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<StockItemDetailModel> getSaleItemSearchList(CommonStatisticSModel params) throws Exception {
		List<StockItemDetailModel> saleItemSearchModel = baseDAO.queryForList("statisticsale.getSaleItemSearchList", params);
    	return saleItemSearchModel;
	}
	
	/*--   Sale Customer Statistic by Day Start   --*/
	@Override
	public Integer countSaleItemDetailSearchList(CommonStatisticSModel params) throws Exception {
		return (Integer)baseDAO.queryForObject("statisticsale.countSaleItemDetailSearchList", params);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<StockItemDetailModel> getSaleItemDetailSearchList(CommonStatisticSModel params) throws Exception {
		List<StockItemDetailModel> saleItemDetailSearchModel = baseDAO.queryForList("statisticsale.getSaleItemDetailSearchList", params);
    	return saleItemDetailSearchModel;
	}
	/*-- -----------------------------------------Sale Customer Statistic End---------------------------------------------*/

	/*-- -----------------------------------------Sale Clerk Statistic Start------------------------------------------------*/
	@Override
	public StatisticTotalModel countSaleClerkSearchList(CommonStatisticSModel params) throws Exception {
		return (StatisticTotalModel)baseDAO.queryForObject("statisticsale.countSaleClerkSearchList", params);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SaleEmpSearchModel> getSaleClerkSearchList(CommonStatisticSModel params) throws Exception {
		List<SaleEmpSearchModel> saleEmpSearchModel = baseDAO.queryForList("statisticsale.getSaleClerkSearchList", params);
    	return saleEmpSearchModel;
	}	
	/*-- -----------------------------------------Sale Clerk Statistic End------------------------------------------------*/
}

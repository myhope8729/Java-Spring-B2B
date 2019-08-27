
package com.kpc.eos.service.statistic;

import java.util.HashMap;
import java.util.List;

import com.kpc.eos.model.billProcMng.BillProcSModel;
import com.kpc.eos.model.bizSetting.HostCustSModel;
import com.kpc.eos.model.statistic.BillSearchModel;
import com.kpc.eos.model.statistic.CommonStatisticSModel;
import com.kpc.eos.model.statistic.CustSearchModel;
import com.kpc.eos.model.statistic.StatisticTotalModel;
import com.kpc.eos.model.statistic.StockItemDetailModel;
import com.kpc.eos.model.statistic.StockSearchSModel;
import com.kpc.eos.model.statistic.UserStockItemModel;

public interface StatisticService {
	
	/*-- -----------------------------------------BillSearch Start---------------------------------------------------------*/
	public Integer countBillSearchList(BillProcSModel params) throws Exception;
    public List<BillSearchModel> getBillSearchList(BillProcSModel params) throws Exception;
    public String getPrintTypeByUser(String userId) throws Exception;
	/*-- -----------------------------------------BillSearch End-----------------------------------------------------------*/
    
	/*-- -----------------------------------------Stock Search Start-------------------------------------------------------*/
    public List<HashMap<String,String>> getStoreComboListByUser(String userId) throws Exception;
    public String getItemCat1FieldByUser(String userId, String PropType) throws Exception ;
    public List<HashMap<String,String>> getItemCategory1ListByUser(String userId, String PropType, String catField) throws Exception ;
    
    public StatisticTotalModel countStockItemListByUser(StockSearchSModel params) throws Exception;
    public List<UserStockItemModel> getStockItemListByUser(StockSearchSModel params) throws Exception;
    
    
    /*--   Stock Detail Search Start   --*/
	public Integer countStockDetailSearchList(BillProcSModel params) throws Exception;
    public List<StockItemDetailModel> getStockDetailSearchList(BillProcSModel params) throws Exception;
    /*-- -----------------------------------------Stock Search End---------------------------------------------------------*/
	
    /*-- -----------------------------------------Cust Info Search Start---------------------------------------------------*/
    public Integer countCustInfoSearchList(HostCustSModel params) throws Exception;
    public List<CustSearchModel> getCustInfoSearchList(HostCustSModel params) throws Exception;
    /*-- -----------------------------------------Cust Info Search End------------------------------------------------------*/
    
    /*-- -----------------------------------------Sale Customer Statistic Start---------------------------------------------*/
    public Integer countSaleCustStatisticList(BillProcSModel params) throws Exception;
    public List<BillSearchModel> getSaleCustStatisticList(BillProcSModel params) throws Exception;
    
    /*--   Sale Customer Statistic by Day Start   --*/
    public Integer countSaleCustStatisticByDayList(CommonStatisticSModel params) throws Exception;
    public List<BillSearchModel> getSaleCustStatisticByDayList(CommonStatisticSModel params) throws Exception;
    /*-- -----------------------------------------Sale Customer Statistic End-----------------------------------------------*/    
}

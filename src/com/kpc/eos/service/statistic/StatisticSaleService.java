
package com.kpc.eos.service.statistic;

import java.util.List;

import com.kpc.eos.model.statistic.BillSearchModel;
import com.kpc.eos.model.statistic.CommonStatisticSModel;
import com.kpc.eos.model.statistic.SaleEmpSearchModel;
import com.kpc.eos.model.statistic.StatisticTotalModel;
import com.kpc.eos.model.statistic.StockItemDetailModel;

public interface StatisticSaleService {
	
    /*-- -----------------------------------------Sale Day Statistic Start---------------------------------------------*/
    public StatisticTotalModel countSaleDaySearchList(CommonStatisticSModel params) throws Exception;
    public List<BillSearchModel> getSaleDaySearchList(CommonStatisticSModel params) throws Exception;
    
    /*--   Sale Day Detail Statistic by cust   --*/
    public Integer countSaleDayDetailSearchList(CommonStatisticSModel params) throws Exception;
    public List<BillSearchModel> getSaleDayDetailSearchList(CommonStatisticSModel params) throws Exception;
    /*-- -----------------------------------------Sale Day Statistic End-----------------------------------------------*/     
    
    /*-- -----------------------------------------Sale Item Statistic Start--------------------------------------------*/
    public StatisticTotalModel countSaleItemSearchList(CommonStatisticSModel params) throws Exception;
    public List<StockItemDetailModel> getSaleItemSearchList(CommonStatisticSModel params) throws Exception;
    
    /*--   Sale Day Detail Statistic by cust   --*/
    public Integer countSaleItemDetailSearchList(CommonStatisticSModel params) throws Exception;
    public List<StockItemDetailModel> getSaleItemDetailSearchList(CommonStatisticSModel params) throws Exception;
    /*-- -----------------------------------------Sale Item Statistic End----------------------------------------------*/   
    
    /*-- -----------------------------------------Sale Clerk Statistic Start--------------------------------------------*/
    public StatisticTotalModel countSaleClerkSearchList(CommonStatisticSModel params) throws Exception;
    public List<SaleEmpSearchModel> getSaleClerkSearchList(CommonStatisticSModel params) throws Exception;
    /*-- -----------------------------------------Sale Clerk Statistic End----------------------------------------------*/      
}

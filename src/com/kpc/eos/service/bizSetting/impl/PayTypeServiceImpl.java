
package com.kpc.eos.service.bizSetting.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.transaction.annotation.Transactional;

import com.kpc.eos.core.service.BaseService;
import com.kpc.eos.model.bill.SubPayTypeModel;
import com.kpc.eos.model.bizSetting.PayTypeModel;
import com.kpc.eos.model.bizSetting.PayTypeSModel;
import com.kpc.eos.model.common.DefaultSModel;
import com.kpc.eos.service.bizSetting.PayTypeService;

@Transactional
public class PayTypeServiceImpl extends BaseService implements PayTypeService {
	
	@SuppressWarnings("unchecked")
	@Override
	public List<PayTypeModel> getPayTypeList(DefaultSModel sc) throws Exception {
		List<PayTypeModel> payTypeList = baseDAO.queryForList("payType.getPayTypeList", sc);
		return payTypeList;
	}
	
	public Integer getTotalCountPayTypeList(DefaultSModel sc) throws Exception{
		return (Integer) baseDAO.queryForObject("payType.getTotalCountPayType", sc);
	}
	
	@Override
	public PayTypeModel getPayType(String payTypeId) throws Exception {
		PayTypeModel payType =  (PayTypeModel) baseDAO.queryForObject("payType.getPaytype", payTypeId);
		return payType;
	}
	
	@Override
	public void savePayType(PayTypeModel payType) throws Exception {
		if (StringUtils.isNotEmpty(payType.getPayTypeId()))
		{
			baseDAO.update("payType.updatePayType", payType);
		}
		else
		{
			baseDAO.insert("payType.insertPayType", payType);
		}
	}
	
	@Override
	public Integer existPayTypeName(PayTypeModel payType) throws Exception
	{
		return (Integer) baseDAO.queryForObject("payType.checkExistPayType", payType);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<PayTypeModel> getUserPayTypeList(PayTypeSModel sc) throws Exception
	{
		List<PayTypeModel> payTypeList = baseDAO.queryForList("payType.getUserPayTypeList", sc);
		return payTypeList;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<PayTypeModel> getUserPrePayTypeList(PayTypeSModel sc) throws Exception
	{
		List<PayTypeModel> payTypeList = baseDAO.queryForList("payType.getUserPrePayTypeList", sc);
		return payTypeList;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<PayTypeModel> getUserPrePayTypeForCustList(PayTypeSModel sc) throws Exception
	{
		List<PayTypeModel> payTypeList = baseDAO.queryForList("payType.getUserPrePayTypeForUserList", sc);
		return payTypeList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PayTypeModel> getAvailablePayTypeList(String userId) throws Exception {
		List<PayTypeModel> availablePayTypeList = baseDAO.queryForList("payType.getAvailblePayTypeList", userId);
		return availablePayTypeList;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public SubPayTypeModel getActiveUserSubPayType(String userId, String custtypeId, String paytypeId, String seqNo) throws Exception
	{
		Map param = new HashMap();
		param.put("userId", userId);
		param.put("custtypeId", custtypeId);
		param.put("paytypeId", paytypeId);
		param.put("seqNo", seqNo);
		
		return (SubPayTypeModel) baseDAO.queryForObject("payType.getActiveUserSubPayType", param);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List<SubPayTypeModel> getActiveUserSubPayTypeList(String userId, String custtypeId, String paytypeId) throws Exception
	{
		Map param = new HashMap();
		param.put("userId", userId);
		param.put("custtypeId", custtypeId);
		param.put("paytypeId", paytypeId);
		return (List<SubPayTypeModel>) baseDAO.queryForList("payType.getActiveUserSubPayTypeList", param);
	}
	
}

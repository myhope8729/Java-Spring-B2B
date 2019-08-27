
package com.kpc.eos.service.bizSetting.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.transaction.annotation.Transactional;

import com.kpc.eos.core.service.BaseService;
import com.kpc.eos.model.bizSetting.CustTypeModel;
import com.kpc.eos.model.common.DefaultSModel;
import com.kpc.eos.service.bizSetting.CustTypeService;

@Transactional
public class CustTypeServiceImpl extends BaseService implements CustTypeService {
	
	@SuppressWarnings("unchecked")
	@Override
	public List<CustTypeModel> getCustTypeList(DefaultSModel sc) throws Exception {
		List<CustTypeModel> custTypeList = baseDAO.queryForList("custType.getCustTypeList", sc);
		return custTypeList;
	}
	
	@Override
	public Integer getTotalCountCustTypeList(DefaultSModel sc) throws Exception
	{
		return (Integer)baseDAO.queryForObject("custType.getTotalCountCustTypeList", sc);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<CustTypeModel> getCustTypeListByUser(String userId) throws Exception {
		List<CustTypeModel> custTypeList = baseDAO.queryForList("custType.findCustTypeListByUser", userId);
		return custTypeList;
	}
	
	@Override
	public CustTypeModel getCustType(String custTypeId) throws Exception {
		CustTypeModel custType =  (CustTypeModel) baseDAO.queryForObject("custType.getCustType", custTypeId);
		return custType;
	}
	
	@Override
	public void saveCustType(CustTypeModel custType) throws Exception {
		if (StringUtils.isNotEmpty(custType.getCustTypeId()))
		{
			baseDAO.update("custType.updateCustType", custType);
		}
		else
		{
			baseDAO.insert("custType.insertCustType", custType);
		}
	}
	
	@Override
	public Integer existCustTypeName(CustTypeModel custType) throws Exception
	{
		return (Integer)baseDAO.queryForObject("custType.checkExistCustTypeName", custType);
	}
}

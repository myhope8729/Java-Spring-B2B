
package com.kpc.eos.service.bizSetting;

import java.util.List;

import com.kpc.eos.model.bizSetting.CustTypeModel;
import com.kpc.eos.model.common.DefaultSModel;

public interface CustTypeService {
	
	public List<CustTypeModel> getCustTypeList(DefaultSModel sc) throws Exception;
	
	public Integer getTotalCountCustTypeList(DefaultSModel sc) throws Exception;
	
	public List<CustTypeModel> getCustTypeListByUser(String userId) throws Exception;
	
	public CustTypeModel getCustType(String custTypeId) throws Exception;
	
	public void saveCustType(CustTypeModel custType) throws Exception;
	
	public Integer existCustTypeName(CustTypeModel custType) throws Exception;
	
}

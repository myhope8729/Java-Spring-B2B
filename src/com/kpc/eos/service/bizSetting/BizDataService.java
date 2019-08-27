
package com.kpc.eos.service.bizSetting;

import java.util.List;
import java.util.Map;

import com.kpc.eos.model.bizSetting.BizDataModel;


public interface BizDataService {
	public List<BizDataModel> getBizDataByBizCode(String userId, String codeId, String seqNo) throws Exception;
	
	public List<BizDataModel> getBizDataMainList() throws Exception;
	
	public List<BizDataModel> getBizDataList(String userId, String codeId) throws Exception;
	
	public void saveBizData(String userId, String empId, String codeId, String[] c1, String[] c2, String[] c3, String[] c4, String[] c5) throws Exception;
	
	public void deleteBizData(String userId, String codeId, String[] seqNo) throws Exception;
	
	@SuppressWarnings("rawtypes")
	public BizDataModel getBizDataForHostCust(Map map) throws Exception;
}

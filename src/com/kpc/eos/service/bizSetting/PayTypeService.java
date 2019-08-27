
package com.kpc.eos.service.bizSetting;

import java.util.List;

import com.kpc.eos.model.bill.SubPayTypeModel;
import com.kpc.eos.model.bill.SubPayTypeSModel;
import com.kpc.eos.model.bizSetting.PayTypeModel;
import com.kpc.eos.model.bizSetting.PayTypeSModel;
import com.kpc.eos.model.common.DefaultSModel;

public interface PayTypeService {
	
	public Integer getTotalCountPayTypeList(DefaultSModel sc) throws Exception;
	
	public List<PayTypeModel> getPayTypeList(DefaultSModel sc) throws Exception;
		
	public PayTypeModel getPayType(String payTypeId) throws Exception;
	
	public void savePayType(PayTypeModel dept) throws Exception;
	
	public List<PayTypeModel> getUserPayTypeList(PayTypeSModel sc) throws Exception;
	
	public List<PayTypeModel> getUserPrePayTypeList(PayTypeSModel sc) throws Exception;
	
	public List<PayTypeModel> getUserPrePayTypeForCustList(PayTypeSModel sc) throws Exception;
	
	public List<PayTypeModel> getAvailablePayTypeList(String userId) throws Exception;
	
	public Integer existPayTypeName(PayTypeModel payType) throws Exception;
	
	// ============= sub payment type section =========================== /
	public SubPayTypeModel getActiveUserSubPayType(String userId, String custtypeId, String paytypeId, String seqNo) throws Exception;
	
	public List<SubPayTypeModel> getActiveUserSubPayTypeList(String userId, String custtypeId, String paytypeId) throws Exception;
	
	
}

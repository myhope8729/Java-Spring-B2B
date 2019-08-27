
package com.kpc.eos.service.bizSetting;

import java.util.HashMap;
import java.util.List;

import com.kpc.eos.model.bizSetting.BizSettingModel;
import com.kpc.eos.model.bizSetting.BizSettingSModel;


public interface BizSettingService {
	public BizSettingModel getBizSettingBySysType(String userId, String sysCode) throws Exception;
	
	public List<BizSettingModel> getBizSettingList(BizSettingSModel sc) throws Exception;
	
	public List<BizSettingModel> getBizSettingDetailInfo() throws Exception;
	
	public void saveBizSetting(String bizSettingStr, String userId) throws Exception;
	
	public HashMap<String, String> getUserBizSettingMap(String userId, String sysKindId) throws Exception;
}

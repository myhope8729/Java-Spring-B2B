
package com.kpc.eos.service.bizSetting;

import java.util.List;

import com.kpc.eos.model.bizSetting.HostCustModel;
import com.kpc.eos.model.bizSetting.HostCustSModel;
import com.kpc.eos.model.bizSetting.PayTypeModel;

public interface HostCustService {
	
	public List<HostCustModel> getCustSettingList(HostCustSModel sc) throws Exception;
	public Integer getTotalCountCustSettingList(HostCustSModel sc) throws Exception;
	
	public List<PayTypeModel> getPayTypeListByUser(String hostId, String custId) throws Exception;
	
	public List<HostCustModel> getCustListByHostIdForCopy(String hostId) throws Exception;
	
	public HostCustModel getCustSetting(String hostId, String custId) throws Exception;
	
	public HostCustModel getHostCustSetting(String hostId, String custId) throws Exception;
	
	public void saveCustSetting(HostCustModel custSetting) throws Exception;
	
	public List<HostCustModel> gethostSettingList(HostCustSModel sc) throws Exception;
	
	public Integer getTotalCountHostSettingList(HostCustSModel sc) throws Exception;
	
	public List<HostCustModel> getSupplyList(HostCustSModel sc) throws Exception;
	
	public Integer getTotalSupplyList(HostCustSModel sc) throws Exception;
	
	public void insertHostList(HostCustModel sc) throws Exception;
	
	public void deleteHost(String hostId, String custId) throws Exception;
	
	public List<HostCustModel> getHostList(String custId) throws Exception;
	
	public List<HostCustModel> getHostListForOrder(HostCustSModel sc) throws Exception;
	
	public Integer countHostListForOrder(HostCustSModel sc) throws Exception;
	
	public List<HostCustModel> getHostCustList(HostCustSModel sc) throws Exception;
	
	public Integer countHostCustList(HostCustSModel sc) throws Exception;
	
}

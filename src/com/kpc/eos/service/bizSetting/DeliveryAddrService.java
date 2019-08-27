
package com.kpc.eos.service.bizSetting;

import java.util.List;

import com.kpc.eos.model.bizSetting.DeliveryAddrModel;
import com.kpc.eos.model.common.DefaultSModel;

public interface DeliveryAddrService {
	
	public List<DeliveryAddrModel> getDeliveryAddrList(DefaultSModel sc) throws Exception;
	
	public Integer getTotalCountDeliveryAddrList(DefaultSModel sc) throws Exception;
	
	public List<DeliveryAddrModel> getUserDeliveryAddrList(String userId) throws Exception;
	
	public DeliveryAddrModel getDeliveryAddr(String addrId) throws Exception;
	
	public void saveDeliveryAddr(DeliveryAddrModel deliveryAddr) throws Exception;
}

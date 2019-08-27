
package com.kpc.eos.service.bizSetting.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.transaction.annotation.Transactional;

import com.kpc.eos.core.Constants;
import com.kpc.eos.core.service.BaseService;
import com.kpc.eos.model.bizSetting.DeliveryAddrModel;
import com.kpc.eos.model.common.DefaultSModel;
import com.kpc.eos.service.bizSetting.DeliveryAddrService;

@Transactional
public class DeliveryAddrServiceImpl extends BaseService implements DeliveryAddrService {
	
	@SuppressWarnings("unchecked")
	@Override
	public List<DeliveryAddrModel> getDeliveryAddrList(DefaultSModel sc) throws Exception {
		List<DeliveryAddrModel> deliveryAddrList = baseDAO.queryForList("deliveryAddr.getDeliveryAddrList", sc);
		return deliveryAddrList;
	}
	
	@Override
	public Integer getTotalCountDeliveryAddrList(DefaultSModel sc) throws Exception
	{
		return (Integer) baseDAO.queryForObject("deliveryAddr.getTotalCountDeliveryAddrList", sc);
	}
	
	@Override
	public DeliveryAddrModel getDeliveryAddr(String addrId) throws Exception {
		DeliveryAddrModel deliveryAddr =  (DeliveryAddrModel) baseDAO.queryForObject("deliveryAddr.getDeliveryAddr", addrId);
		return deliveryAddr;
	}
	
	@Override
	public void saveDeliveryAddr(DeliveryAddrModel deliveryAddr) throws Exception {
		if (deliveryAddr.getDefaultYn().equals(Constants.CONST_Y))
		{
			baseDAO.update("deliveryAddr.updateDefaultYn", deliveryAddr);
		}
		
		if (StringUtils.isNotEmpty(deliveryAddr.getAddrId()))
		{
			baseDAO.update("deliveryAddr.updateDeliveryAddr", deliveryAddr);
		}
		else
		{
			baseDAO.insert("deliveryAddr.insertDeliveryAddr", deliveryAddr);
		}
	}

	@Override
	public List<DeliveryAddrModel> getUserDeliveryAddrList(String userId)
			throws Exception
	{
		List<DeliveryAddrModel> deliveryAddrList = baseDAO.queryForList("deliveryAddr.getUserDeliveryAddrList", userId);
		return deliveryAddrList;
	}
}

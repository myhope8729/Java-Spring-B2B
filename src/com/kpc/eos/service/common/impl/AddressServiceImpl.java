/**
 * 
 */
package com.kpc.eos.service.common.impl;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.kpc.eos.core.service.BaseService;
import com.kpc.eos.model.common.AddressModel;
import com.kpc.eos.service.common.AddressService;

/**
 * @author Administrator
 *
 */
@Transactional
public class AddressServiceImpl extends BaseService implements AddressService {

	@SuppressWarnings("unchecked")
	@Override
	public List<AddressModel> findProvinceList() throws Exception {
		return baseDAO.queryForList("address.findProvinceList", null);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AddressModel> findChildLocationList(String upperId) throws Exception {
		return baseDAO.queryForList("address.findChildLocationList", upperId);
	}

	@Override
	public List<AddressModel> findAreaList(String cityId) throws Exception {
		return null;//baseDAO.queryForList("address.findAreaList", cityId);
	}

	@Override
	public List<AddressModel> findAllAreaList(String provId) throws Exception {
		return null;//baseDAO.queryForList("address.findAllAreaList", provId);
	}

	@Override
	public boolean isProvince(String provId) throws Exception {
		//Integer result = (Integer) baseDAO.queryForObject("address.isProvince", provId);
		return false;
	}

	@Override
	public AddressModel getAddressByLocationId(String locationId) throws Exception 
	{
		return (AddressModel)baseDAO.queryForObject("address.getAddressByLocationId", locationId);
	}

	@Override
	public String getFullAddressByLocationId(String locationId)
			throws Exception
	{
		AddressModel userLocationsTmp = getAddressByLocationId(locationId);
		if (userLocationsTmp == null) return "";
		userLocationsTmp.reArrange();
		return  userLocationsTmp.getFullAddress();
	}
}

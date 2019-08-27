/**
 * 
 */
package com.kpc.eos.service.common;

import java.util.List;

import com.kpc.eos.model.common.AddressModel;

/**
 * @author Administrator
 *
 */
public interface AddressService {
	/**
	 * findProvinceList
	 * ===================
	 * Province  
	 * @param 
	 * @return Address List
	 * @throws Exception
	 */
	public List<AddressModel> findProvinceList() throws Exception;
	
	/**
	 * findChildLocationList
	 * ===================
	 * City  
	 * @param 
	 * @return Address List
	 * @throws Exception
	 */
	public List<AddressModel> findChildLocationList(String upperId) throws Exception;	
	
	/**
	 * findAreaList
	 * ===================
	 * Area  
	 * @param 
	 * @return Address List
	 * @throws Exception
	 */
	public List<AddressModel> findAreaList(String cityId) throws Exception;
	
	/**
	 * findAllAreaList
	 * ===================
	 * Area  
	 * @param 
	 * @return Address List
	 * @throws Exception
	 */
	public List<AddressModel> findAllAreaList(String provId) throws Exception;
	
	
	/**
	 * Description	: get the address row by location id string.
	 * @author 		: RKRK
	 * @param provId
	 * @return
	 * @throws Exception
	 * 2017
	 */
	public AddressModel getAddressByLocationId(String locationId) throws Exception;
	
	public String getFullAddressByLocationId(String locationId) throws Exception;
	
	public boolean isProvince(String provId) throws Exception;
}

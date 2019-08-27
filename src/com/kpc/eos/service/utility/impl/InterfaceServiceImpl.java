package com.kpc.eos.service.utility.impl;

import java.util.List;

import com.kpc.eos.core.service.BaseService;
import com.kpc.eos.model.utility.IFParam;
import com.kpc.eos.model.utility.Interface;
import com.kpc.eos.service.utility.InterfaceService;

public class InterfaceServiceImpl extends BaseService implements InterfaceService {

	@SuppressWarnings("unchecked")
	@Override
	public List<Interface> selectInterfaceList() throws Exception {
		return baseDAO.queryForList("interface.selectInterfaceList", null);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<IFParam> selectIFParamList(String ifId) throws Exception {
		return baseDAO.queryForList("interface.selectIFParamList", ifId);
	}

	@Override
	public Interface getInterfaceInfo(String ifId) throws Exception {
		Interface result = (Interface) baseDAO.queryForObject("interface.getInterfaceInfo", ifId);
		
		return result;
	}

}

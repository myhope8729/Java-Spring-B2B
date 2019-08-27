package com.kpc.eos.service.utility;

import java.util.List;

import com.kpc.eos.model.utility.IFParam;
import com.kpc.eos.model.utility.Interface;

public interface InterfaceService {

	public List<Interface> selectInterfaceList() throws Exception;
	public Interface getInterfaceInfo(String ifId) throws Exception;
	
	public List<IFParam> selectIFParamList(String ifId) throws Exception;
}

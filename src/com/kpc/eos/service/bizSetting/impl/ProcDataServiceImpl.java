
package com.kpc.eos.service.bizSetting.impl;

import org.springframework.transaction.annotation.Transactional;

import com.kpc.eos.core.service.BaseService;
import com.kpc.eos.service.bizSetting.ProcDataService;

@Transactional
public class ProcDataServiceImpl extends BaseService implements ProcDataService {
	
	public void deleteProcGroupByUser(String userId) throws Exception{
		baseDAO.delete("procData.delteProcGroupByUser", userId);
	}
}

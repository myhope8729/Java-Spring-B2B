
package com.kpc.eos.service.system.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

import com.kpc.eos.core.service.BaseService;
import com.kpc.eos.model.system.CodeModel;
import com.kpc.eos.model.system.CodeSModel;
import com.kpc.eos.service.system.CodeService;

/**
 * CodeServiceImpl
 * =================
 * Description :  
 * Methods :
 */
@SuppressWarnings("unchecked")
@Transactional
public class CodeServiceImpl extends BaseService implements CodeService {
	
	private Logger logger = Logger.getLogger(this.getClass());

	@Override
	public List<CodeModel> findCodeGroupList(CodeSModel sc) throws Exception {
		return baseDAO.queryForList("code.findUpperCodeList", sc);
	}

	
	@Override
	public List<CodeModel> findCodeList(String codeGroupId) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		map.put("codeGroupId", codeGroupId);
		return baseDAO.queryForList("code.findCodeList", map);
	}
	
	@Override
	public CodeModel checkCodeId(CodeModel code) throws Exception {
		return (CodeModel) baseDAO.queryForObject("code.findCodeList", code);		
	}
	
	@Override
	public CodeModel getCode(String codeId) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		map.put("codeId", codeId);
		return (CodeModel) baseDAO.queryForObject("code.findCodeList", map);
	}

	@Override
	public void saveCode(List<CodeModel> paramList) throws Exception {
		ArrayList<CodeModel> cParamList = new ArrayList<CodeModel>();
		ArrayList<CodeModel> uParamList = new ArrayList<CodeModel>();
		for (CodeModel model : paramList) {
			if(!StringUtils.isEmpty(model.getCrud()) && model.getCrud().equals("C")) {
				cParamList.add(model);
			} else {
				uParamList.add(model);
			}
		}
			baseDAO.batchUpdate("code.updateCode", uParamList);
			baseDAO.batchInsert("code.insertCode", cParamList);
	}
	
	@Override
	public void deleteCode(String[] codeIds) throws Exception {
		Map<String, Object> data = new HashMap<String, Object>();
		//TODO
		data.put("lastUpdateBy", "system");
		data.put("codeIds", codeIds);
		
		baseDAO.update("code.deleteCode", data);
	}

	@Override
	public List<CodeModel> findUseCodeList() {
		List<CodeModel> list = null;
		try {
			list = baseDAO.queryForList("code.findUseCodeList", null);
		} catch(SQLException e) {
			logger.debug("Error in Common Code List", e);
		}
		return list;
	}
	
	@Override
	public List<CodeModel> getCodeList(CodeSModel sc) throws Exception {
		return baseDAO.queryForList("code.getCodelist", sc);
	}

}
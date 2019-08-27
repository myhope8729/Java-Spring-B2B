
package com.kpc.eos.service.system;

import java.util.List;

import com.kpc.eos.model.system.CodeModel;
import com.kpc.eos.model.system.CodeSModel;


/**
 * CodeService
 * =================
 * Description : Code Service
 * Methods :
 */
public interface CodeService {
	
	/**
	 * findCodeGroupList
	 * ===================
	 * Code Group List
	 * @param sc
	 * @return
	 * @throws Exception
	 */
	public List<CodeModel> findCodeGroupList(CodeSModel sc) throws Exception;
	
	/**
	 * findCodeList
	 * ===================
	 * Code List
	 * @param codeGroupId
	 * @return
	 * @throws Exception
	 */
	public List<CodeModel> findCodeList(String codeGroupId) throws Exception;
	
	/**
	 * checkCodeId
	 * ===================
	 * Check duplication of Code Id 
	 * @param codeId
	 * @return
	 * @throws Exception
	 */
	public CodeModel checkCodeId(CodeModel code) throws Exception;
	
	/**
	 * getCode
	 * ===================
	 * Code detail info
	 * @param codeId
	 * @return
	 * @throws Exception
	 */
	public CodeModel getCode(String codeId) throws Exception;
	
	/**
	 * saveCode
	 * ===================
	 * Code detail info insert/update
	 * @param code
	 * @throws Exception
	 */
	public void saveCode(List<CodeModel> paramList) throws Exception;
	
	/**
	 * deleteCode
	 * ===================
	 * delete code 
	 * @param codeIds 
	 * @throws Exception
	 */
	public void deleteCode(String[] codeIds) throws Exception;
	
	/**
	 * findUseCodeList
	 * ===================
	 * retrieve Code List that useyn is Y
	 * @return
	 */
	public List<CodeModel> findUseCodeList();
	
	/**
	 * Description	: get code list for code list page
	 * @author 		: RKRK
	 * @param sc
	 * @return CodeModel List
	 * @throws Exception
	 * 2017
	 */
	public List<CodeModel> getCodeList(CodeSModel sc) throws Exception;

}

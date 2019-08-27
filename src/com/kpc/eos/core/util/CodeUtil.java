
package com.kpc.eos.core.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.map.LinkedMap;
import org.apache.commons.lang.StringUtils;

import com.kpc.eos.core.exception.ServiceException;
import com.kpc.eos.model.system.CodeModel;
import com.kpc.eos.service.system.CodeService;

/**
 * CodeUtil
 * =================
 * Description : code utility
 * Methods :
 */
public class CodeUtil {

	private static Map<String, LinkedMap> 	codeGroupMap;
	
	private static final int GROUP_CODE_LENGTH = 5;
	
	/**
	 * loadCodeMap
	 * ===================
	 * Common Code List load
	 * @param codeGroupService
	 */
	public static void loadCodeMap(CodeService codeGroupService) {
		
		Map<String, LinkedMap>	tmpCodeGroupMap	= new Hashtable<String, LinkedMap>();
		
		List<CodeModel> codeList = codeGroupService.findUseCodeList();
		
		for (CodeModel codeItem : codeList) {
			if (StringUtils.isBlank(codeItem.getCodeGroupId()))
				continue;

			String codeGroup = codeItem.getCodeGroupId().trim();

			LinkedMap map = tmpCodeGroupMap.get(codeGroup);
			if (map == null) {
				map = new LinkedMap();
			}
			map.put(codeItem.getCodeId(), codeItem);
			
			tmpCodeGroupMap.put(codeGroup, map);
		}
		
		codeGroupMap = tmpCodeGroupMap;
	}
	
	/**
	 * getCodeList
	 * ===================
	 * Get Code List by a code group 
	 * @param codeGroup
	 * @return
	 */
	public static List getCodeList(String codeGroup){
		LinkedMap group = codeGroupMap.get(codeGroup);
		if(group != null) 
			return Arrays.asList(group.values().toArray());
		else 
			throw new ServiceException(MessageUtil.getMessage("system.alert.nodata") + "[codeGroup=" + codeGroup + "]");
	}
	
	/**
	 * getCodeList
	 * ===================
	 * Get Code List by a code group 
	 * @param codeGroup
	 * @return
	 */
	public static List getCodeList(String codeGroup, String exceptCode){
		LinkedMap group = codeGroupMap.get(codeGroup);
		if (exceptCode !=null && !StringUtils.isEmpty(exceptCode)){
			String[] exceptCodeList = exceptCode.split(",");
			for(String exceptCd : exceptCodeList){
				exceptCd = exceptCd.trim();
				group.remove(exceptCd);
			}
		}
		if(group != null){
			return Arrays.asList(group.values().toArray());
		}else 
			throw new ServiceException(MessageUtil.getMessage("system.alert.nodata") + "[codeGroup=" + codeGroup + "]");
	}

	/**
	 * getCodeName
	 * ===================
	 * Get the code name of a given code
	 * @param codeId
	 * @return
	 */
	public static String getCodeName(String codeId){
		
		String codeName = "NA";
		if (StringUtils.isBlank(codeId))
			return codeName;
		
		String groupCode = null;
		if(codeId.trim().length() > GROUP_CODE_LENGTH )
			groupCode = codeId.substring(0, 2) + "0000";
		else
			return codeName;
		
		LinkedMap codeMap = (LinkedMap) codeGroupMap.get(groupCode);
		
		if (codeMap != null) {
			CodeModel codeInfo = (CodeModel)codeMap.get(codeId);
			if(codeInfo != null) codeName = ((CodeModel)codeMap.get(codeId)).getCodeName();
		}
		return codeName;
	}
	
	/**
	 * getCodeName
	 * ===================
	 * Get the code description of a given code
	 * @param codeId
	 * @return
	 */
	public static String getCodeDesc(String codeId){
		
		String codeDesc = "NA";
		if (StringUtils.isBlank(codeId))
			return codeDesc;
		
		String groupCode = null;
		if(codeId.trim().length() > GROUP_CODE_LENGTH )
			groupCode = codeId.substring(0, GROUP_CODE_LENGTH);
		else
			return codeDesc;
		
		LinkedMap codeMap = (LinkedMap) codeGroupMap.get(groupCode);
		
		if (codeMap != null)
			codeDesc = ((CodeModel)codeMap.get(codeId)).getCodeDesc();
		
		return codeDesc;
	}
	
}

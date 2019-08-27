package com.kpc.eos.model.system;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import lombok.Data;

import org.apache.commons.lang.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import com.kpc.eos.core.util.FormatUtil;
import com.kpc.eos.model.common.DefaultSModel;

/**
 * Filename		: ListDataSaveModel.java
 * Description	:
 * 2017
 * @author		: RKRK
 */
@Data
public class ListDataSaveModel extends DefaultSModel {

	private static final long serialVersionUID = 3368964991367485827L;
    
	private String userListData;
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<?> makeModelList(Class<?> ModelClass) throws Exception {
		ArrayList paramList = new ArrayList();
		if(StringUtils.isEmpty(this.userListData)) {
			return null;
		} else {
			JSONArray jsonArr = new JSONArray(this.userListData);
			for (int i=0; i< jsonArr.length(); i++){
				JSONObject obj =  (JSONObject)jsonArr.get(i);
				Object newObj = ModelClass.newInstance();
				Method[] methods = ModelClass.getMethods();
				for (Method method : methods ) {
					for (String jsonname : JSONObject.getNames(obj)) {
						if(StringUtils.isEmpty(jsonname)) continue;
						if(method.getName().equals("set"+FormatUtil.toFirstUpperCase(jsonname))) {
							if(StringUtils.isNotEmpty(obj.getString(jsonname))) {
								Class<?> []ptype = method.getParameterTypes();
								if(ptype.length == 1) {
									if (ptype[0]== Integer.class) {
										method.invoke(newObj, Integer.parseInt(obj.getString(jsonname)));
									}
									else if (ptype[0] == String.class) {
										method.invoke(newObj, (String)obj.getString(jsonname));
									}
								}
								
							}
							
						}
					}
				}
				paramList.add(newObj);
			}
			
			return (List)paramList;
		}
	}
	
	public List<?> getUpdateParamList(List<?> paramList) {
		
		return paramList;
	}
	public ListDataSaveModel() {}
}
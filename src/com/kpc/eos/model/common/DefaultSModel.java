package com.kpc.eos.model.common;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import lombok.Data;

import com.kpc.eos.core.model.BaseModel;
import com.kpc.eos.core.model.PagingModel;
import com.kpc.eos.core.util.FormatUtil;

public @Data class DefaultSModel extends BaseModel {
	
	private static final long serialVersionUID = -4953841893556254317L;
	
	private String searchString;
	private String state;
	
	private PagingModel page;
	
	private String userListData;
	
	public DefaultSModel() 
	{
		this.page = new PagingModel();
	}
	
	public String toString()
	{
		return this.page.toString();
	}
	
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
		
}

/**
 * Filename		: KErrors.java
 * Description	:
 * 
 * 2017
 */
package com.kpc.eos.core.validation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;

import com.kpc.eos.core.util.MessageUtil;

/**
 * Filename		: FormErrors.java
 * Description	:
 * 2017
 * @author		: RKRK
 */
public class FormErrors extends BindException
{
	private static final long serialVersionUID = 1L;
	
	protected HashMap<String, String> fieldLabels = null;
	
	public FormErrors(Object target, String objectName)
	{
		super(target, objectName);	
		
		fieldLabels = new HashMap<String, String>();
	}
	
	public HashMap<String, String> getFieldLabels()
	{
		return this.fieldLabels;
	}
	
	public String getFieldLabel(String field)
	{
		if (fieldLabels.containsKey(field))
		{
			return fieldLabels.get(field);
		}
			
		return null;	
	}
	
	public String getErrorMsgList()
	{
		return null;
	}
	
	public String getErrorMsg(String fieldName)
	{
		FieldError fe = super.getFieldError(fieldName);
		if (fe == null) 
			return "";
		else
			return MessageUtil.getMessage(fe.getCode(), fe.getArguments());
	}
	
	public HashMap<Object, List<Object>> getErrorsMap(String fName)
	{
		HashMap<Object, List<Object>> ret = new HashMap<Object, List<Object>>();
		
		List<FieldError> list = super.getAllErrors();
		
		for (FieldError fe : list)
		{
			String fieldName = fe.getField();
			
			if (fName != null && !fName.equals(fieldName))
			{
				continue;
			}
			
			if (!ret.containsKey(fieldName))
			{
				ret.put(fieldName, new ArrayList<Object>());
			}
			
			ArrayList<Object> old = (ArrayList<Object>)ret.get(fieldName);
			old.add( MessageUtil.getMessage(fe.getCode(), fe.getArguments()) );
			
			ret.put(fieldName, old);
		}
		
		return ret;
	}
	
	@SuppressWarnings("rawtypes")
	public List getErrorsList(String fName)
	{
		HashMap<Object, List<Object>> map = getErrorsMap(fName);
		
		if (map.containsKey(fName))
		{
			return map.get(fName);
		}
		
		return new ArrayList<String>();
	}
}

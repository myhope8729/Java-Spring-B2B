/**
 * Filename		: JqGridUtil.java
 * Description	:
 * 
 * 2017
 */
package com.kpc.eos.core.util;

import org.json.JSONObject;


/**
 * Filename		: JqGridUtil.java
 * Description	:
 * 2017
 * @author		: RKRK
 */
public class JqGridUtil
{
	public static JSONObject getColModel(String name)
	{
		return getColModel(name, null, null);
	}
	
	public static JSONObject getColModel(String name, String index, Boolean key)
	{
		return getColModel(name, index, key, null, null);
	}
	
	public static JSONObject getColModel(String name, String index, Boolean key, Boolean sortable, String align)
	{
		JSONObject col = new JSONObject();
		col.put("name", name);
		col.put("index", (index==null)? name : index);
		
		if (key != null)
		{
			col.put("key", key);
		}
		if (sortable != null) 
		{
			col.put("sortable", sortable);
		}
		
		if (align != null) 
		{
			col.put("align", align);
		}
		
		return col;
	}
	
	
	public static JSONObject getColModel(String name, String index, Boolean key, Boolean sortable, String align, String width, String formatter, JSONObject formatoptions)
	{
		JSONObject col = new JSONObject();
		col.put("name", name);
		col.put("index", (index==null)? name : index);
		
		if (key != null)
		{
			col.put("key", key);
		}
		if (sortable != null) 
		{
			col.put("sortable", sortable);
		}
		
		if (align != null) 
		{
			col.put("align", align);
		}
		
		if (width != null) 
		{
			col.put("width", width);
		}
		
		if (formatter != null) 
		{
			col.put("formatter", formatter);
		}
		
		if (formatoptions != null) 
		{
			col.put("formatoptions", formatoptions);
		}
		return col;
	}	
	
}

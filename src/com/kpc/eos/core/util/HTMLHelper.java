package com.kpc.eos.core.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.util.ArrayUtil;

import com.kpc.eos.core.Constants;
import com.kpc.eos.core.model.BaseModel;
import com.kpc.eos.model.bizSetting.UserItemCategoryMdoel;
import com.sun.org.apache.bcel.internal.generic.Type;

public class HTMLHelper 
{
	public static String dropdownList(String name, Map<String, String> options, String selected, String extra) 
	{
		StringBuilder html = new StringBuilder();
		
		for (int i=0; i<options.size(); i++) 
		{
			
		}
		
		return html.toString();
	}
	
	/**
	 * Description	: Get the Yes/No List. 
	 * @author 		: RKRK
	 * @return
	 * 2017
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static List getYnList()
	{
		List ret = new ArrayList<HashMap>();
		
		Map map = new HashMap();
		map.put("key", Constants.CONST_Y);	
		map.put("value", Constants.CONST_Y_STR);	
		ret.add(map);
		
		map = new HashMap();
		map.put("key", Constants.CONST_N);	
		map.put("value", Constants.CONST_N_STR);	
		ret.add(map);
		
		return ret;
	}
	
	/**
	 * Description	:
	 * @author 		: RKRK
	 * @param catList
	 * @param level
	 * @return
	 * 2018
	 */
	public static String getItemCatHtml(List<UserItemCategoryMdoel> catList, int level)
	{
		if (catList == null || catList.size() < 1) return "";
		
		StringBuilder sb = new StringBuilder();
		
		if (level == 0)
		{
			sb.append("<ul class='store-menu'>");
		} else {
			sb.append("<ul>");
		}
		
		for (UserItemCategoryMdoel cat : catList)
		{
			sb.append("<li>");
			String template = "<a href='javascript:void(0)' class='%s' catName='%s'>%s <span>%s</span></a>";
			String label = cat.getCatName();
			if (StringUtils.isEmpty(label)) 
			{
				label = "未分类";
			}
			
			if ( 0 != cat.getCntLong() ) 
			{
				label += " (" + cat.getCnt() + ")";
			}
			boolean hasChild = cat.hasChildren();
			sb.append(String.format(template, 
					hasChild? "cat-item submenu" : "cat-item", 
					StringUtils.isEmpty(cat.getCatName())? "-1" : cat.getCatName() , 
					hasChild? "<i class='fa fa-chevron-right'></i>" : "", 
					label ));
			
			if ( hasChild ) 
			{
				sb.append( getItemCatHtml(cat.getChildren(), level+1) );
			}
			
			sb.append("</li>");
		}
		
		sb.append("</ul>");
		return sb.toString();
	}
	
	/**
	 * Description	:
	 * @author 		: RKRK
	 * @param catList
	 * @param level
	 * @return
	 * 2018
	 */
	public static String getItemCatHtmlOnMobile(List<UserItemCategoryMdoel> catList, int level)
	{
		if (catList == null || catList.size() < 1) return "";
		
		StringBuilder sb = new StringBuilder();
		
		if (level == 0)
		{
			sb.append("<ul class='store-menu'>");
		} else {
			sb.append("<ul class=''>");
		}
		
		for (UserItemCategoryMdoel cat : catList)
		{
			sb.append("<li>");
			String template = "<a href='javascript:void(0)' class='%s' catName='%s'>%s <span>%s</span></a>";
			String label = cat.getCatName();
			if (StringUtils.isEmpty(label)) 
			{
				label = "未分类";
			}
			
			if ( 0 != cat.getCntLong() ) 
			{
				label += " (" + cat.getCnt() + ")";
			}
			boolean hasChild = cat.hasChildren();
			sb.append(String.format(template, 
					hasChild? "cat-item submenu" : "cat-item", 
					StringUtils.isEmpty(cat.getCatName())? "-1" : cat.getCatName() , 
					hasChild? "<i class='fa fa-chevron-right'></i>" : "", 
					label ));
			
			if ( hasChild ) 
			{
				sb.append( getItemCatHtml(cat.getChildren(), level+1) );
			}
			
			sb.append("</li>");
		}
		
		sb.append("</ul>");
		return sb.toString();
	}
	
}

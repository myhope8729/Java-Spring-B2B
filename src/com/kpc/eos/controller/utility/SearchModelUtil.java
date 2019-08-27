package com.kpc.eos.controller.utility;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import com.kpc.eos.core.Constants;
import com.kpc.eos.core.util.SessionUtil;
import com.kpc.eos.model.common.DefaultSModel;

public class SearchModelUtil
{
	public static  List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected static List<HashMap<String, Object>> getList(HttpServletRequest request)
	{
		list = (List)SessionUtil.getData(request, Constants.SESSION_KEY_SC);
		if (list == null) 
		{
			list = new ArrayList<HashMap<String, Object>>();
		}
		return list;
	}
	
	protected static void storeList(HttpServletRequest request)
	{
		SessionUtil.setData(request, Constants.SESSION_KEY_SC, list);
	}
	
	/**
	 * Description	: Clear the session object.
	 * @author 		: RKRK
	 * @param request
	 * 2017
	 */
	public static void clear(HttpServletRequest request)
	{
		list.clear();
		storeList(request);
	}
	
	
	/**
	 * Description	: store the SearchModel object in the session pool.
	 * @author 		: RKRK
	 * @param key
	 * @param obj
	 * @param request
	 * 2017
	 */
	@SuppressWarnings("unchecked")
	public static void storeSearchModel(String key, Object obj, HttpServletRequest request)
	{
		list  = getList(request);
		
		List<HashMap<String, Object>> removeList = new ArrayList<HashMap<String, Object>>();
		
		// After checking the existing item in the session, prepare the list to be removed.
		for (@SuppressWarnings("rawtypes") HashMap mapItem: list)
		{
			if (mapItem.containsKey(key)) 
			{
				removeList.add(mapItem);
			}
		}
		
		for (@SuppressWarnings("rawtypes") HashMap mapItem: removeList)
		{
			list.remove(mapItem);
		}
		removeList.clear();
		
		// if list's size is bigger than limit value, we need to remove the first one by FIFO principle.
		if (list.size() >= Constants.SESSION_SC_MODEL_LIMIT)
		{
			list.remove(0);
		}
		
		HashMap<String, Object> newTmp = new HashMap<String, Object>();
		newTmp.put(key, obj);
		
		list.add(newTmp);
		
		storeList(request);
		
		//System.out.println(" ################# SESSION ################## ");
		//System.out.println(toString(request));
		//System.out.println(" ################# End of SESSION ################## ");
	}
	
	/**
	 * Description	: Get a search model from the session pool.
	 * @author 		: RKRK
	 * @param key
	 * @param request
	 * @return
	 * 2017
	 */
	@Deprecated
	public static DefaultSModel getSearchModel(String key, HttpServletRequest request) 
	{
		list = getList(request);
		for (HashMap mapItem: list)
		{
			if (mapItem.containsKey(key))
				return (DefaultSModel)mapItem.get(key);
		}
		return new DefaultSModel();
	}
	
	/**
	 * Description	: Get a search model from the session pool.
	 * 				  If not existing, return the obj which is described in the param.
	 * @author 		: RKRK
	 * @param key
	 * @param obj	: DefaultSModel. Should not be null.
	 * @param request
	 * @return
	 * 2017
	 */
	public static DefaultSModel getSearchModel(String key, Object obj, HttpServletRequest request) 
	{
		list = getList(request);
		for (HashMap mapItem: list)
		{
			if (mapItem.containsKey(key)) 
			{
				DefaultSModel m = (DefaultSModel)mapItem.get(key);
				
				if (obj.getClass().isInstance(m))
					return m;
			}
		}
		return (DefaultSModel)obj;
	}
	
	/**
	 * Description	: toString() function.
	 * @author 		: RKRK
	 * @param request
	 * @return
	 * 2017
	 */
	public static String toString(HttpServletRequest request) 
	{
		list = getList(request);
		
		StringBuilder sb = new StringBuilder();
		if (list.size() > 0) 
		{
			for (HashMap mapItem: list)
			{
				sb.append(mapItem.toString());
				sb.append("\n");
			}
		}
		return sb.toString();
	}
}


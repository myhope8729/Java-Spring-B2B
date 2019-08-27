package com.kpc.eos.model.common;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import com.kpc.eos.core.Constants;
import com.kpc.eos.core.util.SessionUtil;


public class SysMsg
{
	public static final int ERROR 		= 0; 
	public static final int WARNING 	= 1; 
	public static final int INFO 		= 2; 
	public static final int SUCCESS 	= 3; 
	
	//public static  List<SysMsgModel> list = new ArrayList<SysMsgModel>();
	public static String getTypeString(int type)
	{
		switch (type)
		{
		case ERROR:
			return "danger";
		case WARNING:
			return "warning";
		case INFO:
			return "info";
		case SUCCESS:
			return "success";
		}
		return "";
	}
	
	public static void clear(HttpServletRequest request)
	{
		//list.clear();
		SessionUtil.setData(request, Constants.SESSION_KEY_SYS_MSG, null);
	}
	
	public static void addMsg(int type, String text, HttpServletRequest request)
	{
		List<SysMsgModel> list = (ArrayList<SysMsgModel>) SessionUtil.getData(request, Constants.SESSION_KEY_SYS_MSG);
		
		if (list == null) 
		{
			list = new ArrayList<SysMsgModel>();
		}
		
		list.add( new SysMsgModel(type, text));
		
		SessionUtil.setData(request, Constants.SESSION_KEY_SYS_MSG, list);
	}
	
	public static List<SysMsgModel> getMsgList(HttpServletRequest request) 
	{
		return (List<SysMsgModel>) SessionUtil.getData(request, Constants.SESSION_KEY_SYS_MSG);
	}
	
	public static String flashMsg(HttpServletRequest request) 
	{
		String [] htmlList = {"", "", "", ""};
		
		List<SysMsgModel> list = SysMsg.getMsgList(request);
		if (list != null && list.size() > 0) 
		{
			int ind = 0;
			for (SysMsgModel sysMsg : list)
			{
				int type = sysMsg.getType();
				if (htmlList[type].length() > 0)
				{
					htmlList[type] += "<br />";
				}
				htmlList[type] += sysMsg.getText(); 
			}
		}
		
		SysMsg.clear(request);
		list = null;
		
		StringBuilder html = new StringBuilder();
		for (int i=0; i<4; i++) 
		{
			String strTmp = htmlList[i];
			if (strTmp.length() > 0) {
				String cls = SysMsg.getTypeString(i);
				html.append(String.format(Constants.SYS_MSG_ITEM_TPL, cls, strTmp));
			}
		}
		return html.toString();
	}
}


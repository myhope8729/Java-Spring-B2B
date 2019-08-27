package com.kpc.eos.core.xhtml;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;


public class ReqMessage {
	
	/** =================================================== Servics URL ================================================= **/
	/** common value **/
	public static final String EXTENTION_XML =              ".xml";
	public static final String EXTENTION_JSON =             ".json";
	
	public static final String RESULT_SUCCESS = "SUCCESS";
	public static final String RESULT_FAILURE = "FAILURE";
	
	public static final String VALUE_REULT_SUCCESS 	=    "SUCCESS";
	public static final String VALUE_REULT_FAILURE 	=    "FAILURE";
	public static final int VALUE_FLAG_SUCCESS	=	 1;
	public static final int VALUE_FLAG_FAILURE	=	 0;
	
	/** ======================================================== Req Tags  ====================================================== **/
	/** common tag **/
	public static final String TAG_succFlag	=			"succFlag";
	public static final String TAG_eos =                "eos";
	
	public static final String TAG_outResult =          "outResult";
	public static final String TAG_outMsgAlert =        "outMsgAlert";
	public static final String TAG_outMsgCode =         "outMsgCode";
	public static final String TAG_outMsgTitle =        "outMsgTitle";
	public static final String TAG_outMsgDesc =         "outMsgDesc";
	public static final String TAG_page =               "page";
	public static final String TAG_recordsPerPage =     "recordsPerPage";
	public static final String TAG_totalRecords =       "totalRecords";
	public static final String TAG_totalRows =          "totalRows";
	public static final String TAG_totalPages =         "totalPages";
	

	/**============================================================ Making Request Parameters  ==========================================================**/
	/**
	 * @param postParam
	 * @return
	 */
	public static String makePostBody(HashMap<String, String> postParam) {
		if (postParam != null && postParam.size() > 0) {
			StringBuffer postBuilder = new StringBuffer();
			postBuilder = postBuilder.append(getStTag(TAG_eos));
			Iterator<String> paramKeys = postParam.keySet().iterator();
			while (paramKeys.hasNext()) {
				String paramKey = paramKeys.next();
				String paramValue = postParam.get(paramKey);
				if (paramValue != null && !paramValue.equals(""))
					postBuilder = postBuilder.append(getStTag(paramKey)).append(paramValue).append(getEdTag(paramKey));
			}
			postBuilder = postBuilder.append(getEdTag(TAG_eos));
			return postBuilder.toString();
		}else
			return "";
	}
	
	/**
	 * @param postParam
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static String makePostListBody(HashMap<String, Object> postParam) {
		if (postParam != null && postParam.size() > 0) {
			StringBuffer postBuilder = new StringBuffer();
			postBuilder = postBuilder.append(getStTag(TAG_eos));
			Iterator<String> paramKeys = postParam.keySet().iterator();
			while (paramKeys.hasNext()) {
				String paramKey = paramKeys.next();
				Object paramValue = postParam.get(paramKey);
				if (paramValue != null) {
					if(paramValue.getClass().getName().equals(String.class.getName())) {
						if (!paramValue.equals(""))
							postBuilder = postBuilder.append(getStTag(paramKey)).append(paramValue).append(getEdTag(paramKey));
					}else if (paramValue.getClass().getName().equals(ArrayList.class.getName())) {
						ArrayList<HashMap<String, String>> tmpArray = (ArrayList<HashMap<String, String>>) paramValue;
						Iterator<HashMap<String, String>> tmpArrayIt = tmpArray.iterator();
						while (tmpArrayIt.hasNext()) {
							HashMap<String, String> tmpKeyMap = tmpArrayIt.next();
							Iterator<String> tmpKeys = tmpKeyMap.keySet().iterator();
							while (tmpKeys.hasNext()) {
								String tmpKey = tmpKeys.next();
								String tmpValue = tmpKeyMap.get(tmpKey);
								if (tmpValue != null && !tmpValue.equals(""))
									postBuilder.append(getStTag(paramKey)).append(getStTag(tmpKey)).append(tmpValue).append(getEdTag(tmpKey)).append(getEdTag(paramKey));
							}
						}
					}
				}
				
			}
			postBuilder = postBuilder.append(getEdTag(TAG_eos));
			return postBuilder.toString();
		}else
			return "";
	}
	
	/**
	 * @param getParam
	 * @return
	 */
	public static String makeGetBody(HashMap<String, String> getParam) {
		if (getParam != null && getParam.size()>0) {
			
			StringBuffer sb = new StringBuffer();
			Iterator<String> paramKeyIt = getParam.keySet().iterator();
			int cnt = 0;
			while(paramKeyIt.hasNext()) {
				String paramKey = paramKeyIt.next();
				String parmaValue = getParam.get(paramKey);
				if (parmaValue != null && !parmaValue.equals("")) {
					if (cnt ==0 )
						sb = sb.append("?").append(paramKey).append("=").append(parmaValue);
					else 
						sb = sb.append("&").append(paramKey).append("=").append(parmaValue);
					cnt ++;
				}
			}
			return urlEncodeParam(sb.toString());
		}else
			return "";
		
	}
	
	/**
	 * @param getParam
	 * @return
	 */
	public static String makeGetBodyEmpty(HashMap<String, String> getParam) {
		if (getParam != null && getParam.size()>0) {
			
			StringBuffer sb = new StringBuffer();
			Iterator<String> paramKeyIt = getParam.keySet().iterator();
			int cnt = 0;
			while(paramKeyIt.hasNext()) {
				String paramKey = paramKeyIt.next();
				String parmaValue = getParam.get(paramKey);
				if (parmaValue != null) {
					if (cnt ==0 )
						sb = sb.append("?").append(paramKey).append("=").append(parmaValue);
					else 
						sb = sb.append("&").append(paramKey).append("=").append(parmaValue);
					cnt ++;
				}
			}
			return urlEncodeParam(sb.toString());
		}else
			return "";
		
	}
	
	/**
	 * @param url
	 * @return
	 */
	private static String urlEncodeParam(String params)
	{
		String encodeUrl = "";
		try {
			encodeUrl = URLEncoder.encode(params, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		encodeUrl = encodeUrl.replace("%3A", ":");
		encodeUrl = encodeUrl.replace("%2F", "/");
		encodeUrl = encodeUrl.replace("%3F", "?");
		encodeUrl = encodeUrl.replace("%3D", "=");
		encodeUrl = encodeUrl.replace("%26", "&");
		
		return encodeUrl;
	}
	
	private static String getStTag(String tag) {
		return "<" + tag + ">";
	}
	
	private static String getEdTag(String tag) {
		return "</" + tag + ">";
	}
}

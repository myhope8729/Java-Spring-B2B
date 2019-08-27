/**
 * KPC Common Utility
 */
package com.kpc.eos.core.util;

import java.io.Serializable;
import java.util.Locale;

import org.apache.commons.lang.StringUtils;

import com.kpc.eos.core.web.context.ApplicationSetting;

/**
 * 
 * MessageUtil
 * =================
 * Description : Class to get the message of Message.properties
 */
public class MessageUtil implements Serializable {

    private static final long serialVersionUID = 5061851524220260360L;
    
    /**
     * @param key - message key
     * @return - message string of key
     */
    public static String getMessage(String key) {
        return getMessage(key, null, key);
    }
    
    /**
     * Return message string of key within parameters.
     * 
     * @param key - message key
     * @param args - message parameters
     * @return - message string 
     */
    public static String getMessage(String key, Object[] args) {
        return getMessage(key, args, key);
    }
    
    /**
     *  
     * 
     * @param key  
     * @param args  
     * @param defaultMessage - when not exists message string of key, return default message
     * @return  
     */
    public static String getMessage(String key, Object[] args, String defaultMessage) {
        return ApplicationSetting.getMessageSource().getMessage(key, args, defaultMessage, getLocale());
    }

    /**
     * Return Locale Instance that set in Application config file
     * 
     * @return Locale
     */
    public static Locale getLocale() {
//        return LocaleContextHolder.getLocale();
    	String msg_locale = ApplicationSetting.getConfig("message-locale");
    	if(StringUtils.isEmpty(msg_locale)) return java.util.Locale.ENGLISH;
    	else if(msg_locale.equals("zh")) return java.util.Locale.CHINESE;
        else if(msg_locale.equals("ko")) return java.util.Locale.KOREAN;
        else return java.util.Locale.ENGLISH;
    }
    
    /**
     * Return message string of key with parameters separated with comma (,) 
     * 
     * @param key
     * @param param
     * @return message string 
     */
    public static String getMessage(String key, String param) {
    	String[] res = null;
    	if(StringUtils.isNotBlank(param))
    		res= param.split(",");    	
        return getMessage(key, res, key);
    }
      
}

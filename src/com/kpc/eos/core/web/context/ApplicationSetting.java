/**
 * KPC EOS PLATFORM
 */
package com.kpc.eos.core.web.context;

import java.util.List;
import java.util.NoSuchElementException;

import javax.servlet.ServletContext;

import org.apache.commons.configuration.AbstractConfiguration;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.context.MessageSource;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.kpc.eos.core.util.AuthorityUtil;
import com.kpc.eos.core.util.CodeUtil;
import com.kpc.eos.service.system.CodeService;

/**
 * 
 * ApplicationSetting
 * =================
 * Description :  
 * Methods :
 */
public class ApplicationSetting {

	private static final Logger logger = Logger.getLogger(ApplicationSetting.class);

	public static final int OPERATION_MODE_PROD 	= 1;	//Operation
	public static final int OPERATION_MODE_DEV 		= 2;	//Dev Server
	public static final int OPERATION_MODE_WORKER 	= 3;	//Developer

    private static WebApplicationContext webApplicationContext;
	private static MessageSource messageSource;
    private static AbstractConfiguration appConfig;

    public static void initialize(ServletContext sctx) throws Exception {

        webApplicationContext = WebApplicationContextUtils.getWebApplicationContext(sctx);

        //messageSource load
        messageSource = (MessageSource) webApplicationContext.getBean("messageSource");

        //application configuration load
        appConfig = (AbstractConfiguration) webApplicationContext.getBean("applicationConfig");

        //common code load
        CodeService service = (CodeService) webApplicationContext.getBean("codeService");
        CodeUtil.loadCodeMap(service);
        
    }
    
    public static MessageSource getMessageSource() {
    	return messageSource;
    }
    

	public static String getConfig(String key) {
		String value = null;
		try {
			value = appConfig.getString(key);
		} catch (NoSuchElementException ne) {
			value = null;
		}
		return value;
	}

	@SuppressWarnings("unchecked")
	public static List<String> getConfigList(String key) {
		if (appConfig == null) {
			if (logger.isDebugEnabled()) {
				logger.warn("ApplicationConfig was not initialized.");
			}
			return null;
		}

		return appConfig.getList(key);
	}

	public static final int getOperationMode() {
		String opMode = getConfig("operation-mode");
		if (opMode.equals("prod")) {
			return OPERATION_MODE_PROD;
		} else if (opMode.equals("dev")) {
			return OPERATION_MODE_DEV;
		} else if (opMode.equals("worker")) {
			return OPERATION_MODE_WORKER;
		}
		return OPERATION_MODE_PROD;
	}

	public static WebApplicationContext getWebApplicationContext() {
		return webApplicationContext;
	}

	public static void destroyed(ServletContext sctx) {
        appConfig = null;
        messageSource = null;
    }

}

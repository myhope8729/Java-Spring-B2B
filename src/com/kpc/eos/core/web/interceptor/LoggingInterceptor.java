package com.kpc.eos.core.web.interceptor;

import java.lang.reflect.Method;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * 
 * LoggingInterceptor
 * =================
 * Description :  
 * Methods :
 */
public class LoggingInterceptor extends HandlerInterceptorAdapter {

    @SuppressWarnings("unchecked")
	@Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        
        Logger logger = Logger.getLogger(this.getClass());
        
        if (logger.isInfoEnabled()) {
            
            StringBuilder message = new StringBuilder("\n");
            message.append("== REQUEST INFO ============\n");
            message.append("== URI      :   ");
            message.append(request.getRequestURI()).append("\n");
            message.append("== EXE      :   ").append(handler.toString()).append("()\n");

            message.append("== PARAMS   :   ").append("\n");
            Enumeration<String> paramNames = request.getParameterNames();
            while (paramNames.hasMoreElements()) {
                String paramName = paramNames.nextElement();
                String[] values = request.getParameterValues(paramName);
                for (String value : values) {
                	message.append(paramName).append("=").append(value).append("\n");
                }
            }
            /*
            Cookie[] cookies = request.getCookies();
    		if (cookies!= null && cookies.length > 0) {
    			message.append("== COOKIES   :   ").append("\n");
	    		for (Cookie cookie : cookies) {
	    			message.append("name=").append(cookie.getName()).append(", value=").append(cookie.getValue()).append("\n");
	    		}
    		}
    		*/
            message.append("============================\n");
            
            logger.info(message.toString());
        }
        
        //------------- Start the breadcrumb function
        Method m = handler.getClass().getMethod("initCmd");
		if (m != null) {
			m.invoke(handler);
		}
        
        m = handler.getClass().getMethod("initCmd", HttpServletRequest.class, HttpServletResponse.class);
		if (m != null) {
			m.invoke(handler, request, response);
		}
		//------------- End of breadcrumb function
        
        return true;
    }
    
}

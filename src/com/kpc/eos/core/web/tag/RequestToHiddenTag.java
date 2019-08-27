package com.kpc.eos.core.web.tag;

import java.util.Enumeration;

import javax.servlet.ServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

/**
 * 
 * RequestToHiddenTag
 * =================
 * Description :  
 * Methods :
 */
public class RequestToHiddenTag extends BodyTagSupport {

	private static final long serialVersionUID = 4336032404573289299L;
	
	@Override
	public int doEndTag() throws JspException {
		
		try {
			
			Object vobj = this.pageContext.getRequest();
			this.pageContext.getOut().print(this.getOutString(vobj));
			
		} catch (JspException e) {
			throw e;
		} catch (Exception e) {
			throw new JspException("RequestToHidden Tag render fail.",  e);
		} finally {
			release();
		}
		
		return EVAL_PAGE;
	}

	@SuppressWarnings({ "rawtypes" })
	private String getOutString(Object bean) throws Exception {
		
		StringBuffer rv = new StringBuffer();
		
		if (bean instanceof ServletRequest) {
			
			ServletRequest request = (ServletRequest) bean;
			Enumeration paramNames = request.getParameterNames();
			
            while (paramNames.hasMoreElements()) {
            	
                String paramName = (String)paramNames.nextElement();
                if ("cmd".equals(paramName)) continue;
                
                String[] values = request.getParameterValues(paramName);
                for (String value : values) {
                	rv.append("<input type=\"hidden\" name=\"").append(paramName).append("\" value=\"").append(value).append("\" />\n");
                }
                
            }
		}
		
		return rv.toString();
	}

}

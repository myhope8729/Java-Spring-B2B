
package com.kpc.eos.core.web.tag;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Enumeration;

import javax.servlet.ServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

/**
 * PrintBeanTag
 * =================
 * Methods :
 */
public class PrintBeanTag extends BodyTagSupport {

	private static final long serialVersionUID = 4332964156770089607L;
	
	protected Object value;
	protected String outType;
	
	public void setValue(Object value) {
		this.value = value;
	}
	
	public void setOutType(String outType) {
		this.outType = outType;
	}

	@Override
	public int doStartTag() throws JspException {
	
		try {
			if (this.value == null) 
				throw new JspException("property value is required.");
	
			if (this.outType == null) {
				this.outType	= "hidden";
			}
			
			StringBuffer sb = new StringBuffer();

			if (this.value instanceof ServletRequest) {
				sb.append( this.printRequest() );
			} else {
				sb.append( this.printBean("", this.value) );
			}
			this.pageContext.getOut().print( sb.toString() );
			
		} catch (Exception e) {
			throw new JspException("PrintBean Tag render fail.", e);
		} finally {
			this.release();
		}

		return EVAL_PAGE;
	}

	@SuppressWarnings("rawtypes")
	protected String printRequest() {
		
		StringBuffer rv = new StringBuffer();
		
		ServletRequest request = (ServletRequest) this.value;
		Enumeration paramNames = request.getParameterNames();
		
        while (paramNames.hasMoreElements()) {
        	
            String paramName = (String)paramNames.nextElement();
            if ("cmd".equals(paramName)) continue;
            
            String[] values = request.getParameterValues(paramName);
            for (String value : values) {
            	rv.append(printAttribute(paramName, value));
            }
        }
        
        return rv.toString();
	}
	
	@SuppressWarnings("rawtypes")
	protected String printBean(String prefixName, Object bean) throws Exception {
		
		StringBuffer rv = new StringBuffer();

			Class clazz 	= bean.getClass();
	        Field fields[] 	= clazz.getDeclaredFields();
	        AccessibleObject.setAccessible(fields, true);
	        
	        for(Field field : fields) {
	        	if (!accept(field))
	        		continue;
	        	
	        	String name  = field.getName();
	        	Object value = field.get(bean);
	        	if (value == null)
	        		continue;
	        	
	        	Class cls 	 = value.getClass();
	        	if (cls.isPrimitive() 
	        			|| cls.isArray() 
					    || (java.lang.Number.class).isAssignableFrom(cls) 
					    || (java.lang.Boolean.class).isAssignableFrom(cls) 
					    || (java.lang.String.class).isAssignableFrom(cls) 
					    || (java.io.File.class).isAssignableFrom(cls) 
					    || (java.util.Date.class).isAssignableFrom(cls)) 
	        	{
	        		rv.append(printAttribute(prefixName + name, value));
	        	} else {
	        		rv.append(printBean(name+".", value));
	        	}
	        }
		
		return rv.toString();
	}

	private boolean accept(Field field){
        if(field.getName().indexOf('$') != -1
        		|| Modifier.isTransient(field.getModifiers())
        		|| Modifier.isStatic(field.getModifiers()))
            return false;
        
        return true;
    }
	
	private String printAttribute(String name, Object value) {
		
		StringBuffer sb = new StringBuffer();
		
		if ("qs".equals(this.outType)) {
			if(sb.length() > 0)
				sb.append("&");
			sb.append(name).append("=").append(value == null? "" : value);
		} else { 
			sb.append("<INPUT TYPE=\"HIDDEN\" NAME=\"").append(name)
			.append("\" VALUE=\"").append(value == null? "" : value).append("\" />\n");
		}
		
		return sb.toString();
	}

	@Override
	public void release() {
		this.value				= null;
		this.outType			= null;
	}
	
}

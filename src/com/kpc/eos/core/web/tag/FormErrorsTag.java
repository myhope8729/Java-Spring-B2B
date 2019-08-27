/**
 * Filename		: FormErrors.java
 * Description	:
 * 
 * 2017
 */
package com.kpc.eos.core.web.tag;

import java.util.ArrayList;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.kpc.eos.core.validation.FormErrors;

/**
 * Filename		: FormErrors.java
 * Description	:
 * 2017
 * @author		: RKRK
 */
public class FormErrorsTag extends TagSupport
{
	/**
	 * Field		: serialVersionUID
	 * Description	:
	 * 2017
	 */
	private static final long serialVersionUID = 8238766860027955471L;
	
	private static Logger logger = Logger.getLogger(HtmlSelectTag.class);
	
	private FormErrors errors = null;
	
	private String path = null;
	private String type = null;
	private String htmlAttr = null;
	private String cssClass = null;
	
	public int doStartTag() throws JspException {
		try {
			if (errors == null) return SKIP_BODY;
			
			JspWriter out = pageContext.getOut();
			
			if ("errorCls".equals(type))
			{
				out.println(errors.hasFieldErrors(path)? " has-error" : "");
			} 
			else 
			{
				ArrayList<String> list = (ArrayList<String>) errors.getErrorsList(path);
				
				StringBuilder sb = new StringBuilder();
				String format = "<span generated=\"true\" class='qc-error%s'%s>%s</span>";
				for (String ind: list) 
				{
					sb.append(String.format(format, 
						StringUtils.isEmpty(cssClass)? " help-block" : "", 
						StringUtils.isEmpty(htmlAttr)? "" : htmlAttr, 
						ind
					));
				}
				
				out.println(sb.toString());
			}
			
		} catch (Exception e) {
			if (logger.isDebugEnabled())
				logger.debug("", e);
				
			throw new JspException("Html Select Tag render fail.",  e);
		} finally {
			this.release();
		}
        
		return SKIP_BODY;
	}
	
	public void setItems(FormErrors val)
	{
		this.errors = val; 
	}
	public void setPath(String path)
	{
		this.path = path; 
	}
	public void setType(String val)
	{
		this.type = val; 
	}
	
	public void setHtmlAttr(String val)
	{
		this.htmlAttr = val; 
	}
	
	public void setCssClass(String val)
	{
		this.cssClass = val; 
	}
}

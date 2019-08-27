package com.kpc.eos.core.web.tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.lang.StringUtils;

import com.kpc.eos.core.web.context.ApplicationSetting;

/**
 * application config  tag
 * 
 * @author 
 */
public class ConfigTag extends TagSupport {
	
	private static final long serialVersionUID = 4641031157339113196L;

	protected String key;
	protected String var;
	
	@Override
	public int doStartTag() throws JspException {
	
		try {
			JspWriter out = pageContext.getOut();
	
			if (StringUtils.isNotBlank(var)) {
				pageContext.getRequest().setAttribute(var, ApplicationSetting.getConfig(key));
			} else {
				out.print(ApplicationSetting.getConfig(key));
			}
			
		} catch (Exception e) {
			throw new JspException("Config Tag render fail.",  e);
		} finally {
			this.release();
		}
		
		return EVAL_PAGE;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public void setVar(String var) {
		this.var = var;
	}

	@Override
	public void release() {
		this.key = null;
		this.var = null;
	}

}

/**
 * KPC Common Tag
 */
package com.kpc.eos.core.web.tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import com.kpc.eos.core.util.MessageUtil;

/**
 * Tag retrieve info of Message.properties 
 * @date 2017.11.20.
 * @author 
 */
public class MessageTag extends TagSupport {
	
	private static final long serialVersionUID = -3795423002957591255L;
	protected String code;
	protected String param;	
	
	@Override
	public int doStartTag() throws JspException {
	
		try {
			JspWriter out = pageContext.getOut();
			out.print(MessageUtil.getMessage(code,param));
		} catch (Exception e) {
			throw new JspException("Message Tag render fail.",  e);
		} finally {
			this.release();
		}
		
		return EVAL_PAGE;
	}

	public void setCode(String code) {
		this.code = code;
	}


	public void setParam(String param) {
		this.param = param;
	}	
	@Override
	public void release() {
		this.code = null;
		this.param = null;		
	}

}

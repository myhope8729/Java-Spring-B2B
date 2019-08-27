
package com.kpc.eos.core.web.tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import com.kpc.eos.core.util.CodeUtil;

/**
 * CodeTag
 * =================
 * Description : code tag
 * Methods :
 */
public class CodeTag extends TagSupport {

	private static final long serialVersionUID = 7622383411955323647L;

	private String 	code;
	
	public int doStartTag() throws JspException{
		
		try {
			this.pageContext.getOut().print(CodeUtil.getCodeName(code));
		} catch (Exception e) {
			throw new JspException("Code Tag render fail.",  e);
		} finally {
			this.release();
		}
        
		return SKIP_BODY;
	}
	
	public void setCode(String code) {
		this.code = code;
	}
	
}

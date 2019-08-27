
package com.kpc.eos.core.web.tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.log4j.Logger;

import com.kpc.eos.core.util.CodeUtil;

/**
 * CodeListTag
 * =================
 * Description : code list tag
 * Methods :
 */
public class CodeListTag extends TagSupport {
	
	private static final long serialVersionUID = 5021648699725113122L;

	private Logger logger = Logger.getLogger(this.getClass());
	
	private String var;
	
	private String codeGroup;
	
	private String exceptCode;
	
	public int doStartTag() throws JspException{
		
		try {
			pageContext.setAttribute(var, CodeUtil.getCodeList(codeGroup, exceptCode));
		} catch (Exception e) {
			if (logger.isDebugEnabled())
				logger.debug("", e);
				
			throw new JspException("CodeList Tag render fail.",  e);
		} finally {
			this.release();
		}
        
		return SKIP_BODY;
	}

	public void setVar(String var) {
		this.var = var;
	}
	
	public void setCodeGroup(String codeGroup) {
		this.codeGroup = codeGroup;
	}
	
	public void setExceptCode(String exceptCode) {
		this.exceptCode = exceptCode;
	}

}

/**
 * KPC Common Tag
 */
package com.kpc.eos.core.web.tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * image Path tag
 * @date 2015.01.20.
 * @author 
 */
public class ImagesTag extends TagSupport {
	
	private static final long serialVersionUID = -3795423002957591255L;
	protected String rootPath;
	
	@Override
	public int doStartTag() throws JspException {
	
		try {
			JspWriter out = pageContext.getOut();
			out.print(rootPath);
		} catch (Exception e) {
			throw new JspException("Images Tag render fail.",  e);
		} finally {
			this.release();
		}
		
		return EVAL_PAGE;
	}

	public void setRootPath(String rootPath) {
		this.rootPath = rootPath;
	}


	@Override
	public void release() {
		this.rootPath = null;
	}

}

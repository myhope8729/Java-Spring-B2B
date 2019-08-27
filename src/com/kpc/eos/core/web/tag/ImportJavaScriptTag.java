
package com.kpc.eos.core.web.tag;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyTagSupport;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.kpc.eos.core.Constants;

/**
 * ImportJavaScriptTag
 * =================
 * Description : tag to automatically import a javascript
 * Methods :
 */
public class ImportJavaScriptTag extends BodyTagSupport {

	private static final long serialVersionUID = -1975851680906432142L;
	
	private Logger logger = Logger.getLogger(this.getClass());

	@Override
	public int doEndTag() throws JspException {

		String viewName = (String) pageContext.findAttribute(Constants.REQUEST_VIEW_NAME);
		
		String jsPage = null;
		if (StringUtils.isNotBlank(viewName)) {
			jsPage = "/js/" + viewName + ".js";
			if (logger.isDebugEnabled()) {
				logger.debug("jsPage = " + jsPage);
			}

		}
		
		if (jsPage != null) {
			if (logger.isDebugEnabled()) {
				logger.debug("import js page = " + jsPage);
			}

			StringBuffer sb = new StringBuffer();
			try {
				if (StringUtils.contains(viewName, "/ajax/")) {
					sb.append("<script type=\"text/javascript\">");
					sb.append("importJS(\"");
					sb.append(jsPage);
					sb.append("\");</script>");
				} else {
					sb.append("<script type=\"text/javascript\" src=\"");
					sb.append(((HttpServletRequest)pageContext.getRequest()).getContextPath());
					sb.append(jsPage);
					sb.append("\"></script>");
				}
				
				JspWriter jout	= pageContext.getOut();
				jout.print(sb.toString());
			} catch(IOException e) {
				throw new JspException("ImportJavaScript Tag render fail.",  e);
			} finally {
				this.release();
			}
		}
		
		return EVAL_PAGE;
	}
	
}

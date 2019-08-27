package com.kpc.eos.core.web.tag;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.taglibs.standard.lang.support.ExpressionEvaluatorManager;

public class HtmlViewerTag extends TagSupport {

	private static final long serialVersionUID = -7410998344268663393L;
	private String idExp;
	private String valueExp;
	
	public int doStartTag() throws JspException {
		String id = (String)ExpressionEvaluatorManager.evaluate("id", idExp, String.class, this, pageContext);
		String value = (String)ExpressionEvaluatorManager.evaluate("value", valueExp, String.class, this, pageContext);
		
		JspWriter out = pageContext.getOut();

		String txt = value;
        txt = txt.replaceAll("&", "&amp;");
        txt = txt.replaceAll("<", "&lt;");
        txt = txt.replaceAll(">", "&gt;");
        txt = txt.replaceAll("\"", "&quot;");
        txt = txt.replaceAll("'", "&#146;");
		
        String tag = ""
        + "<input type='hidden' id='" + id + "' value='" + txt + "' />"
        + "<iframe id='" + id + "___Frame' src='htmleditor/editor/fckviewer.html?" + id + "' width='100%' height='100%' frameborder='no' scrolling='no' allowtransparency='true'></iframe>";
        
		try {
			out.println(tag);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return SKIP_BODY;
	}
	
	public void setId(String id) {
		this.idExp = id;
	}
	
	public void setValue(String value) {
		this.valueExp = value;
	}
}

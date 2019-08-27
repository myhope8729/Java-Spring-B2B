
package com.kpc.eos.core.web.tag;

import javax.servlet.ServletRequest;
import javax.servlet.jsp.JspException;

/**
 * PreviousTag
 * =================
 * Description :  
 * Methods :
 */
public class PreviousTag extends PrintBeanTag {

	private static final long serialVersionUID = 8823913170841940664L;
	
	/**
	 * form tag name
	 */
	private String formName;
	/**
	 */
	private String url;
	
	@Override
	public int doStartTag() throws JspException {
		
		try {
			if (this.value == null) 
				throw new JspException("property value is required.");
			
			if (this.url == null) 
				throw new JspException("property value is required.");
	
			if (this.formName == null) {
				this.formName	= "previousFrm";
			}
			
			StringBuffer sb = new StringBuffer();
			this.startFormTag(sb);

			if (this.value instanceof ServletRequest) {
				sb.append( this.printRequest() );
			} else {
				sb.append( this.printBean("", this.value) );
			}
			
			this.endFormTag(sb);

			this.pageContext.getOut().print( sb.toString() );
			
		} catch (Exception e) {
			throw new JspException("Previous Tag render fail.", e);
		} finally {
			this.release();
		}

		return EVAL_PAGE;		
	}

	private void startFormTag(StringBuffer sb) {
		sb.append("<FORM ID=\"").append(this.formName).append("\""); 
		sb.append(" NAME=\"").append(this.formName).append("\"");
		sb.append(" METHOD=\"POST\"");
		sb.append(" ACTION=\"").append(this.url).append("\">");
	}
	
	private void endFormTag(StringBuffer sb) {
		sb.append("</FORM>");
	}
	
	public void setFormName(String formName) {
		this.formName = formName;
	}

	public void setUrl(String url) {
		this.url = url + "&reloadPage=Y";
	}

	@Override
	public void release() {
		this.formName = null;
		this.url = null;
		super.release();
	}
	
	
}

package com.kpc.eos.core.web.tag;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.taglibs.standard.lang.support.ExpressionEvaluatorManager;

public class CutstringTag  extends TagSupport {

    private static final long serialVersionUID = -526540746304276453L;
    private String stringExp;
    private String lengthExp;
    private String suffix;

	public CutstringTag() {
        init();
    }

    private void init() {
        suffix = "";
    }

    public void release() {
        super.release();
        init();
    }

    @SuppressWarnings("unchecked")
	public int doStartTag() throws JspException {

    	try {
	    	String string = (String)ExpressionEvaluatorManager.evaluate("string", stringExp, String.class, this, pageContext);
	    	int length = ((Integer)ExpressionEvaluatorManager.evaluate("begin", lengthExp, Integer.class, this, pageContext)).intValue();
	
	    	String substring = "";
	    	if(string != null && length > 0) {
	    		
	    		if(length < string.length())
	    			substring = string.substring(0, length);
	    		else
	    			substring = string;
	    		
	    		if(substring.length() < string.length()) {
	    			substring += suffix;
	    		}
	    	}
	    	
	    	pageContext.getOut().print(substring);
		} catch (IOException e) {
			e.printStackTrace();
		}
    	
        return SKIP_BODY;
    }

    public void setString(String string) {
    	stringExp = string;
    }
    
    public void setLength(String length) {
    	lengthExp = length;
    }
    
    public void setSuffix(String suffix) {
    	this.suffix = suffix;
    }
}

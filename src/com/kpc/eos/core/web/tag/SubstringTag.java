package com.kpc.eos.core.web.tag;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.taglibs.standard.lang.support.ExpressionEvaluatorManager;

public class SubstringTag  extends TagSupport {

    private static final long serialVersionUID = -751248248415663036L;
    private String stringExp;
	private String beginExp;
    private String endExp;

	public SubstringTag() {
        init();
    }

    private void init() {
    }

    public void release() {
        super.release();
        init();
    }

    @SuppressWarnings("unchecked")
	public int doStartTag() throws JspException {

    	try {
	    	String string = (String)ExpressionEvaluatorManager.evaluate("string", stringExp, String.class, this, pageContext);
	    	int begin = ((Integer)ExpressionEvaluatorManager.evaluate("begin", beginExp, Integer.class, this, pageContext)).intValue();
	    	int end = -1;
	    	if(endExp != null) {
	    		end = ((Integer)ExpressionEvaluatorManager.evaluate("end", endExp, Integer.class, this, pageContext)).intValue();
	    	}
	
	    	String substring = "";
	    	if(string != null) {
		    	if(end < 0) {
		    		substring = string.substring(begin);
		    	} else {
		    		substring = string.substring(begin, end);
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

    public void setBegin(String begin) {
        beginExp = begin;
    }
    
    public void setEnd(String end) {
    	endExp = end;
    }
}

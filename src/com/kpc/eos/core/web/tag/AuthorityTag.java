
package com.kpc.eos.core.web.tag;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.kpc.eos.core.Constants;
import com.kpc.eos.core.util.MenuUtil;
import com.kpc.eos.core.util.SessionUtil;
import com.kpc.eos.model.dataMng.UserModel;

/**
 * AuthorityTag
 * =================
 * Description :  
 * Methods :
 */
public class AuthorityTag extends TagSupport {

	private static final long serialVersionUID = 6694726601609184719L;

	private Logger logger = Logger.getLogger(this.getClass());

	private String actionId;
	
	private String var;
	
	public void setActionId(String actionId) {
		this.actionId = actionId;
	}

	public void setVar(String var) {
		this.var = var;
	}
	
	@Override
	public int doStartTag() throws JspException {

		try {
			HttpServletRequest request = (HttpServletRequest) this.pageContext.getRequest();
			Object obj = SessionUtil.getUser(request, (String) request.getAttribute(Constants.REQUEST_KEY_SYSTEMNAME));
			if (obj == null) 
				return SKIP_BODY;
			
			UserModel user = (UserModel) obj;

			boolean b = MenuUtil.hasAuthorityOfAction(user, actionId);
			if (StringUtils.isNotBlank(var)) {
				pageContext.getRequest().setAttribute(var, b);
			}
			if (b)
				return EVAL_BODY_INCLUDE;
			
		} catch (Exception e) {
			if (logger.isDebugEnabled())
				logger.debug("", e);
				
			throw new JspException("Menu Tag render fail.",  e);
		} finally {
			this.release();
		}
		
		return SKIP_BODY;
	}

	@Override
	public void release() {
		this.actionId = null;
		this.var = null;
		super.release();
	}
	
}

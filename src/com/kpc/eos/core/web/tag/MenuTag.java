
package com.kpc.eos.core.web.tag;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.kpc.eos.core.Constants;
import com.kpc.eos.core.util.MenuUtil;
import com.kpc.eos.core.util.SessionUtil;
import com.kpc.eos.model.common.DefaultSModel;
import com.kpc.eos.model.dataMng.UserModel;
import com.kpc.eos.model.system.MenuModel;

/**
 * MenuTag
 * =================
 * Description : menu tag
 * Methods :
 */
public class MenuTag extends TagSupport {

	private static final long serialVersionUID = -2348174922612584034L;

	private Logger logger = Logger.getLogger(this.getClass());
	
	private String var;
	
	private String menuId;
	
	private String menuType;

	@SuppressWarnings("unchecked")
	@Override
	public int doStartTag() throws JspException {
		
		try {
			HttpServletRequest request = (HttpServletRequest) this.pageContext.getRequest();
			
			String hostUserId = (String)request.getParameter("hostUserId");
			if (StringUtils.isNotEmpty(getMenuType()) && StringUtils.equals(getMenuType(), Constants.CONST_MENU_TYPE_USER)){
				DefaultSModel sc = new DefaultSModel();
				sc.setUserId(hostUserId);
				sc.setState(Constants.CONST_STATE_Y);
				List menus = MenuUtil.findUserMenuList(sc);
				request.setAttribute(var, menus);
			}
			Object obj = SessionUtil.getUser(request, (String) request.getAttribute(Constants.REQUEST_KEY_SYSTEMNAME));
			if (obj == null) 
				return SKIP_BODY;
			
			UserModel user = (UserModel) obj;
				
			if (StringUtils.isBlank(getMenuType()) && StringUtils.isBlank(getMenuId())) {
				List menus = MenuUtil.findAccessibleMenuList(user);
				request.setAttribute(var, menus);
			} else {
				//MenuModel menu = MenuUtil.getAccessibleMenu(user, getMenuId());
				//request.setAttribute(var, menu);
			}
			
		} catch (Exception e) {
			if (logger.isDebugEnabled())
				logger.debug("", e);
				
			throw new JspException("Menu Tag render fail.",  e);
		} finally {
			this.release();
		}
		
		return SKIP_BODY;
	}

	public String getVar() {
		return var;
	}

	public void setVar(String var) {
		this.var = var;
	}

	public String getMenuId() {
		return menuId;
	}

	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}
	
	public String getMenuType() {
		return menuType;
	}

	public void setMenuType(String menuType) {
		this.menuType = menuType;
	}
	
}

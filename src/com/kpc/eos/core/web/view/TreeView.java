
package com.kpc.eos.core.web.view;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.view.AbstractView;

/**
 * TreeView
 * =================
 * Description :  
 * Methods :
 */
public class TreeView extends AbstractView {

	Logger logger = Logger.getLogger(getClass());
	
	@Override
	protected void renderMergedOutputModel(Map model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		if (!model.isEmpty() && model.containsKey("tree")) {
			
			JSONArray jsonArray = JSONArray.fromObject(model.get("tree"));
			response.setCharacterEncoding("UTF-8");
			response.getWriter().println(jsonArray);
	        response.getWriter().flush();
	        
	        if (logger.isDebugEnabled()) {logger.debug(jsonArray);}
		}

	}

}

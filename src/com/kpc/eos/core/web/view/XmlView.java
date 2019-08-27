
package com.kpc.eos.core.web.view;

import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.view.AbstractView;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

/**
 * XmlView
 * =================
 * Description :  
 * Methods :
 */
public class XmlView extends AbstractView {

	@SuppressWarnings("unchecked")
	@Override
	protected void renderMergedOutputModel(Map model, HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		StringBuffer xmlStream = new StringBuffer("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
		
		if (!model.isEmpty()) {
			Iterator<String> keys = model.keySet().iterator();
			XStream xstream = new XStream(new DomDriver());
	        while (keys.hasNext()) {
	        	String key = keys.next();
	            Object value = model.get(key);
	            if (value != null) {
	        		xstream.alias(key, value.getClass());
	        		xmlStream.append(xstream.toXML(value));
	            }
	        }
		}
		
        response.setContentType(getContentType());
        response.setHeader("Cache-Control", "no-cache");
		response.getWriter().write(xmlStream.toString());
		if (logger.isDebugEnabled()) {
			logger.debug("xmlStream=" + xmlStream.toString());
		}
        response.getWriter().flush();
	}

}

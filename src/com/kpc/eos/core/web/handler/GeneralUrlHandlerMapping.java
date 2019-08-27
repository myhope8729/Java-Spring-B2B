package com.kpc.eos.core.web.handler;

import java.util.Iterator;
import java.util.Map;

import org.springframework.beans.BeansException;
import org.springframework.web.servlet.handler.SimpleUrlHandlerMapping;

import com.kpc.eos.core.web.context.ApplicationSetting;

public class GeneralUrlHandlerMapping extends SimpleUrlHandlerMapping {

	private String urlPrefix;
	
	public void setUrlPrefix(String urlPrefix) {
		this.urlPrefix = urlPrefix;
	}

	@Override
	protected void registerHandlers(Map urlMap) throws BeansException {
		boolean enable = Boolean.parseBoolean(ApplicationSetting.getConfig("domain.prefix"));
		if (enable) {
			if (urlMap.isEmpty()) {
				logger.warn("Neither 'urlMap' nor 'mappings' set on SimpleUrlHandlerMapping");
			}
			else {
				Iterator it = urlMap.keySet().iterator();
				while (it.hasNext()) {
					String url = (String) it.next();
					Object handler = urlMap.get(url);
					// Prepend with slash if not already present.
					if (!url.startsWith("/")) {
						url = urlPrefix + "/" + url;
					}
					registerHandler(url, handler);
				}
			}
		} else {
			super.registerHandlers(urlMap);
		}
	}

}

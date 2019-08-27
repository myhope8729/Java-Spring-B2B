/**
 * KPC EOS PLATFORM
 */
package com.kpc.eos.core.web.context;

import javax.servlet.ServletContextEvent;

import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.util.Log4jWebConfigurer;

public class GeneralContextLoaderListener extends ContextLoaderListener {

    @Override
    public void contextInitialized(ServletContextEvent event) {
        try {
            super.contextInitialized(event);
            
            //system common Env load
            ApplicationSetting.initialize(event.getServletContext());

            //log4j load
            Log4jWebConfigurer.initLogging(event.getServletContext());
            
        } catch(Throwable t) {
        	t.printStackTrace();
            throw new RuntimeException(t);
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent event) {
        try {
        	ApplicationSetting.destroyed(event.getServletContext());
            Log4jWebConfigurer.shutdownLogging(event.getServletContext());
            super.contextDestroyed(event);
        } catch(Throwable t) {
        	t.printStackTrace();
            throw new RuntimeException(t);
        }
    }
}

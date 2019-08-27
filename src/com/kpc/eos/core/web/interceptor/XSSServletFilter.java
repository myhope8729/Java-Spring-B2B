package com.kpc.eos.core.web.interceptor;


import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;


public class XSSServletFilter implements Filter {

	public XSSServletFilter() {
		config = null;
	}

	public void init(FilterConfig filterconfig) throws ServletException {
		config = filterconfig;
		no_init = false;
	}

	public void destroy() {
		config = null;
	}

	public FilterConfig getFilterConfig() {
		return config;
	}

	public void setFilterConfig(FilterConfig filterconfig) {
		if (no_init) {
			no_init = false;
			config = filterconfig;
		}
	}

	public void doFilter(ServletRequest servletrequest, ServletResponse servletresponse, FilterChain filterchain) throws IOException,
			ServletException {
		filterchain.doFilter(new XssRequestWrapper((HttpServletRequest) servletrequest), servletresponse);
	}

	private FilterConfig config;
	private static boolean no_init = true;

}

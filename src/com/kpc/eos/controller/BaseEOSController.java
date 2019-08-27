
package com.kpc.eos.controller;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kpc.eos.core.controller.BaseController;
import com.kpc.eos.model.common.BreadcrumbModel;

public class BaseEOSController extends BaseController {

	private final String SYSTEMNAME_BACKOFFICE	= "bo";
	
	@Override
	public String getSystemName() {
		return SYSTEMNAME_BACKOFFICE;
	}
	
	public BaseEOSController() {
		super();
		this.breads =  new ArrayList<BreadcrumbModel>();
	}

	@Override
	public void initCmd() {
		breads.clear();
	}

	@Override
	public void initCmd(HttpServletRequest request, HttpServletResponse response)
			throws Exception
	{
		breads.clear();
	}
	
}

package com.kpc.eos.controller.common;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kpc.eos.core.controller.BaseController;
import com.kpc.eos.model.dataMng.UserModel;


public class CommonController extends BaseController {

	private final String SYSTEMNAME_COMMON	= "EOS_COMMON";

	@Override
	public String getSystemName() {
		return SYSTEMNAME_COMMON;
	}

	@Override
	public UserModel getLoginUser(HttpServletRequest request) {
		return null;
	}

	@Override
	public void initCmd() {
		
		
	}

	@Override
	public void initCmd(HttpServletRequest request, HttpServletResponse response)
			throws Exception
	{
	}
	
}

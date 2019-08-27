package com.kpc.eos.controller.common;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.servlet.ModelAndView;

import com.kpc.eos.controller.BaseEOSController;
import com.kpc.eos.model.common.AddressModel;
import com.kpc.eos.service.common.AddressService;

public class AddressController extends BaseEOSController {

	private AddressService addrService;
	
	public void setAddrService(AddressService addrService) {
		this.addrService = addrService;
	}
	
	public ModelAndView getChidrenAjax(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		ModelAndView mv = new ModelAndView("jsonView");
		
		String upperId = request.getParameter("upperId");
		String level = request.getParameter("level");
		
		List<AddressModel> areaList;
		
		if (level.equals("2")){
			List<AddressModel> cityList = addrService.findChildLocationList(upperId);
			mv.addObject("cityList", cityList);
			AddressModel firstCity = cityList.get(0);
			areaList = addrService.findChildLocationList(firstCity.getAddressId());
			mv.addObject("areaList", areaList);
			mv.addObject("isProvince", true);
		}else{
			areaList = addrService.findChildLocationList(upperId);
			mv.addObject("areaList", areaList);
			mv.addObject("isProvince", false);
		}
		return mv;
	}
	
	
	@SuppressWarnings("rawtypes")
	public ModelAndView getChidren2Ajax(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		ModelAndView mv = new ModelAndView("jsonView");
		
		String upperId = request.getParameter("upperId");
		String inputName = request.getParameter("inputName");
		
		List<AddressModel> childrenList;
		
		if (StringUtils.isNotEmpty(upperId)) {
			childrenList = addrService.findChildLocationList(upperId);
			mv.addObject("childrenList", childrenList);
			
			if ("provId".equals(inputName) && childrenList.size() > 0) {
				mv.addObject("firstchildrenList", addrService.findChildLocationList(childrenList.get(0).getAddressId()));
			} else {
				mv.addObject("firstchildrenList", new ArrayList());
			}
		}
		return mv;
	}

}

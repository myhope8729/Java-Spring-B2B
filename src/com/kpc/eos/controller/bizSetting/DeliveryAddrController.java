
package com.kpc.eos.controller.bizSetting;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.servlet.ModelAndView;

import com.kpc.eos.controller.BaseEOSController;
import com.kpc.eos.controller.utility.SearchModelUtil;
import com.kpc.eos.core.util.MessageUtil;
import com.kpc.eos.core.util.SessionUtil;
import com.kpc.eos.model.bizSetting.DeliveryAddrModel;
import com.kpc.eos.model.common.AddressModel;
import com.kpc.eos.model.common.BreadcrumbModel;
import com.kpc.eos.model.common.DefaultSModel;
import com.kpc.eos.model.common.SysMsg;
import com.kpc.eos.service.bizSetting.DeliveryAddrService;
import com.kpc.eos.service.common.AddressService;

public class DeliveryAddrController extends BaseEOSController {

	private DeliveryAddrService deliveryAddrService;
	private AddressService addrService;
	
	public void setDeliveryAddrService(DeliveryAddrService deliveryAddrService) {
		this.deliveryAddrService = deliveryAddrService;
	}
	
	public void setAddrService(AddressService addrService) {
		this.addrService = addrService;
	}
	
	public DeliveryAddrController() {
		super();
		controllerId = "DeliveryAddr";
	}
	
	/**
	 * command initialization function.
	 * When getting a request, this function will be called before running a cmd's method.
	 * Define Breadcrumb model.
	 */
	public void initCmd()
	{
		super.initCmd();
		breads.add(new BreadcrumbModel("业务设置", "", false));
		breads.add(new BreadcrumbModel("收货地址 ", getCmdUrl("deliveryAddrList"), true));
	}
	
	/****************************************************************************************************************************
	* Function Name:  			deliveryAddrList
	* Function Description		Call the view for delivery address list according to user id
	*****************************************************************************************************************************/
	public ModelAndView deliveryAddrList(HttpServletRequest request, HttpServletResponse response)
	{
		initCmd();
		
		DefaultSModel sc = new DefaultSModel();
		
		String key = "Delivery_deliveryAddrList";
		request.setAttribute(SC_ID_SESSION, key);
		
		sc  = (DefaultSModel)SearchModelUtil.getSearchModel(key, sc, request);
		
		ModelAndView mv = new ModelAndView("bizSetting/deliveryAddrList", "sc", sc);
		
		mv.addObject("page", sc.getPage());
		
		return mv;
	}
	
	/****************************************************************************************************************************
	* Function Name:  			deliveryAddrGridAjax
	* Function Description		Retrieve the customer type list according to customer type id
	*****************************************************************************************************************************/
	public ModelAndView deliveryAddrGridAjax(HttpServletRequest request, HttpServletResponse response, DefaultSModel sc) throws Exception {
		ModelAndView mv = new ModelAndView("jsonView");

		request.setAttribute(SC_ID_SESSION, "Delivery_deliveryAddrList");
		
		String userId = SessionUtil.getUserId(request, getSystemName());
		sc.setUserId(userId);
		
		Integer totalCount = deliveryAddrService.getTotalCountDeliveryAddrList(sc);
		sc.getPage().setRecords(totalCount);
		
		List<DeliveryAddrModel> list = deliveryAddrService.getDeliveryAddrList(sc);
		
		mv.addObject("rows", list);
		mv.addObject("sc", sc);
		mv.addObject("page", sc.getPage());
		
		return mv;
	}
	
	/****************************************************************************************************************************
	* Function Name:  			deliveryAddrForm
	* Function Description		Get the delivery address information according to customer id
	*****************************************************************************************************************************/
	public ModelAndView deliveryAddrForm(HttpServletRequest request, HttpServletResponse response, DefaultSModel sc) throws Exception {
		initCmd();
		String userId = SessionUtil.getUserId(request, getSystemName());
		String addrId = request.getParameter("addrId");
		
		DeliveryAddrModel deliveryAddr = null;
		if (StringUtils.isNotEmpty(addrId))
		{
			breads.add(new BreadcrumbModel("修改收货地址", "", false));
			deliveryAddr = deliveryAddrService.getDeliveryAddr(addrId);
			if (StringUtils.isEmpty(userId) || !userId.equals(deliveryAddr.getUserId()))
			{
				return redirect("DeliveryAddr", "deliveryAddrList");
			}
		}
		else
		{
			breads.add(new BreadcrumbModel("新增收货地址", "", false));
		}
		
		ModelAndView mv = new ModelAndView("bizSetting/deliveryAddrForm");
		
		List<AddressModel> provList = addrService.findProvinceList();
		mv.addObject("provList", provList);
		
		List<AddressModel> areaList;
		AddressModel firstProv = new AddressModel();
		if (deliveryAddr != null)
		{
			firstProv.setAddressId(deliveryAddr.getProvinceId());
			firstProv.setAddressLevel(deliveryAddr.getProvinceLevel());
		}
		else
		{
			firstProv = provList.get(0);
		}
		
		if (firstProv.getAddressLevel().equals("2")){
			List<AddressModel> cityList = addrService.findChildLocationList(firstProv.getAddressId());
			mv.addObject("cityList", cityList);
			AddressModel firstCity = cityList.get(0);
			if (deliveryAddr != null)
			{
				firstCity.setAddressId(deliveryAddr.getCityId());
			}
			areaList = addrService.findChildLocationList(firstCity.getAddressId());
			mv.addObject("areaList", areaList);
			mv.addObject("isProvince", true);
		}else{
			areaList = addrService.findChildLocationList(firstProv.getAddressId());
			mv.addObject("areaList", areaList);
			mv.addObject("isProvince", false);
		}
		
		mv.addObject("deliveryAddr", deliveryAddr);
		mv.addObject("sc", sc);
		
		return mv;
	}
	
	/****************************************************************************************************************************
	* Function Name:  			saveDeliveryAddr
	* Function Description		Save the delivery address information.
	*****************************************************************************************************************************/
	public ModelAndView saveDeliveryAddr(HttpServletRequest request, HttpServletResponse response, DeliveryAddrModel deliveryAddr) throws Exception {
		String userId = SessionUtil.getUserId(request, getSystemName());
		deliveryAddr.setUserId(userId);
		
		deliveryAddrService.saveDeliveryAddr(deliveryAddr);
		
		SysMsg.addMsg(SysMsg.SUCCESS, MessageUtil.getMessage("system.alert.save"), request);
		
		return redirect("DeliveryAddr", "deliveryAddrList");
	}
}

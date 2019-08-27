package com.kpc.eos.controller.common;

import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.servlet.ModelAndView;

import com.kpc.eos.controller.BaseEOSController;
import com.kpc.eos.core.BizSetting;
import com.kpc.eos.core.Constants;
import com.kpc.eos.core.util.MessageUtil;
import com.kpc.eos.core.util.SessionUtil;
import com.kpc.eos.core.validation.FormErrors;
import com.kpc.eos.model.bizSetting.BizSettingModel;
import com.kpc.eos.model.common.AddressModel;
import com.kpc.eos.model.common.SysMsg;
import com.kpc.eos.model.dataMng.UserModel;
import com.kpc.eos.service.bizSetting.BizSettingService;
import com.kpc.eos.service.common.AddressService;
import com.kpc.eos.service.dataMng.UserService;

public class LoginController extends BaseEOSController 
{
	
	private UserService userService;
	private BizSettingService 	bizSettingService;
	
	private AddressService addrService;
	
	public void setAddrService(AddressService addrService) 
	{
		this.addrService = addrService;
	}
	
	public void setUserService(UserService userService) 
	{
		this.userService = userService;
	}
	
	public void setBizSettingService(BizSettingService bizSettingService) 
	{
		this.bizSettingService = bizSettingService;
	}
	
	/****************************************************************************************************************************
	* Function Name:  			loginForm
	* Function Description		Call the view of login form.
	*****************************************************************************************************************************/
	public ModelAndView loginForm(HttpServletRequest request, HttpServletResponse response) throws Exception 
	{
		
		ModelAndView mv = new ModelAndView();
		
		HttpSession session = request.getSession(false);
		if (session == null || SessionUtil.getUser(request, getSystemName()) == null) 
		{
			mv.setViewName("common/login");
		}
		else 
		{
			if(isMobileClient()) mv.setViewName("redirect:UserPage.do?cmd=mainPage&hostUserId=" + SessionUtil.getUserId(request, getSystemName()));
			else mv.setViewName("redirect:Main.do?cmd=main");
		}
			
		//System.out.println( MessageUtil.getMessage("system.common.valid.error.required"));
		
		String empId = (String)SessionUtil.getData(request, "empNo");
		if (StringUtils.isNotEmpty(empId))
		{
			mv.addObject("empId", empId);
		}
		SessionUtil.removeData(request, "empNo");
		mv.addObject(Constants.REQUEST_KEY_RETURN_URL, request.getParameter(Constants.REQUEST_KEY_RETURN_URL));
		return mv;
	}
	
	/****************************************************************************************************************************
	* Function Name:  			login
	* Function Description		Login process
	*****************************************************************************************************************************/
	public ModelAndView login(HttpServletRequest request, HttpServletResponse response, UserModel inUser) throws Exception 
	{
		
		String lang = request.getParameter("lang");
		SessionUtil.setData(request, "lang", lang);
		
		ModelAndView mv = new ModelAndView();
		
		String rememberme = request.getParameter("rememberme");
		
		if (!StringUtils.equals(Constants.LOGIN_REMEMBERME_ON, rememberme))
		{
			response.addCookie(new Cookie("userid", ""));
			response.addCookie(new Cookie("userpwd", ""));
			response.addCookie(new Cookie("rememberme", ""));
		}
		
		UserModel user = userService.login(inUser);
		
		if (user == null) 
		{
			SysMsg.addMsg(SysMsg.ERROR, MessageUtil.getMessage("login.invalidPwd"), request);
			SessionUtil.setData(request, "empNo", inUser.getEmpId());
			mv.setViewName("redirect:Login.do?cmd=loginForm");
			return mv;
		}
		
		user.setAddress(addrService.getFullAddressByLocationId(user.getLocationId()));
		
		SessionUtil.setUser(request, user, getSystemName());
		
		if (StringUtils.equals(Constants.LOGIN_REMEMBERME_ON, rememberme))
		{
			response.addCookie(new Cookie("userid", user.getEmpNo()));
			response.addCookie(new Cookie("userpwd", user.getPwd()));
			response.addCookie(new Cookie("rememberme", "1"));
		}
		
		String returnUrl = null;
		returnUrl = (String)SessionUtil.getData(request, Constants.REQUEST_KEY_RETURN_URL);
		
		if (returnUrl == null) 
		{
			returnUrl = request.getParameter(Constants.REQUEST_KEY_RETURN_URL);
		} 
		else 
		{
			SessionUtil.setData(request, Constants.REQUEST_KEY_RETURN_URL, null);
		}
		
		if(isMobileClient())
		{
			if ( ! StringUtils.isBlank(returnUrl) )
			{
				mv.setViewName("redirect:" + returnUrl);
			}
			else
			{
				if (Constants.UK_ADMIN.equals(user.getUserKind()))
				{
					mv.setViewName("redirect:Item.do?cmd=itemList");
				}
				else
				{
					mv.setViewName("redirect:UserPage.do?cmd=myMain");
				}
			}
		}
		else
		{
			mv.setViewName("redirect:" + (StringUtils.isBlank(returnUrl) ? "Main.do?cmd=main" : returnUrl));
		}
		
		return mv;
	}
	
	/****************************************************************************************************************************
	* Function Name:  			logout
	* Function Description		Logout process
	*****************************************************************************************************************************/
	public ModelAndView logout(HttpServletRequest request, HttpServletResponse response) throws Exception 
	{
		
		ModelAndView mv = new ModelAndView("redirect:Login.do?cmd=loginForm");
		
		SessionUtil.clearUserData( request, getSystemName() );
		
		return mv;
	}
	
	/****************************************************************************************************************************
	* Function Name:  			register
	* Function Description		User registrator
	*****************************************************************************************************************************/
	public ModelAndView register(HttpServletRequest request, HttpServletResponse response) throws Exception 
	{
		ModelAndView mv = new ModelAndView("common/register");
		
		List<AddressModel> provList = addrService.findProvinceList();
		
		mv.addObject("provList", provList);
		
		List<AddressModel> areaList;
		
		AddressModel firstProv = provList.get(0);
		if (firstProv.getAddressLevel().equals("2"))
		{
			List<AddressModel> cityList = addrService.findChildLocationList(firstProv.getAddressId());
			mv.addObject("cityList", cityList);
			AddressModel firstCity = cityList.get(0);
			areaList = addrService.findChildLocationList(firstCity.getAddressId());
			mv.addObject("areaList", areaList);
			mv.addObject("isProvince", true);
		}
		else
		{
			areaList = addrService.findChildLocationList(firstProv.getAddressId());
			mv.addObject("areaList", areaList);
			mv.addObject("isProvince", false);
		}
		
		return mv;
	}
	
	/****************************************************************************************************************************
	* Function Name:  			saveUser
	* Function Description		Save User Data
	*****************************************************************************************************************************/
	public ModelAndView saveUser(HttpServletRequest request, HttpServletResponse response, UserModel inUser) throws Exception 
	{
		if (!StringUtils.isEmpty(inUser.getUserNo()))
		{
			UserModel already = userService.getUser("USER_NO", inUser.getUserNo());
			String hostId = request.getParameter("hostUserId");
			String qrCodeMark = null;
			boolean failYn = (already != null);
			
			if (StringUtils.isNotEmpty(hostId))
			{
				qrCodeMark = ((BizSettingModel)bizSettingService.getBizSettingBySysType(hostId, BizSetting.USERPAGE_QRCODE)).getSysValueName();
				if (BizSetting.USERPAGE_QRCODE_R.equals(qrCodeMark))
				{
					Integer tmp = userService.checkClerkNo(inUser.getClerkNo(), hostId);
					if (tmp == null) failYn = true;
				}
			}
			
			if (failYn == false)
			{
				userService.saveUser(inUser, hostId);
			}
			else
			{
				ModelAndView mv = null;
				
				if (StringUtils.isNotEmpty(hostId))
				{
					UserModel hostUser = userService.getUserById(hostId);
					mv = new ModelAndView("userPage/register");
					mv.addObject("hostUser", hostUser);
					qrCodeMark = ((BizSettingModel)bizSettingService.getBizSettingBySysType(hostId, BizSetting.USERPAGE_QRCODE)).getSysValueName();
					mv.addObject("qrCodeMark", qrCodeMark);
				}
				else
				{
					mv = new ModelAndView("common/register");
				}
				mv.addObject("user", inUser);
				
				AddressModel userLocations = addrService.getAddressByLocationId(inUser.getLocationId());
				
				List<AddressModel> provList = addrService.findProvinceList();
				mv.addObject("provList", provList);
				
				if (StringUtils.isNotEmpty(userLocations.getLevel2Id()))
				{
					List<AddressModel> cityList = addrService.findChildLocationList(userLocations.getLevel2Id());
					mv.addObject("cityList", cityList);
					if (StringUtils.isNotEmpty(userLocations.getLevel1Id()))
					{
						List<AddressModel> areaList = addrService.findChildLocationList(userLocations.getLevel1Id());
						mv.addObject("areaList", areaList);
					}
					mv.addObject("isProvince", true);
				}
				else
				{
					if (StringUtils.isNotEmpty(userLocations.getLevel1Id()))
					{
						List<AddressModel> areaList = addrService.findChildLocationList(userLocations.getLevel1Id());
						mv.addObject("areaList", areaList);
					}
					else
					{
						mv.addObject("areaList", null);
					}
					mv.addObject("isProvince", false);
				}
				
				mv.addObject("userLocations", userLocations);
				
				formErrors = new FormErrors(inUser, "target");
				if (already != null)
				{
					formErrors.rejectValue("userNo", "user.empno.duplicated", new Object[]{inUser.getUserNo()}, null);
				}
				else
				{
					formErrors.rejectValue("clerkNo", "userpage.user.invalid.clerkno", new Object[]{inUser.getClerkNo()}, null);
				}
				
				return mv;
			}
		}
		
		if(isMobileClient())
		{
			inUser.setAddress(addrService.getFullAddressByLocationId(inUser.getLocationId()));
			SessionUtil.setUser(request, inUser, getSystemName());
			
			return redirect("UserPage", "myMain");
		}
		else
		{
			SysMsg.addMsg(SysMsg.SUCCESS, MessageUtil.getMessage("system.alert.save"), request);
			return redirect("Login", "loginForm");
		}
	}
}

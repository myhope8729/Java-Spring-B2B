
package com.kpc.eos.model.dataMng;

import java.util.HashMap;
import java.util.Map;

import lombok.Data;

import org.apache.commons.lang.StringUtils;

import com.kpc.eos.core.Constants;
import com.kpc.eos.core.model.CommonModel;
import com.kpc.eos.core.validation.FormErrors;
import com.kpc.eos.core.validation.FormValidationUtils;

public @Data class UserModel extends CommonModel{

	private static final long serialVersionUID = -238240335038594350L;
	
	private String userId;
	private String empId;
	
	private String userNo;
	private String empNo;
	
	private String userName;
	private String empName;
	
	private String empRole;
	
	private String pwd;
	private String oldPwd;
	private String confirmPwd;
	
	private String locationId;
	
	private String userAddr;
		
	private String userKind;
	
	private String userMColor;
	
	private String userBKColor;
	
	private String contactName;
	
	private String userYn;
		
	private String deptId;
	
	
	private String chelp;
	
	private String address;
	
	private String telNo;
	private String mobileNo;
	private String qqNo;
	private String faxNo;
	private String emailAddr;
	private String cardNo;
	private String type1;
	private String url;
	private String topic1;
	private String topic2;
	private String webName;
	private String logoImgPath;
	private String mainColor;
	private String bkColor;
	private String note;
	private String saleOnlineMark;
	
	private String clerkNo;
	
	private String oldUserId;
	
	private String firstMark;
	
	//added by rmh
	private boolean isMobile;
	
	DepartmentModel dept;
	
	public UserModel() {
		
	}
	
	public boolean isHostYn()
	{
		return Constants.CONST_Y.equals(getUserYn());
	}
	
	private String empChelp;
	
	public String getLogoImgPath()
	{
		if (logoImgPath != null){
			return logoImgPath;
		}else{
			return "";
		}
	}
	
	public boolean isBuyer()
	{
		return Constants.UK_BUYER.equals(userKind);
	}
	
	
	@SuppressWarnings({ "static-access" })
	public FormErrors validate()
	{
		FormErrors errors = new FormErrors(this, "target");
		FormValidationUtils fv = new FormValidationUtils(errors);
		
		fv.rejectIfEmptyOrWhitespace(new String[]{"empNo", "empName", "deptId", "state"}, "system.common.valid.error.required", "Field");
		
		// is New?
		if (StringUtils.isEmpty(empId))
		{
			fv.rejectIfEmpty(errors, "pwd", "system.common.valid.error.required", new String[]{"登录密码"});
			fv.rejectIfEmpty(errors, "confirmPwd", "system.common.valid.error.required", new String[]{"重复密码"});
		}
		
		if (! errors.hasErrors()) 
		{
			if (! confirmPwd.equals(pwd)) 
			{
				errors.rejectValue("pwd", "js.user.mismatch.confirmPassword", new String[]{"", "confirm password"}, "Same value again.");
			}
		}
		
		return errors;
	}
	
	@SuppressWarnings({ "rawtypes", "unused", "unchecked" })
	public Map getColors()
	{
		Map ret = new HashMap();
		
		// bkColor test;
		try { 
			int tempbkColor = Integer.parseInt(mainColor, 16); 
		} catch(Exception e) {
			mainColor = Integer.toHexString(Constants.US_MAIN_COLOR);
		}
		
		
		if (mainColor != null) 
		{
			ret.put("bk", mainColor);
			ret.put("bk_f", getDiffColorString(mainColor, 0xb1a27) );
			
			ret.put("border", getDiffColorString(mainColor, 0x50D13) );
			ret.put("border_f", getDiffColorString(mainColor, 0xb1a27) );
			ret.put("border_h", getDiffColorString(mainColor, 0xb1a27) );
		} 
		
		// bkColor test;
		try { 
			int tempbkColor = Integer.parseInt(bkColor, 16); 
		} catch(Exception e) {
			bkColor = "FFFFFF";
		}
		
		if ( bkColor != null) 
		{
			ret.put("bk_full", bkColor);
			ret.put("bk_th", getDiffColorString(bkColor, 0x0b0b0b) );
			ret.put("bk_th_h", getDiffColorString(bkColor, 0x080808) );
		}		
		return ret;
	}
	
	public String getDiffColorString(String mainColor, int diff)
	{
		int mainCol = Constants.US_MAIN_COLOR;
		try { 
			mainCol = Integer.parseInt(mainColor, 16); 
		} catch(Exception e) {
			
		}
		String ret = Integer.toHexString( (mainCol - diff) & 0x0FFFFFF );
		int len = ret.length();
		if (len < 6) {
			for (int i=0; i< 6-len; i++) {
				ret = "0" + ret;
			}
		}
		return ret;
	}
	
	public void copyFromUser(UserModel nUser)
	{
		this.userNo = nUser.getUserNo();
		this.userName = nUser.getUserName();
		this.userKind = nUser.getUserKind();
		this.locationId = nUser.getLocationId();
		this.address = nUser.getAddress();
		this.empName = nUser.getContactName();
		this.contactName = nUser.getContactName();
		this.mobileNo = nUser.getMobileNo();
		this.telNo = nUser.getTelNo();
	}
}

package com.kpc.eos.service.dataMng.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.transaction.annotation.Transactional;

import com.kpc.eos.core.Constants;
import com.kpc.eos.core.service.BaseService;
import com.kpc.eos.model.bizSetting.HostCustModel;
import com.kpc.eos.model.common.AddressModel;
import com.kpc.eos.model.dataMng.EmpMenuRightModel;
import com.kpc.eos.model.dataMng.EmpSModel;
import com.kpc.eos.model.dataMng.UserModel;
import com.kpc.eos.service.dataMng.UserService;

public class UserServiceImpl extends BaseService implements UserService 
{
	
	@Override
	public UserModel getUser(String fieldName, String fieldVal) throws Exception 
	{
		Map<String, String> data = new HashMap<String,String>();
		data.put("fieldName", fieldName);
		data.put("fieldVal", fieldVal);
		UserModel user =  (UserModel) baseDAO.queryForObject("user.getUser", data);
		return user;
	}
	
	@Transactional
	@Override
	public void saveUser(UserModel user) throws Exception 
	{
		AddressModel locationName = (AddressModel) baseDAO.queryForObject("address.getAddressByLocationId", user.getLocationId());
		
		StringBuilder strBuild = new StringBuilder();
		strBuild = strBuild.append(user.getUserNo()).append(" ").append(user.getUserName()).append(" ").append(locationName.getFullLocationName()).append(" ").append(user.getAddress()).append(" ");
		strBuild = strBuild.append(StringUtils.isEmpty(user.getTelNo())?"":user.getTelNo()).append(" ").append(StringUtils.isEmpty(user.getMobileNo())?"":user.getMobileNo()).append(" ").append(user.getContactName());
		user.setChelp(strBuild.toString());
		
		strBuild = new StringBuilder();
		strBuild.append(user.getUserNo()).append(" ").append(user.getUserName()).append(" ").append(user.getContactName()).append(" ").append(StringUtils.isEmpty(user.getTelNo())?"":user.getTelNo()).append(" ").append(StringUtils.isEmpty(user.getMobileNo())?"":user.getMobileNo());
		user.setEmpChelp(strBuild.toString());
		
		boolean isNew = StringUtils.isEmpty(user.getUserId());
		if ( !isNew )
		{
			baseDAO.update("user.updateUser", user);
			
			// we need to update the emp table for the master user.
			baseDAO.update("user.updateMasterEmpName", user);
			return;
		}
		
		String userId = (String)baseDAO.queryForObject("user.getNextTblId", "eos_user_new");
		String empId = (String)baseDAO.queryForObject("user.getNextTblId", "eos_emp_new");
		user.setUserId(userId);
		user.setEmpId(empId);
		user.setUserMColor(Integer.toHexString(Constants.US_MAIN_COLOR));
		user.setUserBKColor("FFFFFF");
		baseDAO.insert("user.insertUser", user);
		
		// DEPT Data
		String deptId = (String)baseDAO.queryForObject("user.getNextTblId", "eos_dept_new");
		Map<String, String> deptData = new HashMap<String,String>();
		deptData.put("deptId", deptId);
		deptData.put("userId", userId);
		deptData.put("deptNo", user.getUserNo());
		deptData.put("deptName", user.getUserName());
		deptData.put("accountYn", "Y");
		deptData.put("state", "ST0001");
		deptData.put("createBy", empId);
		deptData.put("updateBy", empId);
		baseDAO.insert("user.insertDept", deptData);
		
		user.setUserYn("Y");
		user.setFirstMark("Y");
		user.setCreateBy(empId);
		user.setUpdateBy(empId);
		user.setEmpRole("管理员");
		user.setState("ST0001");
		user.setDeptId(deptId);
		user.setEmpNo(user.getUserNo());
		user.setEmpName(user.getContactName());
		baseDAO.insert("user.insertEmployer", user);
		
		// Insert Menu
		Map<String, String> menuData = new HashMap<String, String>();
		menuData.put("userId", userId);
		menuData.put("userKind", user.getUserKind());
		menuData.put("state", "ST0001");
		baseDAO.insert("menu.insertUserMenu", menuData);
		
		// Insert PageBanner	
		for (int n=0; n<6; n++) 
		{
			baseDAO.insert("configPage.insertPageBanner", userId);
		}
		
		if (user.getUserKind().equals("UK0002") || user.getUserKind().equals("UK0003"))
		{
			// Insert CustType
			String custtypeId = (String)baseDAO.queryForObject("user.getNextTblId", "eos_custtype_new");
			Map<String, String> custTypeData = new HashMap<String, String>();
			custTypeData.put("custtypeId", custtypeId);
			custTypeData.put("userId", userId);
			custTypeData.put("custtypeName", "不分类");
			custTypeData.put("state", "ST0001");
			custTypeData.put("createBy", empId);
			custTypeData.put("updateBy", empId);
			baseDAO.insert("user.insertCustType", custTypeData);
			// Insert Store
			String storeId = (String)baseDAO.queryForObject("user.getNextTblId", "eos_store_new");
			Map<String, String> storeData = new HashMap<String, String>();
			storeData.put("storeId", storeId);
			storeData.put("userId", userId);
			storeData.put("deptId", deptId);
			storeData.put("storeNo", user.getUserNo());
			storeData.put("storeName", "仓库");
			storeData.put("state", "ST0001");
			storeData.put("createBy", empId);
			storeData.put("updateBy", empId);
			baseDAO.insert("user.insertStore", storeData);
			
			// Insert User Item Property
			baseDAO.insert("userItem.insertDefaultUserItemProperty", user);
		}
		
		if (user.getUserKind().equals("UK0001") || user.getUserKind().equals("UK0003"))
		{
			// Insert Delivery Addr
			String addrId = (String)baseDAO.queryForObject("user.getNextTblId", "eos_delivery_addr_new");
			Map<String, String> dAddrData = new HashMap<String,String>();
			dAddrData.put("addrId", addrId);
			dAddrData.put("userId", userId);
			dAddrData.put("locationId", user.getLocationId());
			dAddrData.put("address", user.getAddress());
			dAddrData.put("contactName", user.getContactName());
			dAddrData.put("telNo", user.getTelNo());
			dAddrData.put("mobileNo", user.getMobileNo());
			dAddrData.put("defaultYN", "Y");
			dAddrData.put("state", "ST0001");
			dAddrData.put("createBy", empId);
			dAddrData.put("updateBy", empId);
			baseDAO.insert("user.insertDAddr", dAddrData);
		}
	}
	
	@Override
	@Transactional
	public void saveUser(UserModel user, String hostId) throws Exception 
	{
		saveUser(user);
		// Query for add Host
		if (!StringUtils.isEmpty(hostId))
		{
			UserModel hostUser = this.getUserById(hostId);
			if (hostUser != null)
			{
				String hostUserKind = hostUser.getUserKind();
				if ("UK0002".equals(hostUserKind) || "UK0003".equals(hostUserKind))
				{
					HostCustModel sc = new HostCustModel();
					sc.setHostUserId(hostId);
					sc.setCustUserId(user.getUserId());
					sc.setCreateBy(user.getEmpId());
					sc.setUpdateBy(user.getEmpId());
					baseDAO.insert("hostcust.insertHostList", sc);
				}
			}
		}
	}
	
	@Override
	public UserModel login(UserModel user) throws Exception 
	{
		UserModel u = (UserModel) baseDAO.queryForObject("user.login", user);
		return u;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<UserModel> getEmployerList(HashMap<String, Object> params) throws Exception 
	{
		List<UserModel> empList = baseDAO.queryForList("user.getEmployerList", params);
		return empList;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<UserModel> getEmployerListByUser(String userId) throws Exception
	{
		List<UserModel> empList = baseDAO.queryForList("user.findEmployerListByUser", userId);
		return empList;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<UserModel> getEmployerListByUserPerm(EmpSModel empS) throws Exception
	{
		List<UserModel> empList = baseDAO.queryForList("user.getEmployerListByUserPerm", empS);
		return empList;
	}
	
	@Override
	public Integer countEmployerListCountByUserPerm(EmpSModel empS) throws Exception
	{
		return (Integer)baseDAO.queryForObject("user.countEmployerListByUserPerm", empS);
	}
	
	@Override
	public void saveEmployer(UserModel emp) throws Exception 
	{
		StringBuilder strBuild = new StringBuilder();
		strBuild.append(emp.getEmpNo()).append(" ").append(emp.getEmpName()).append(" ").append(StringUtils.isEmpty(emp.getTelNo())?"":emp.getTelNo()).append(" ").append(StringUtils.isEmpty(emp.getMobileNo())?"":emp.getMobileNo());
		emp.setEmpChelp(strBuild.toString());
		
		// is new?
		if (StringUtils.isEmpty(emp.getEmpId()))
		{
			String empId = (String)baseDAO.queryForObject("user.getNextTblId", "eos_emp_new");
			emp.setUserYn(Constants.CONST_N);	// set the firstmark field in emp table.
			emp.setEmpId(empId);
			baseDAO.insert("user.insertEmployer", emp);
		}
		else
		{
			baseDAO.update("user.updateEmployer", emp);
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<EmpMenuRightModel> getEmployerPrivList(HashMap map) throws Exception 
	{
		List<EmpMenuRightModel> list = baseDAO.queryForList("empMenu.getEmpMenuRightList", map);
		return list;
	}

	@Override
	@Transactional
	public void saveEmployerPriv(String empId, String[] menuIds, String userId) throws Exception 
	{
		baseDAO.getSqlMapClient().startBatch();
		baseDAO.delete("empMenu.deleteEmpMenuRights", empId);
		if (menuIds!= null && menuIds.length > 0)
		{
			for (String menuId: menuIds)
			{
				HashMap<String, String> param = new HashMap<String, String>();
				param.put("empId", empId);
				param.put("menuId", menuId);
				param.put("createBy", userId);
				baseDAO.getSqlMapClient().insert("empMenu.insertEmpMenuRight", param);
			}
		}
		baseDAO.getSqlMapClient().executeBatch();
	}

	@Override
	public void changePassword(UserModel user) throws Exception
	{
		baseDAO.update("user.changePassword", user); 
	}

	@Override
	public UserModel getUserById(String userId) throws Exception 
	{
		UserModel user =  (UserModel) baseDAO.queryForObject("user.getUserById", userId);
		return user;
	}

	@Override
	public String getEmpPwdById(String empId) throws Exception 
	{
		return (String)baseDAO.queryForObject("user.getEmpPwdById", empId);
	}

	@Override
	public UserModel getEmployerByEmpId(String empId) throws Exception 
	{
		return (UserModel) baseDAO.queryForObject("user.getEmployerByEmpId", empId);
	}
	
	@Override
	public Integer existEmployerByNo(String empNo) throws Exception
	{
		return (Integer) baseDAO.queryForObject("user.existEmployerByNo", empNo);
	}

	@Override
	public Integer existEmployer(UserModel emp) throws Exception
	{
		return (Integer) baseDAO.queryForObject("user.existEmployer", emp);
	}
	
	@Override
	public Integer existOtherUser(UserModel user) throws Exception
	{
		return (Integer) baseDAO.queryForObject("user.existOtherUser", user);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Integer checkClerkNo(String clerkNo, String hostId) throws Exception
	{
		Map map = new HashMap();
		map.put("hostId", hostId);
		map.put("clerkNo", clerkNo);
		return (Integer) baseDAO.queryForObject("user.checkClekrNo", map);
	}

	@Transactional
	@Override
	public void saveUserByUserKind(UserModel user, String oldUserKind) throws Exception
	{
		if (user.isBuyer()) 
		{
			baseDAO.update("hostcust.disableHostCustSetting", user.getUserId());
		}
		
		// Delete the menu first
		baseDAO.delete("menu.deleteUserMenu", user.getUserId());
		
		// Insert Menu
		Map<String, String> menuData = new HashMap<String, String>();
		menuData.put("userId", user.getUserId());
		menuData.put("userKind", user.getUserKind());
		menuData.put("state", "ST0001");
		baseDAO.insert("menu.insertUserMenu", menuData);
	}
	
}

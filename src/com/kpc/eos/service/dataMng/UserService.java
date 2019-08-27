
package com.kpc.eos.service.dataMng;

import java.util.HashMap;
import java.util.List;

import com.kpc.eos.model.dataMng.EmpMenuRightModel;
import com.kpc.eos.model.dataMng.EmpSModel;
import com.kpc.eos.model.dataMng.UserModel;

public interface UserService {
		
	public UserModel getUser(String fieldName, String fieldVal) throws Exception;
	public UserModel getUserById(String userId) throws Exception;
	
	public void saveUser(UserModel user) throws Exception;
	public void saveUser(UserModel user, String hostId) throws Exception;
	
	public void saveUserByUserKind(UserModel user, String oldUserKind) throws Exception;
	
	
	public UserModel login(UserModel user) throws Exception;
	
	public void saveEmployer(UserModel user) throws Exception;
	public List<UserModel> getEmployerListByUser(String userId) throws Exception;
	
	public List<UserModel> getEmployerListByUserPerm(EmpSModel empS) throws Exception;
	
	public Integer countEmployerListCountByUserPerm(EmpSModel empS) throws Exception;
	
	@SuppressWarnings("rawtypes")
	public List<EmpMenuRightModel> getEmployerPrivList(HashMap map) throws Exception;
	
	public void saveEmployerPriv(String empId, String[] menuIds, String userId) throws Exception;
	
	public void changePassword(UserModel user) throws Exception;
	
	public String getEmpPwdById(String empId) throws Exception;
	
	public List<UserModel> getEmployerList(HashMap<String, Object> params) throws Exception;
	
	public UserModel getEmployerByEmpId(String empId) throws Exception;
	
	public Integer existEmployerByNo(String empNo) throws Exception;
	
	public Integer existEmployer(UserModel emp) throws Exception;
	
	public Integer existOtherUser(UserModel user) throws Exception;
	
	public Integer checkClerkNo(String clerkNo, String hostId) throws Exception;
	
}

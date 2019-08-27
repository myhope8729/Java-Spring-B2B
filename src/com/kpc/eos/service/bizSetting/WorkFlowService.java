
package com.kpc.eos.service.bizSetting;

import java.util.List;
import java.util.Map;

import com.kpc.eos.model.bizSetting.CustomerModel;
import com.kpc.eos.model.bizSetting.EmployeeModel;
import com.kpc.eos.model.bizSetting.WorkFlowGroupModel;
import com.kpc.eos.model.bizSetting.WorkFlowModel;
import com.kpc.eos.model.bizSetting.WorkFlowSModel;


public interface WorkFlowService {
	
	public List<WorkFlowModel> getWorkFlowList(WorkFlowSModel sc) throws Exception;
	
	public Integer getTotalCountWorkFlowList(WorkFlowSModel sc) throws Exception;
	
	public WorkFlowModel getWorkFlow(String workFlowId) throws Exception;
	
	public List<EmployeeModel> getEmployeeList(String userId, String workFlowId) throws Exception;
	
	public void saveWorkFlow(WorkFlowModel workFlow, boolean mobileYn) throws Exception;
	
	public List<WorkFlowGroupModel> getWorkFlowGroupList(WorkFlowSModel sc) throws Exception;
	
	public List<String> getWorkFlowGroupEmpList(WorkFlowGroupModel sc) throws Exception;
	
	public List<String> getWorkFlowGroupCustList(WorkFlowGroupModel sc) throws Exception;
	
	public WorkFlowGroupModel getWorkFlowGroup(String workFlowId, String seqNo) throws Exception;
	
	public List<CustomerModel> getCustomerCheckedList(String workFlowId, String seqNo, String userId) throws Exception;
	
	public List<EmployeeModel> getEmployeeCheckedList(String workFlowId, String seqNo, String userId) throws Exception;
	
	public void saveWorkFlowGroupCust(WorkFlowGroupModel workFlowGroup) throws Exception;
	
	public void saveWorkFlowGroupEmp(WorkFlowGroupModel workFlowGroup) throws Exception;
	
	public void saveWorkFlowGroup(WorkFlowGroupModel workFlowGroup) throws Exception;
	
	@SuppressWarnings("rawtypes")
	public WorkFlowModel getNextWorkFlowOfBillProcess(Map map, boolean thirdParyFlag) throws Exception;
	
	public List<WorkFlowGroupModel> getWorkFlowGroupListForWfID(WorkFlowSModel sc) throws Exception;
	
	public List<EmployeeModel> getWorkFlowEmpList(WorkFlowSModel sc) throws Exception;
	
	public List<EmployeeModel> getWorkFlowGroupEmployeeList(WorkFlowGroupModel sc) throws Exception;
	
	public Integer getTotalCountWorkFlowGroupList(String workFlowId) throws Exception;
}

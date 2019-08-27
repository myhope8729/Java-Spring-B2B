
package com.kpc.eos.service.bizSetting.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.axis.utils.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.transaction.annotation.Transactional;

import com.kpc.eos.core.Constants;
import com.kpc.eos.core.service.BaseService;
import com.kpc.eos.model.bizSetting.CustomerModel;
import com.kpc.eos.model.bizSetting.EmployeeModel;
import com.kpc.eos.model.bizSetting.WorkFlowGroupModel;
import com.kpc.eos.model.bizSetting.WorkFlowModel;
import com.kpc.eos.model.bizSetting.WorkFlowSModel;
import com.kpc.eos.service.bizSetting.WorkFlowService;

@Transactional
public class WorkFlowServiceImpl extends BaseService implements WorkFlowService {
	
	@SuppressWarnings("unchecked")
	@Override
	public List<WorkFlowModel> getWorkFlowList(WorkFlowSModel sc) throws Exception{
		List<WorkFlowModel> workFlowList =  baseDAO.queryForList("workflow.getWorkFlowList", sc);
		return workFlowList;
	}
	
	public Integer getTotalCountWorkFlowList(WorkFlowSModel sc) throws Exception
	{
		return (Integer)baseDAO.queryForObject("workflow.getTotalCountWorkFlowList", sc);
	}
	
	@Override
	public WorkFlowModel getWorkFlow(String workFlowId) throws Exception{
		WorkFlowModel workFlow = (WorkFlowModel)baseDAO.queryForObject("workflow.getWorkFlow", workFlowId);
		return workFlow;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void saveWorkFlow(WorkFlowModel workFlow, boolean mobileYn) throws Exception{
		if (StringUtils.isEmpty(workFlow.getWorkFlowId())){
			if (mobileYn == false){
				workFlow.setSeqNo(null);
			}
			String strWorkFlowId = (String)baseDAO.insert("workflow.insertWorkFlow", workFlow);
			workFlow.setWorkFlowId(strWorkFlowId);
		}else{
			if (mobileYn == false){
				workFlow.setSeqNo("1000");
			}
			baseDAO.update("workflow.updateWorkFlow", workFlow);
			baseDAO.delete("workflow.deleteProcEmployeeByProcId", workFlow);
			
		}
		
		if (Constants.CONST_N.equals(workFlow.getGroupYn())){
			baseDAO.insert("workflow.insertProcEmployee", workFlow);
		}
		
		if (mobileYn == true) return;
		
		JSONArray jsonArr = new JSONArray(workFlow.getSeqData());
		if (jsonArr == null || jsonArr.length() == 0) return;
		
		baseDAO.getSqlMapClient().startBatch();
		for (int i = 0; i < jsonArr.length(); i++){
			JSONObject obj =  (JSONObject)jsonArr.get(i);
			Map map = new HashMap();
			map.put("workFlowId", obj.getString("workFlowId"));
			map.put("seqNo", 10000 + i);
			baseDAO.update("workflow.updateSeqNo", map);
		}
		for (int i = 0; i < jsonArr.length(); i++){
			JSONObject obj =  (JSONObject)jsonArr.get(i);
			Map map = new HashMap();
			map.put("workFlowId", obj.getString("workFlowId"));
			map.put("seqNo", obj.getInt("seqNo"));
			baseDAO.update("workflow.updateSeqNo", map);
		}
		baseDAO.getSqlMapClient().executeBatch();
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List<EmployeeModel> getEmployeeList(String userId, String workFlowId) throws Exception
	{
		Map map = new HashMap();
		map.put("userId", userId);
		map.put("workFlowId", workFlowId);
		List<EmployeeModel> empList = baseDAO.queryForList("workflow.getEmployeeCheckedList", map);
		
		return empList;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<WorkFlowGroupModel> getWorkFlowGroupList(WorkFlowSModel sc) throws Exception
	{
		List<WorkFlowGroupModel> workFlowGroupList = baseDAO.queryForList("workflow.getWorkFlowGroupList", sc);
		return workFlowGroupList;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<String> getWorkFlowGroupEmpList(WorkFlowGroupModel sc) throws Exception
	{
		List<String> workFlowCustList = baseDAO.queryForList("workflow.getWorkFlowGroupEmpList", sc);
		return workFlowCustList;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<String> getWorkFlowGroupCustList(WorkFlowGroupModel sc) throws Exception
	{
		List<String> workFlowCustList = baseDAO.queryForList("workflow.getWorkFlowGroupCustList", sc);
		return workFlowCustList;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public WorkFlowGroupModel getWorkFlowGroup(String workFlowId, String seqNo) throws Exception
	{
		Map map = new HashMap();
		map.put("workFlowId", workFlowId);
		map.put("seqNo", seqNo);
		WorkFlowGroupModel workFlowGroup = (WorkFlowGroupModel)baseDAO.queryForObject("workflow.getWorkFlowGroup", map);
		
		return workFlowGroup;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List<CustomerModel> getCustomerCheckedList(String workFlowId, String seqNo, String userId) throws Exception
	{
		Map map = new HashMap();
		map.put("workFlowId", workFlowId);
		map.put("seqNo", seqNo);
		map.put("userId", userId);
		
		List<CustomerModel> custList = baseDAO.queryForList("workflow.getWorkFlowGroupCustCheckedList", map);
		
		return custList;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List<EmployeeModel> getEmployeeCheckedList(String workFlowId, String seqNo, String userId) throws Exception
	{
		Map map = new HashMap();
		map.put("workFlowId", workFlowId);
		map.put("seqNo", seqNo);
		map.put("userId", userId);
		List<EmployeeModel> empList = baseDAO.queryForList("workflow.getWorkFlowGroupEmployeeCheckedList", map);
		
		return empList;
	}
	
	@Override
	public void saveWorkFlowGroupCust(WorkFlowGroupModel workFlowGroup) throws Exception
	{
		baseDAO.delete("workflow.deleteWorkFlowGroupCust", workFlowGroup);
		baseDAO.insert("workflow.insertWorkFlowGroupCust", workFlowGroup);
	}
	
	@Override
	public void saveWorkFlowGroupEmp(WorkFlowGroupModel workFlowGroup) throws Exception
	{
		baseDAO.delete("workflow.deleteWorkFlowGroupEmp", workFlowGroup);
		baseDAO.insert("workflow.insertWorkFlowGroupEmp", workFlowGroup);
	}
	
	@Override
	public void saveWorkFlowGroup(WorkFlowGroupModel workFlowGroup) throws Exception
	{
		if (StringUtils.isEmpty(workFlowGroup.getSeqNo()))
		{
			baseDAO.insert("workflow.insertWorkFlowGroup", workFlowGroup);
		}
		else
		{
			baseDAO.update("workflow.updateWorkFlowGroup", workFlowGroup);
		}
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public WorkFlowModel getNextWorkFlowOfBillProcess(Map map, boolean thirdParyFlag) throws Exception
	{
		WorkFlowModel nextWorkFlow = null;
		if (thirdParyFlag)
		{
			nextWorkFlow = (WorkFlowModel)baseDAO.queryForObject("workflow.getNextWorkFlowOfThirdPartyUser", map);
		}
		else
		{
			nextWorkFlow = (WorkFlowModel)baseDAO.queryForObject("workflow.getNextWorkFlowOfCurUser", map);
		}
		return nextWorkFlow;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<WorkFlowGroupModel> getWorkFlowGroupListForWfID(WorkFlowSModel sc) throws Exception
	{
		List<WorkFlowGroupModel> wfGroupList = baseDAO.queryForList("workflow.getWFGroupListByCondition", sc);
		return wfGroupList;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<EmployeeModel> getWorkFlowEmpList(WorkFlowSModel sc) throws Exception
	{
		List<EmployeeModel> wfEmpList = baseDAO.queryForList("workflow.getWorkFlowEmpList", sc);
		return wfEmpList;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<EmployeeModel> getWorkFlowGroupEmployeeList(WorkFlowGroupModel sc) throws Exception
	{
		List<EmployeeModel> wfGroupEmpList = baseDAO.queryForList("workflow.getWorkFlowGroupEmployeeList", sc);
		return wfGroupEmpList;
	}
	
	@Override
	public Integer getTotalCountWorkFlowGroupList(String workFlowId) throws Exception{
		return (Integer)baseDAO.queryForObject("workflow.countWorkFlowGroupList", workFlowId);
	}
}

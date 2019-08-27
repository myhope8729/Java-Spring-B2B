
package com.kpc.eos.service.dataMng.impl;

import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.transaction.annotation.Transactional;

import com.kpc.eos.core.service.BaseService;
import com.kpc.eos.model.common.DefaultSModel;
import com.kpc.eos.model.dataMng.DepartmentModel;
import com.kpc.eos.service.dataMng.DepartmentService;

@Transactional
public class DepartmentServiceImpl extends BaseService implements DepartmentService {
	
    @SuppressWarnings("unchecked")
    @Override
    public List<DepartmentModel> getDepartmentList() throws Exception {
    	List<DepartmentModel> deptList = baseDAO.queryForList("department.getDepartmentList", null);
    	return deptList;
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public List<DepartmentModel> getDepartmentList(HashMap<String, Object> params) throws Exception {
    	List<DepartmentModel> deptList = baseDAO.queryForList("department.getDepartmentList", params);
    	return deptList;
    }
    
    @SuppressWarnings("unchecked")
	@Override
    public List<DepartmentModel> getDepartmentListGridAjax(DefaultSModel sc) throws Exception {
    	List<DepartmentModel> deptList = baseDAO.queryForList("department.getDepartmentList", sc);
    	return deptList;
    }
    
    @SuppressWarnings("unchecked")
    @Override
	public List<DepartmentModel> getDepartmentList(String userId) throws Exception {
    	return baseDAO.queryForList("department.getDepartmentListByUserId", userId);
    }
    
    @Override
    public Integer getTotalCountDeptList(DefaultSModel sc) throws Exception {
    	return (Integer) baseDAO.queryForObject("department.getTotalCountDeptList", sc);
    }
    
    @Override
    public DepartmentModel getDepartment(String deptId) throws Exception {
    	
    	return (DepartmentModel)baseDAO.queryForObject("department.getDepartment", deptId);
    }
    
    @Override
    public void saveDepartment(DepartmentModel dept) throws Exception {
    	if (StringUtils.isNotEmpty(dept.getDeptId()))
		{	
    		baseDAO.update("department.updateDepartment", dept);
		}
		else	
		{
			baseDAO.update("department.insertDepartment", dept);
		}
    }
    
	@Override
	public Integer existDepartmentNo(DepartmentModel dept) throws Exception{
		return (Integer) baseDAO.queryForObject("department.checkExistDeparment", dept);
	}
}

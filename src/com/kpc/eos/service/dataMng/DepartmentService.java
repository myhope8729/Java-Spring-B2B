
package com.kpc.eos.service.dataMng;

import java.util.HashMap;
import java.util.List;

import com.kpc.eos.model.common.DefaultSModel;
import com.kpc.eos.model.dataMng.DepartmentModel;

public interface DepartmentService {
	
    public List<DepartmentModel> getDepartmentList() throws Exception;
	
    public List<DepartmentModel> getDepartmentList(HashMap<String, Object> params) throws Exception;
	
    public List<DepartmentModel> getDepartmentListGridAjax(DefaultSModel sc) throws Exception;
    
    public Integer getTotalCountDeptList(DefaultSModel sc) throws Exception;
    
    public List<DepartmentModel> getDepartmentList(String userId) throws Exception;
    
    public DepartmentModel getDepartment(String deptId) throws Exception;
	
    public void saveDepartment(DepartmentModel dept) throws Exception;
    
    public Integer existDepartmentNo(DepartmentModel dept) throws Exception;
}

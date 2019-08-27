
package com.kpc.eos.controller.system;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.servlet.ModelAndView;

import com.kpc.eos.controller.BaseEOSController;
import com.kpc.eos.core.exception.ServiceException;
import com.kpc.eos.core.model.ResultModel;
import com.kpc.eos.model.common.BreadcrumbModel;
import com.kpc.eos.model.system.CodeModel;
import com.kpc.eos.model.system.CodeSModel;
import com.kpc.eos.model.system.ListDataSaveModel;
import com.kpc.eos.service.system.CodeService;

/**
 * CodeController
 * =================
 * Description : System Code Management Controller
 * Methods :
 */
public class CodeController extends BaseEOSController {
	
	private CodeService codeService;

	public void setCodeService(CodeService codeService) {
		this.codeService = codeService;
	}
	
	public CodeController() 
	{
		super();
		controllerId = "Code";
	}
	
	public void initCmd()
	{
		super.initCmd();
		breads.add(new BreadcrumbModel("系统资料", "", false));
		breads.add(new BreadcrumbModel("编码资料", getCmdUrl( "codeList" ), true));
	}
	/**
	 * codeList
	 * ===================
	 * Search Code List
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 */
	public ModelAndView codeList(HttpServletRequest request, HttpServletResponse response, CodeSModel sc) throws Exception {
		
		ModelAndView mv = new ModelAndView("system/codeList");
		
		List<CodeModel> list = codeService.findCodeGroupList(sc);
		mv.addObject("upperCodeList", list);
		mv.addObject("sc", sc);
		
		return mv;
	}

	/**
	 * codeGridAjax
	 * ===================
	 * Code list by a code group
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView codeGridAjax(HttpServletRequest request, HttpServletResponse response, CodeSModel sc) throws Exception {
		
		ModelAndView mv = new ModelAndView("jsonView");
		
		List<CodeModel> list = codeService.getCodeList(sc);
		mv.addObject("rows", list);

		return mv;
	}
	

	/**
	 * saveCodeAjax
	 * ===================
	 * Save Code item information by ajax
	 * @param request
	 * @param response
	 * @param codeGroup
	 * @return
	 * @throws Exception
	 */
	public ModelAndView saveCodeAjax(HttpServletRequest request, HttpServletResponse response, ListDataSaveModel code) throws Exception {
		
		List<CodeModel> paramList = (List<CodeModel>) code.makeModelList(CodeModel.class);
		
		codeService.saveCode(paramList);
		
		ModelAndView mv = new ModelAndView("jsonView", "result", new ResultModel());
		return mv;
	}
}

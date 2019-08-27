package com.kpc.eos.controller.common;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.kpc.eos.core.exception.ServiceException;
import com.kpc.eos.core.model.ResultModel;
import com.kpc.eos.core.util.FileUtil;
import com.kpc.eos.core.util.SessionUtil;
import com.kpc.eos.core.web.context.ApplicationSetting;
import com.kpc.eos.model.common.UploadFile;
import com.kpc.eos.model.dataMng.UserModel;
import com.kpc.eos.service.common.FileService;

public class FileUploadController extends CommonController {

	private FileService fileService;
	
	public void setFileService(FileService fileService) {
		this.fileService = fileService;
	}
	
	/**
	 * File Upload
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView fileUploadForm(HttpServletRequest request, HttpServletResponse response) {
		
		ModelAndView mv = new ModelAndView("common/system/ajax/fileUploadForm");
		
		mv.addObject("url", request.getParameter("url"));
		mv.addObject("cbf", request.getParameter("cbf"));
		mv.addObject("module", request.getParameter("module"));
		
		return mv;
	}
	
	public ModelAndView fileUploadForm2(HttpServletRequest request, HttpServletResponse response) {
		
		ModelAndView mv = new ModelAndView("common/system/ajax/fileUploadForm2");
		
		mv.addObject("url", request.getParameter("url"));
		mv.addObject("cbf", request.getParameter("cbf"));
		mv.addObject("mediaNo", request.getParameter("mediaNo"));
		mv.addObject("img", request.getParameter("img"));
		mv.addObject("imgValid", request.getParameter("imgValid"));
		mv.addObject("deny", request.getParameter("deny"));
		mv.addObject("module", request.getParameter("module"));
		
		return mv;
	}
	
	/**
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView fileUploadSizeForm(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView("common/system/ajax/fileUploadSizeForm");
		String module 	= StringUtils.defaultString(request.getParameter("module"));
		String allow 	= ApplicationSetting.getConfig(new StringBuffer("upload.").append(module).append(".allow").toString());
		String width 	= ApplicationSetting.getConfig(new StringBuffer("upload.").append(module).append(".width").toString());
		String height 	= ApplicationSetting.getConfig(new StringBuffer("upload.").append(module).append(".height").toString());
		String maxsize 	= ApplicationSetting.getConfig(new StringBuffer("upload.").append(module).append(".maxsize").toString());
		mv.addObject("allow"	, allow);	//file extension to allow to upload 	
		mv.addObject("width"	, width);	//image max width		
		mv.addObject("height"	, height);	//image max height		
		mv.addObject("maxsize"	, maxsize);	//image max size		
		mv.addObject("url", request.getParameter("url"));
		mv.addObject("cbf", request.getParameter("cbf"));
		mv.addObject("module", module);
		return mv;
	}
	
	/**
	 * file upload by ajax
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView uploadAjax(HttpServletRequest request, HttpServletResponse response) throws Exception {
//		ModelAndView mv = new ModelAndView("common/system/upload");
		ModelAndView mv = new ModelAndView("jsonView");
		
		UploadFile file = FileUtil.uploadImageFileOnLocal(request
				, "productimage"//request.getParameter("module")
				, "file"
				, ""
				, "prd001");
		if(file != null){
			// insert media table
			fileService.insertFile(file);
			mv.addObject("fileInfo", JSONObject.fromObject( file ) );
			mv.addObject("result", new ResultModel("File sent successfully."));
		}
		
		mv.addObject("cbf", request.getParameter("cbf"));
		return mv;
	}
	
	/**
	 * Media file upload  - upload only file, no save DB.
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView uploadFileOnlyAjax(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		ModelAndView mv = new ModelAndView("jsonView");
		MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
		CommonsMultipartFile f = (CommonsMultipartFile)multiRequest.getFile("file");
		String module = StringUtils.defaultString(request.getParameter("module"));
		Boolean resultsize = false;
		mv.addObject("cbf", request.getParameter("cbf"));			
		
		String customUploadPath = "";
		String seq = fileService.getNewMediaNo();
		String inputFieldName = "file";
		
		resultsize = FileUtil.checkSizeLimit(module, f.getSize());
		if (logger.isDebugEnabled()) {logger.debug("f.getSize() : "+f.getSize());}
		mv.addObject("resultsize", resultsize);
		if (logger.isDebugEnabled()) {logger.debug("resultsize : "+resultsize);}
		if( ! resultsize) return mv;				

		String imgVolume = FileUtil.checkPixelSameSize(module, f.getInputStream()) ? "success" : "fail";
		mv.addObject("imgVolume", imgVolume);
		if("fail".equals(imgVolume)) return mv;				
		
		//file upload
		UploadFile file = FileUtil.uploadImageFileWithoutCheck(request, module, inputFieldName, customUploadPath, seq);
		mv.addObject("fileInfo", JSONObject.fromObject(file));			

		return mv;
	}	
	
	//
	public ModelAndView uploadFileOnlyAjax2(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		ModelAndView mv = new ModelAndView("jsonView");
		MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
		CommonsMultipartFile f = (CommonsMultipartFile)multiRequest.getFile("file");
		String module = StringUtils.defaultString(request.getParameter("module"));
		mv.addObject("cbf", request.getParameter("cbf"));			
		
		String customUploadPath = "";
		String inputFieldName = "file";
		
		mv.addObject("result", new ResultModel());
		mv.addObject("mediaNo", request.getParameter("mediaNo"));
		mv.addObject("img", request.getParameter("img"));
		mv.addObject("imgValid", request.getParameter("imgValid"));
		mv.addObject("deny", request.getParameter("deny"));

		//if image size(bytes) < max size
		String checkResult = FileUtil.checkSizeLimit(module, f.getSize())? "success" : "fail";
		if (logger.isDebugEnabled()) {logger.debug("f.getSize() : "+f.getSize());}
		if("fail".equals(checkResult)) {
			UploadFile file = new UploadFile();
			file.setCheckSize(checkResult);
			mv.addObject("fileInfo", JSONObject.fromObject(file));
			return mv;
		}

		//if image width, height < max size
		checkResult = FileUtil.checkPixelSameSize(module, f.getInputStream()) ? "success" : "fail";
		if("fail".equals(checkResult)) {
			UploadFile file = new UploadFile();
			file.setCheckPixel(checkResult);
			mv.addObject("fileInfo", JSONObject.fromObject(file));
			return mv;
		}
		
		//if image width, height < max size
		checkResult = FileUtil.checkFileExt(module, f.getOriginalFilename()) ? "success" : "fail";
		if("fail".equals(checkResult)) {
			UploadFile file = new UploadFile();
			file.setCheckExt(checkResult);
			mv.addObject("fileInfo", JSONObject.fromObject(file));
			return mv;
		}
		
		//file upload
		String special = ApplicationSetting.getConfig("upload." + module + ".special");
		if("Y".equals(special)) {
			String fileName = ApplicationSetting.getConfig("upload." + module + ".fileName");
			UploadFile file = FileUtil.uploadImageFileWithoutCheckForSpecial(request, module, inputFieldName, customUploadPath, fileName);
			mv.addObject("fileInfo", JSONObject.fromObject(file));			
		} else {
			String seq = fileService.getNewMediaNo();
			UploadFile file = FileUtil.uploadImageFileWithoutCheck(request, module, inputFieldName, customUploadPath, seq);
			file.setMediaNo(seq);
			Object obj = SessionUtil.getUser(request, "bo");
			if (obj != null) {
				UserModel user = (UserModel) obj;
				file.setCreateBy(user.getEmpId());
				file.setLastUpdateBy(user.getEmpId());
			} else {
				file.setCreateBy("system");
				file.setLastUpdateBy("system");
			}
			
			fileService.insertMediaTemp(file);
			mv.addObject("fileInfo", JSONObject.fromObject(file));			
		}
		
		return mv;
	}	
	
	/**
	 * Media file upload  -  INSERT only DB
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView saveUploadFileDataAjax(HttpServletRequest request, HttpServletResponse response, UploadFile file)
			throws Exception {
		ModelAndView mv = new ModelAndView("jsonView", "result", new ResultModel());
		if (file != null) {
			// insert media table
			fileService.insertFile(file);
		}
		mv.addObject("cbf", request.getParameter("cbf"));
		mv.addObject("fileInfo", JSONObject.fromObject(file));
		return mv;
	}	
	
	/**
	 * deleteUploadedFile
	 * ===================
	 * @param request - mediaNo 
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView deleteUploadedFileAjax(HttpServletRequest request, HttpServletResponse response, UploadFile file) throws Exception {
		ModelAndView mv = new ModelAndView("jsonView");
		
		if (StringUtils.isBlank(file.getMediaNo())) {
			String mediaNo = request.getParameter("attachFiles.mediaNo");
			if (StringUtils.isBlank(mediaNo)) {
				throw new ServiceException("Media Number is required.");
			} else {
				file.setMediaNo(mediaNo);
			}
		}
		
		file = fileService.getFile(file);
		
		ResultModel result = new ResultModel();
		if (FileUtil.deleteFile(file)) {
			fileService.deleteFile(file);
			result.setResultMsg("File was deleted successfully");
		} else {
			result.setResultCode(-1);
			result.setResultMsg("File delete Failed.");
		}
		mv.addObject("result", result);
		
		return mv;
	}
	
	/**
	 * ckEditorUpload
	 * ===================
	 * CkEditor file upload 
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView ckEditorUpload(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView("common/system/ckEditorUpload");
		
		UploadFile file = FileUtil.uploadImageFile(request, "ckEditor", "upload", "", String.valueOf(new Date().getTime()));
		
		if(file != null){
			mv.addObject("fileInfo", JSONObject.fromObject( file ) );
		}		
		mv.addObject("cbf", request.getParameter("cbf"));
		
		return mv;
	}
	
	/**
	 * fileDownload<br>
	 * ===================<br>
	 * File download
	 * @param request
	 * @param response
	 * @param file
	 * 			mediaNo required
	 * @return
	 * @throws Exception
	 */
	public ModelAndView fileDownload(HttpServletRequest request, HttpServletResponse response, UploadFile file) throws Exception {
		ModelAndView mv = new ModelAndView("sendContentView");
		
		if (StringUtils.isBlank(file.getMediaNo()))
			throw new ServiceException("Media Number is required.");
		
		file = fileService.getFile(file);
		
		mv.addObject("file", file.getSaveAbsPath() + "/" + file.getSysFileName());
		mv.addObject("saveAs", file.getUserFileName());
		
		return mv;
	}
	
}

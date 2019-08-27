package com.kpc.eos.model.common;

import java.util.List;
import lombok.Data;

import com.kpc.eos.core.model.CommonModel;

public @Data class UploadFile extends CommonModel {

	private static final long serialVersionUID = -1890860638219516813L;
	
	public static final String IMAGE_SERVER = "IMAGE";
	public static final String WAS_SERVER 	= "WAS";
	
	private String mediaNo;
	private String mediaDesc;
	private String mediaLevelCd;
	
	private String mediaTypeCd;
	private String saveServer;
	private long fileSize;
	private String userFileName;
	private String sysFileName;
	private String saveAbsPath;
	private String saveRelPath;
	
	private String localFileName;
	private String localAbsPath;

	private List<String> mediaNoList; 
	
	private String checkSize = "success"; 
	private String checkPixel = "success"; 
	private String checkExt = "success"; 
	
	private String createBy;	
	
	private String createTime;
	
	private String lastUpdateBy;
	
	private String lastUpdateTime;
	
	private String resultCode;
	
	private String resultMsg;
	
	public UploadFile() {}
}

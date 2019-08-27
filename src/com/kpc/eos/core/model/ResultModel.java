package com.kpc.eos.core.model;

import com.kpc.eos.core.util.MessageUtil;

public class ResultModel extends BaseModel {
    
    static final long serialVersionUID = -5241452994094574790L;
    
    public static final int 	RESULT_SUCCESS_CODE = 0;
    public static final String 	RESULT_SUCCESS_MSG 	= MessageUtil.getMessage("system.common.success");

    public static final int 	RESULT_FAIL_CODE 	= -1;
    public static final String 	RESULT_FAIL_MSG 	= "FAIL";

    private int resultCode;
        
    private String resultMsg;
    
    private Object resultData;
    
    public ResultModel() {
        this.resultCode = RESULT_SUCCESS_CODE;
        this.resultMsg 	= RESULT_SUCCESS_MSG;
    }

    public ResultModel(String resultMsg) {
    	this.resultMsg = resultMsg;
    }
    
    public ResultModel(int resultCode, String resultMsg) {
    	this.resultCode = resultCode;
    	this.resultMsg = resultMsg;
    }
    
    public int getResultCode() {
        return resultCode;
    }

    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }

    public String getResultMsg() {
        return resultMsg;
    }

    public void setResultMsg(String resultMsg) {
        this.resultMsg = resultMsg;
    }
    
    public Object getResultData() {
    	return resultData;
    }
    
    public void setResultData(Object resultData) {
    	this.resultData = resultData;
    }

}
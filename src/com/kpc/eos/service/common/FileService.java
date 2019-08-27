/**
 * COPYRIGHT(c) KT POC Backoffice 2012
 *
 * Package Name: com.kt.poc.service.common
 * Author: 
 * Description: 
 * Modification Log:
 * When			Version	Who		What
 * -----------------------------------
 * 2011. 08. 22.	0.1		
 */
package com.kpc.eos.service.common;

import java.util.List;

import com.kpc.eos.model.common.UploadFile;

/**
 * FileService
 * =================
 * Description : 설명
 * Methods :
 */
public interface FileService {

	public void insertFile(UploadFile file) throws Exception;
	
	public UploadFile getNewMediaNo(UploadFile file) throws Exception;

	public String getNewMediaNo() throws Exception;

	public void insertMediaTemp(UploadFile file) throws Exception;
	
	public void setMediaValid(String mediaNo) throws Exception;

	public UploadFile getFile(UploadFile file) throws Exception;
	
	public void deleteFile(UploadFile file) throws Exception;
	
	public void updateMediaId(UploadFile file) throws Exception;
	
	public List<UploadFile> findFileList(String mediaId) throws Exception;
}

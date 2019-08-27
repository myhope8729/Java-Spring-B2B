/**
 * COPYRIGHT(c) KT POC Backoffice 2012
 *
 * Package Name: com.kt.poc.service.common.impl
 * Author: 
 * Description: 
 * Modification Log:
 * When			Version	Who		What
 * -----------------------------------
 * 2011. 08. 22.	0.1		
 */
package com.kpc.eos.service.common.impl;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.kpc.eos.core.service.BaseService;
import com.kpc.eos.model.common.UploadFile;
import com.kpc.eos.service.common.FileService;

/**
 * FileServiceImpl
 * =================
 * Description : 설명
 * Methods :
 */
@Transactional
public class FileServiceImpl extends BaseService implements FileService {

	@Override
	public void insertFile(UploadFile file) throws Exception {
		if (file.getMediaNo() == null)
			baseDAO.insert("file.insertMedia", file);
		else
			baseDAO.update("file.updateMedia", file);
	}

	@Override
	public UploadFile getFile(UploadFile file) throws Exception {
		return (UploadFile) baseDAO.queryForObject("file.getFile", file);
	}

	@Override
	public void deleteFile(UploadFile file) throws Exception {
		baseDAO.update("file.deleteMedia", file);
	}
	
	@Override
	public void updateMediaId(UploadFile file) throws Exception {
		baseDAO.update("file.updateMediaId", file);
	}
	
	@Override
	public List<UploadFile> findFileList(String mediaNo) throws Exception {
		return baseDAO.queryForList("file.findFileList", mediaNo);
	}

	@Override
	public UploadFile getNewMediaNo(UploadFile file) throws Exception {
		baseDAO.insert("file.insertMedia2", file);
		
		return file;
	}

	@Override
	public String getNewMediaNo() throws Exception {
		return (String)baseDAO.queryForObject("file.getNewMediaNo", null);
	}

	@Override
	public void insertMediaTemp(UploadFile file) throws Exception {
		baseDAO.insert("file.insertMediaTemp", file);
	}

	@Override
	public void setMediaValid(String mediaNo) throws Exception {
		baseDAO.insert("file.setMediaValid", mediaNo);
	}
	
}

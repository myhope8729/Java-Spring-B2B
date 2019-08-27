/**
 * Filename		: InfoServiceImpl.java
 * Description	:
 * 
 * 2018
 */
package com.kpc.eos.service.bill.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.transaction.annotation.Transactional;

import com.kpc.eos.core.service.BaseService;
import com.kpc.eos.core.util.FileUtil;
import com.kpc.eos.model.bill.BillHeadModel;
import com.kpc.eos.model.bill.InfoAttachmentModel;
import com.kpc.eos.model.bill.InfoDetailModel;
import com.kpc.eos.model.bill.InfoDetailSModel;
import com.kpc.eos.model.bill.InfoModel;
import com.kpc.eos.model.common.DefaultSModel;
import com.kpc.eos.service.bill.InfoService;

/**
 * Filename		: InfoServiceImpl.java
 * Description	:
 * 2018
 * @author		: RKRK
 */
public class InfoServiceImpl extends BaseService implements InfoService
{

	@SuppressWarnings("unchecked")
	@Override
	public List<InfoModel> getUserInfoList(DefaultSModel sc) throws Exception
	{
		return (List<InfoModel>) baseDAO.queryForList("info.getUserInfoList", sc);
	}
	
	
	public void deleteUserInfoList(String userId, String billId)  throws Exception
	{
		baseDAO.delete("info.deleteInfoByBillId", billId);
		baseDAO.delete("info.deleteInfoDetailByBillId", billId);
	}
	
	@Override
	@Transactional
	public Object saveInfoPic(List<InfoModel> infoList, List<InfoDetailModel> infoDetailList, BillHeadModel info) throws Exception
	{
		// delete the info list.
		deleteUserInfoList(info.getUserId(), info.getBillId());
		
		baseDAO.getSqlMapClient().startBatch();
		
		for (int i=0; i< infoList.size(); i++)
		{
			baseDAO.insert("info.insertInfo", infoList.get(i));
		}

		for (int i=0; i< infoDetailList.size(); i++)
		{
			baseDAO.insert("info.insertInfoDetail", infoDetailList.get(i));
		}
		
		baseDAO.getSqlMapClient().executeBatch();
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<InfoDetailModel> getInfoDetailList(InfoDetailSModel sc)
			throws Exception
	{
		return (List<InfoDetailModel>) baseDAO.queryForList("info.getInfoDetailList", sc);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<String> getDeleteImageList(String userId, String billId, List<String> saveImgList) throws Exception 
	{

		Map map = new HashMap();
		map.put("userId", userId);
		map.put("billId", billId);
		map.put("imgNames", saveImgList);
		
		List<String> deleteImageList = baseDAO.queryForList("info.getDeleteImageList", map);
		return deleteImageList;
	}

	@Override
	public InfoAttachmentModel getInfoAttachment(String id) throws Exception
	{
		return (InfoAttachmentModel) baseDAO.queryForObject("info.getInfoAttachment", id);
	}
	
	@Override
	public Integer countInfoAttachmentList(DefaultSModel sc)throws Exception
	{
		
		return (Integer)baseDAO.queryForObject("info.countInfoAttachmentList", sc);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<InfoAttachmentModel> getInfoAttachmentList(DefaultSModel sc)throws Exception
	{
		return baseDAO.queryForList("info.getInfoAttachmentList", sc);
	}

	@Override
	@Transactional
	public void deleteInfoAttachment(String id)  throws Exception
	{
		
		InfoAttachmentModel attach = getInfoAttachment(id);
		if (attach != null && StringUtils.isNotEmpty(attach.getAttachName()))
		{
			FileUtil.deleteFile("infoAttachment", attach.getAttachName());
		}
		baseDAO.delete("info.deleteInfoAttachment", id);
	}

	@Override
	public Object saveInfoAttachment(InfoAttachmentModel attach) throws Exception
	{
		return baseDAO.insert("info.insertInfoAttachment", attach);
	}
}


package com.kpc.eos.service.bill;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.kpc.eos.model.bill.BillHeadModel;
import com.kpc.eos.model.bill.InfoAttachmentModel;
import com.kpc.eos.model.bill.InfoDetailModel;
import com.kpc.eos.model.bill.InfoDetailSModel;
import com.kpc.eos.model.bill.InfoModel;
import com.kpc.eos.model.common.DefaultSModel;

public interface InfoService
{
	public List<InfoModel> getUserInfoList(DefaultSModel sc) throws Exception;
	
	public List<InfoDetailModel> getInfoDetailList(InfoDetailSModel sc) throws Exception;
	
	public List<String> getDeleteImageList(String userId, String billId, List<String> saveImgList) throws Exception ;
	
	public Object saveInfoPic(List<InfoModel> infoPicList, List<InfoDetailModel> infoPicDetailList, BillHeadModel info) throws Exception ;
	
	
	public void deleteUserInfoList(String userId, String billId) throws Exception;
	
	public Integer countInfoAttachmentList(DefaultSModel sc) throws Exception;
	
	public List<InfoAttachmentModel> getInfoAttachmentList(DefaultSModel sc) throws Exception;
	
	public InfoAttachmentModel getInfoAttachment(String id) throws Exception;
	
	public Object saveInfoAttachment(InfoAttachmentModel attach) throws Exception ;
	
	public void deleteInfoAttachment(String id) throws Exception;
}

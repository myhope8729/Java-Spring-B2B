package com.kpc.eos.service.bizSetting;

import java.util.List;
import java.util.Map;

import com.kpc.eos.model.dataMng.UserModel;
import com.kpc.eos.model.bizSetting.ConfigPageBannerModel;
import com.kpc.eos.model.bizSetting.ConfigPageDetailModel;

public interface ConfigPageService {
	
	public List<ConfigPageBannerModel> getPageBannerList(String userId) throws Exception;
	public List<ConfigPageDetailModel> getPageDetailList(String userId) throws Exception;
	
	public List<ConfigPageBannerModel> getAllPageBannerList(String userId) throws Exception;
	public List<ConfigPageDetailModel> getAllPageDetailList(String userId) throws Exception;
	
	public ConfigPageBannerModel getPageBannerById(String id) throws Exception;
	public ConfigPageDetailModel getPageDetailById(String id) throws Exception;
	
	public void savePageMain(UserModel userModel) throws Exception;
	public void savePageBanner(ConfigPageBannerModel pageBanner) throws Exception;
	public void savePageDetail(ConfigPageDetailModel pageDetail) throws Exception;
	
	public void deletePageDetail(Map<String, String> map) throws Exception;
	public String addPageDetail(ConfigPageDetailModel pageDetail) throws Exception;
	
	public void setPageDetailWidth(ConfigPageDetailModel pageDetail) throws Exception;
}

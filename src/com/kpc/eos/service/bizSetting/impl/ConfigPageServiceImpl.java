package com.kpc.eos.service.bizSetting.impl;

import org.apache.commons.lang.StringUtils;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.kpc.eos.core.service.BaseService;
import com.kpc.eos.service.bizSetting.ConfigPageService;

import com.kpc.eos.model.dataMng.UserModel;
import com.kpc.eos.model.bizSetting.ConfigPageBannerModel;
import com.kpc.eos.model.bizSetting.ConfigPageDetailModel;

@Transactional
public class ConfigPageServiceImpl extends BaseService implements ConfigPageService {
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List<ConfigPageBannerModel> getPageBannerList(String userId) throws Exception {
		List<ConfigPageBannerModel> bannerList = (List<ConfigPageBannerModel>)baseDAO.queryForList("configPage.getPageBannerByUser", userId);		
		return bannerList;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List<ConfigPageBannerModel> getAllPageBannerList(String userId) throws Exception {
		List<ConfigPageBannerModel> bannerList = (List<ConfigPageBannerModel>)baseDAO.queryForList("configPage.getAllPageBannerByUser", userId);		
		return bannerList;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List<ConfigPageDetailModel> getAllPageDetailList(String userId) throws Exception {
		List<ConfigPageDetailModel> detailList = (List<ConfigPageDetailModel>)baseDAO.queryForList("configPage.getAllPageDetailByUser", userId);		
		return detailList;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List<ConfigPageDetailModel> getPageDetailList(String userId) throws Exception {
		List<ConfigPageDetailModel> detailList = (List<ConfigPageDetailModel>)baseDAO.queryForList("configPage.getPageDetailByUser", userId);		
		return detailList;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public ConfigPageBannerModel getPageBannerById(String id) throws Exception {
		ConfigPageBannerModel banner = (ConfigPageBannerModel)baseDAO.queryForObject("configPage.getPageBannerById", id);
		return banner;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public ConfigPageDetailModel getPageDetailById(String id) throws Exception {
		ConfigPageDetailModel detail = (ConfigPageDetailModel)baseDAO.queryForObject("configPage.getPageDetailById", id);
		return detail;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void savePageMain(UserModel userModel) throws Exception {
		baseDAO.update("configPage.updateUserPage", userModel);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void savePageBanner(ConfigPageBannerModel pageBanner) throws Exception {
		baseDAO.update("configPage.updatePageBanner", pageBanner);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void savePageDetail(ConfigPageDetailModel pageDetail) throws Exception {
		baseDAO.update("configPage.updatePageDetail", pageDetail);
	}

	/* (non-Javadoc)
	 * @see com.kpc.eos.service.bizSetting.ConfigPageService#deletePageDetail(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public void deletePageDetail(Map<String, String> map) throws Exception {
		baseDAO.delete("configPage.deletePageDetail", map);
	}

	/* (non-Javadoc)
	 * @see com.kpc.eos.service.bizSetting.ConfigPageService#addPageDetail(java.util.Map)
	 */
	@Override
	public String addPageDetail(ConfigPageDetailModel pageDetail) throws Exception {
		String detailId = (String)baseDAO.queryForObject("user.getNextTblId", "eos_page_detail_new");
		pageDetail.setDetailId(detailId);
		baseDAO.insert("configPage.insertPageDetail", pageDetail);
		return detailId;
	}

	/* (non-Javadoc)
	 * @see com.kpc.eos.service.bizSetting.ConfigPageService#setPageDetailWidth(com.kpc.eos.model.bizSetting.ConfigPageDetailModel)
	 */
	@Override
	public void setPageDetailWidth(ConfigPageDetailModel pageDetail) throws Exception {
		baseDAO.update("configPage.updatePageDetailWidth", pageDetail);
	}
}

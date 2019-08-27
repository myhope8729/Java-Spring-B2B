
package com.kpc.eos.model.bizSetting;

import java.lang.reflect.Field;

import lombok.Data;

import org.apache.commons.lang.StringUtils;

import com.kpc.eos.core.Constants;
import com.kpc.eos.core.model.CommonModel;
import com.kpc.eos.core.util.FileUtil;

@Data
public class ConfigPageBannerModel extends CommonModel {
	
	private static final long serialVersionUID = 7204228868895697763L;
	
	private String 	bannerId;
	private String 	userId;
	private String 	urlType;
	private String 	url;
	private String 	showMark;
	private String 	bannerImgPath;
	
	private String  productGroup;
	/*private String 	bannerImgFile;
		
	public String getBannerImgFile()
	{
		if (StringUtils.isNotEmpty(bannerImgPath) && FileUtil.checkFileExist("pagebanner", bannerImgPath)){
			return "/uploaded/pagebanner/" + bannerImgPath;
		}else{
			return "/uploaded/pagebanner/noImage_300x200.gif";
		}
	}*/
	
	public String getBannerImgPath()
	{
		
		if (bannerImgPath != null){
			return bannerImgPath;
		}else{
			return "";
		}
	}	
	
	public ConfigPageBannerModel() {
		
	}
}
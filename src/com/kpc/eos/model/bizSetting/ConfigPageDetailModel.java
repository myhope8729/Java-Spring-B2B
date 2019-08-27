
package com.kpc.eos.model.bizSetting;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

import com.kpc.eos.core.model.CommonModel;
import com.kpc.eos.model.userPage.InfoLinkModel;
@Data
public class ConfigPageDetailModel extends CommonModel {
	
	private static final long serialVersionUID = 8790261791945000250L;
	
	private String 	detailId;
	private String 	userId;
	private String	rowNum;
	private String	colNum;
	private String	cellNum;
	
	private String 	detailType;
	private String 	urlType;
	private String 	url;
	private String 	widthPc;
	private String 	widthMob;
	private String 	bdColor;
	private String 	mobMark;
	private String  productId;
	private String  productGroup;
	
	private String 	picNote;
	private String 	picSize;
	private String 	picBdColor;
	
	private String 	fontNote;
	private String 	fontSize;
	private String 	fontColor;
	private String 	bgColor;
	private String 	liMark;
	private String 	mobOnly;
	
	private String 	infoType;
	private String 	titleFontColor;
	private String 	titleFontSize;
	private String 	titleFontBgColor;
	private String 	infoRecNum;
	
	private String 	detailImgPath;
	
	private UserItemModel userItem;
	private List<InfoLinkModel> infoLinkList;
	
	public String getDetailImgPath()
	{
		if (detailImgPath != null){
			return detailImgPath;
		}else{
			return "";
		}
	}
	
	public String getInfoRecNum()
	{
		if (infoRecNum == null || infoRecNum == "" ) {
			return "50";
		} else {
			return infoRecNum;
		}	
	}
	
	public void addInfoLink(InfoLinkModel infoLink)
	{
		infoLinkList.add(infoLink);
	}
	
	public ConfigPageDetailModel() {
		this.userItem = new UserItemModel();
		this.infoLinkList = new ArrayList<InfoLinkModel>();
	}
}
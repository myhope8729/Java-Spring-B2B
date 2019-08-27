
package com.kpc.eos.model.userPage;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

import com.kpc.eos.core.model.CommonModel;

@Data
public class InfoLinkModel extends CommonModel {
	
	private String billId;
	private String hostUserId;
	private String billType;
	private String infoType;
	private String info;
	private String picType;
	private String createDate;
	
	private List<String> picPath;
	
	private Integer infoNum;
		
	public InfoLinkModel() {
		picPath = new ArrayList<String>();
	}
	
	public void addPicPath(String path)
	{
		picPath.add(path);
	}
}
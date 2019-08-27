
package com.kpc.eos.model.bizSetting;

import lombok.Data;

import org.apache.commons.lang.StringUtils;

import com.kpc.eos.core.Constants;
import com.kpc.eos.core.model.CommonModel;

@Data
public class ItemWidgetModel extends CommonModel {
	
	private static final long serialVersionUID = -2091762602779849188L;
	
	private String userId;
	private String itemId;
	private String lineNo;
	private String colNo;
	private String widgetType;
	private String widgetContent;
	private String linkUrl;
	private String state;
	private String colCount;
	private String colClass;
	
	public ItemWidgetModel() {
		
	}
	
	public String getColClass(){
		return Integer.toString(12 / Integer.parseInt(this.colCount));
	}
}
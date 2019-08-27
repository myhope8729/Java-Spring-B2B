
package com.kpc.eos.model.statistic;


import org.apache.commons.lang.StringUtils;

import lombok.Data;

import com.kpc.eos.core.util.CodeUtil;
import com.kpc.eos.model.common.DefaultSModel;

@Data
public class CommonStatisticSModel extends DefaultSModel {
	
	private static final long serialVersionUID = 5110546420068900605L;
	
	private String searchString1;
	private String searchString2;
	private String searchString3;
	private String searchString4;
	private String type1;
	private String type1Name;
	private String type2;
	private String type2Name;
	private String type3;
	private String type3Name;
	private String dateFrom;
	private String dateTo;
	
	
	public String getType1Name() {
		if(StringUtils.isEmpty(this.type1)) return "";
		else return CodeUtil.getCodeName(this.type1);
	}
	
	public String getType2Name() {
		if(StringUtils.isEmpty(this.type2)) return "";
		else return CodeUtil.getCodeName(this.type2);
	}	
	
	public String getType3Name() {
		if(StringUtils.isEmpty(this.type3)) return "";
		else return CodeUtil.getCodeName(this.type3);
	}		
	
	public CommonStatisticSModel() {
		
	}
}
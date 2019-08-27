
package com.kpc.eos.model.bill;

import java.util.List;

import lombok.Data;

import com.kpc.eos.model.common.DefaultSModel;

@Data
public class PriceDetailSModel extends DefaultSModel 
{
	
	private static final long serialVersionUID = 5375549911976998220L;
	
	private String 			category;
	private String 			createDate;
	private String 			catFieldName;
	private String 			userId;

}

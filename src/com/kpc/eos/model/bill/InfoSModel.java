/**
 * Filename		: InfoSModel.java
 * Description	:
 * 
 * 2017
 */
package com.kpc.eos.model.bill;

import com.kpc.eos.model.common.DefaultSModel;

import lombok.Data;

/**
 * Filename		: InfoSModel.java
 * Description	:
 * 2017
 * @author		: RKRK
 */
@Data
public class InfoSModel extends DefaultSModel
{
	private String fileType;
	
	private String billId;
	
	private String showmark;
	
}

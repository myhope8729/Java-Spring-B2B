/**
 * 
 */
package com.kpc.eos.model.common;

import lombok.Data;

import com.kpc.eos.core.model.CommonModel;

@Data
public class BreadcrumbModel extends CommonModel {
	private static final long serialVersionUID = -7551843764264266321L;
	
	//Breadcrumb name
	private String breadName;
	
	//Breadcrumb url
	private String breadLink;
	
	private Boolean isLast;
	
	public BreadcrumbModel() {}
	
	public BreadcrumbModel(String bName, String bLink) {
		this.breadName = bName;
		this.breadLink = bLink;
	}
	
	public BreadcrumbModel(String bName, String bLink, Boolean bLast) {
		this.breadName = bName;
		this.breadLink = bLink;
		this.isLast = bLast;
	}
}

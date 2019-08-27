/**
 * Filename		: UserItemCategoryMdoel.java
 * Description	:
 * 
 * 2018
 */
package com.kpc.eos.model.bizSetting;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

/**
 * Filename		: UserItemCategoryMdoel.java
 * Description	:
 * 2018
 * @author		: RKRK
 */
@Data
public class UserItemCategoryMdoel
{
	private String parentCatName;
	private String catName;
	private String cnt;
	
	List<UserItemCategoryMdoel> children = null;
	
	public UserItemCategoryMdoel(){}
	
	public UserItemCategoryMdoel(String catName, String cnt)
	{
		this.catName = catName;
		this.cnt = cnt;
	}
	
	public UserItemCategoryMdoel(String catName, Long cnt)
	{
		this.catName = catName;
		this.cnt = String.valueOf(cnt);
	}
	
	public void setChildren(List<UserItemCategoryMdoel> children)
	{
		this.children = children;
	}
	
	public void setEmptyChildren()
	{
		this.children = new ArrayList<UserItemCategoryMdoel>();
	}
	
	public void addChild(UserItemCategoryMdoel child)
	{
		this.children.add(child);
	}
	
	public boolean hasChildren()
	{
		return this.children != null && this.children.size() > 0; 
	}
	
	public Long getCntLong()
	{
		return cnt == null? 0L : Long.parseLong(cnt);
	}
}

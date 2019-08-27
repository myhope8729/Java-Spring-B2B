package com.kpc.eos.model.common;

import java.io.Serializable;

public class SysMsgModel implements Serializable{
	private static final long serialVersionUID = 4530658521304698244L;
	
	private int 	type;
	private String 	text;
	
	SysMsgModel()
	{
		
	}
	
	SysMsgModel(int type, String text)
	{
		this.type = type;
		this.text = text;
	}
	
	public int getType() 
	{
		return type;
	}
	
	public String getText()
	{
		return text;
	}
}

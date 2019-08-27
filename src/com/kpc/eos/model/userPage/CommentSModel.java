
package com.kpc.eos.model.userPage;

import lombok.Data;

import com.kpc.eos.core.model.CommonModel;
	

@Data
public class CommentSModel extends CommonModel {
	
	private String commentId;
	private String hostId;
	private String commentType;
	private String itemId;
		
	public CommentSModel() {
		
	}
	
}
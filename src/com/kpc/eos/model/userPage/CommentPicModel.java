
package com.kpc.eos.model.userPage;

import lombok.Data;

import com.kpc.eos.core.model.CommonModel;


@Data
public class CommentPicModel extends CommonModel {
	
	private String commentPicId;
	private String commentId;
	private String commentImgPath;
	
	public CommentPicModel() {
		
	}
}
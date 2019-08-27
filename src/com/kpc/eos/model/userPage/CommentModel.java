
package com.kpc.eos.model.userPage;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

import com.kpc.eos.core.model.CommonModel;
	

@Data
public class CommentModel extends CommonModel {
	
	private String commentId;
	private String hostId;
	private String commentType;
	private String itemId;
	
	private String commentName;
	private String commentText;
	
	private String upperId;
	private String upperName;
	private String upperText;
	
	private String createDate;
	private String createBy;
	private String updateDate;
	private String updateBy;
	
	private String avatarImgPath;
	private String state;
	
	private List<CommentPicModel> commentPicList;
	
	public CommentModel() {
		
	}
	
	public void addPic(CommentPicModel commentPic) {
		if (this.commentPicList == null)
			this.commentPicList = new ArrayList<CommentPicModel>();

		this.commentPicList.add(commentPic);
	}
}
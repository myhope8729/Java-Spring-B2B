
package com.kpc.eos.model.bizSetting;

import lombok.Data;

import org.apache.commons.lang.StringUtils;

import com.kpc.eos.core.Constants;
import com.kpc.eos.core.model.CommonModel;
	

@Data
public class ItemCommentModel extends CommonModel {
	
	private static final long serialVersionUID = 5001990741189866670L;
	
	private String commentId;
	private String userId;
	private String itemId;
	private String empNickname;
	private String commentType;
	private String commentContent;
	private String empImgfile;
	private String upperCommentId;
	private String state;
	
	public ItemCommentModel() {
		
	}
}
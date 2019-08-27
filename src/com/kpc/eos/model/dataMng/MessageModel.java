
package com.kpc.eos.model.dataMng;

import lombok.Data;

import com.kpc.eos.core.model.CommonModel;

/**
 * User
 * =================
 * Description : message model
 * Methods :
 */
public @Data class MessageModel extends CommonModel {

	private static final long serialVersionUID = -238240335038594350L;
	
	/**
	 * message ID
	 */
	private String msgId;
	private String msgGroupId;
	private String msgGroupName;
	/**
	 * message 제목
	 */
	private String msgTitle;
	/**
	 * message 내용
	 */
	private String msgContent;
	/**
	 * 생성자
	 */
	private String CreateBy;
	/**
	 * 수정자
	 */
	private String LastUpdateBy;
}
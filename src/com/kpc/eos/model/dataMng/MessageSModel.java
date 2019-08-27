
package com.kpc.eos.model.dataMng;

import lombok.Data;

import com.kpc.eos.core.model.BaseModel;
import com.kpc.eos.core.model.PagingModel;

/**
 * MessageSModel
 * =================
 * Description : message 검색 model
 * Methods :
 */
@Data
public class MessageSModel extends BaseModel {

	private static final long serialVersionUID = -2534849434441576271L;
	
	/**
	 * 검색류형(1: messageID, 2: 제목, 3:내용)
	 */
	private int searchType;
	/**
	 * 검색어
	 */
	private String searchString;

	private String msgGroupId;
	/**
	 * 페이징 객체
	 */
	private PagingModel page;

	public MessageSModel() {
		this.page = new PagingModel();
	}
}

package com.kpc.eos.model.dataMng;

import lombok.Data;

import com.kpc.eos.core.model.CommonModel;

/**
 * @author
 * 공지사항 리스트 Model
 */
@Data
public class NoticeModel extends CommonModel {

	private static final long serialVersionUID = -2875278039660684721L;


	/**
	 *  게시물 ID
	 */
	private String notifyNo;

	private String sortNo;
	private String topFlag;
	/**
	 * 게시물 제목
	 */
	private String notifyTitle;
	
	/**
	 * 게시물 내용
	 */
	private String notifyContent;
	
	/**
	 * 류형
	 */
	private String notifyType;
	
	/**
	 * 등록자
	 */
	private String createBy;	
	
	/**
	 * 등록일 
	 */
	private String createTime;
	
	/**
	 * 최종수정자
	 */
	private String lastUpdateBy;
	
	/**
	 * 최종수정일
	 */
	private String lastUpdateTime;	

	/*
	 * 게시 시작일
	 */
	private String notifyStartDate;	
	
	/**
	 * 게시 종료일 
	 */
	private String notifyExpDate;
	
	/*
	 * 공지사항 유지
	 */
    private String notifySaveYn;

	/**
	 * ROWNUM
	 */
	private Integer rowNo;
	/**
	 * 검색수
	 */
	private Integer readCount;
	/**
	 * 최신여부(7주일이내는 최신)
	 */
	
	private String noticeId;
	private String title;
	private String content;
	private String regTime;
	private String expTime;
	
	
	private Integer isNew = new Integer(0);
	
	public NoticeModel() {}
	
}

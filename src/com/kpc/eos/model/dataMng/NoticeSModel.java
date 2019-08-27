package com.kpc.eos.model.dataMng;

import lombok.Data;

import com.kpc.eos.core.model.BaseModel;
import com.kpc.eos.core.model.PagingModel;

@Data
public class NoticeSModel extends BaseModel {

	private static final long serialVersionUID = -3270809914813489206L;
	
	/**
	 * 기간류형 선택
	 */
	private String noticeId;
	
	/**
	 * 기간류형 선택
	 */
	private String dateType;
	
	/**
	 * 시작일
	 */
	private String date1;
	
	/**
	 * 종료일
	 */
	private String date2;
	
	/**
	 * 기간류형
	 */
	private String daySet;


	/**
	 * 류형
	 */
	private String noticeType;
	
	/**
	 * 등록자
	 */
	private String registerUser;
	
	/**
	 * 페이지
	 */
	private PagingModel page;
	
	
	/**
	 * 시스템류형
	 */
	private String systype;
	
	/**
	 * 검색조건
	 */
	private String searchType;
	
	/**
	 * 검색값
	 */
	private String searchString;
	
	/**
     * 메인에서 보여줄지 여부
     */
    private String target;
     
    /**
     * 탑 가저오기
     */
    private Integer topCnt;    
    private String userKindCd;
    
       
    /**
	 * 생성자 페이지 세팅
	 */
	public NoticeSModel() {
		this.page = new PagingModel();
	}
}

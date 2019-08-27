package com.kpc.eos.service.dataMng;

import java.util.List;

import com.kpc.eos.model.dataMng.NoticeModel;
import com.kpc.eos.model.dataMng.NoticeSModel;
import com.kpc.eos.model.system.MenuModel;


/**
 * NoticeService
 * =================
 * Description : 공지사항 관리 Service Interface
 * Methods : findNoticeList, getNotice, saveNotice
 */
public interface NoticeService {
	
	
	/**
	 * findNoticeList
	 * ===================
	 * 공지 리스트
	 * @param sc
	 * @return
	 * @throws Exception
	 */
	public List<NoticeModel> findNoticeList(NoticeSModel sc) throws Exception;
	

	/**
	 * findNoticeList
	 * ===================
	 * 현재 로출되는 공지 리스트
	 * @param sc
	 * @return
	 * @throws Exception
	 */
	public List<NoticeModel> findNoticeList2(NoticeSModel sc) throws Exception;
	
	/**
	 * getNotice
	 * ===================
	 * 공지사항 수정화면
	 * @param contents_id
	 * @return Notice
	 * @throws Exception
	 */
	public NoticeModel getNotice(String noticeId) throws Exception;

	/**

	/**
	 * saveNotice
	 * ===================
	 * 공지사항 등록/수정
	 * @param notice
	 * @throws Exception
	 */
	public void saveNotice(NoticeModel notice) throws Exception;
	
	/**
	 * deleteNotice
	 * ===================
	 * 공지사항 삭제
	 * @param ids
	 * @throws Exception
	 */
	public void deleteNotice(String[] noticeIds) throws Exception;
}

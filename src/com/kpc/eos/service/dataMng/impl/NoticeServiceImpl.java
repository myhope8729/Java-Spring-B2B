package com.kpc.eos.service.dataMng.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.transaction.annotation.Transactional;

import com.kpc.eos.core.Constants;
import com.kpc.eos.core.exception.ServiceException;
import com.kpc.eos.core.service.BaseService;
import com.kpc.eos.model.dataMng.NoticeModel;
import com.kpc.eos.model.dataMng.NoticeSModel;
import com.kpc.eos.service.dataMng.NoticeService;
 

/**
 * NoticeServiceImpl
 * =================
 * Description : 공지사항 관리 Service Interface 구현
 * Methods : deleteNotice, findNoticeList, getNotice, saveNotice
 */
@Transactional
public class NoticeServiceImpl extends BaseService implements NoticeService {

	/**
	 * findNoticeList
	 * ===================
	 * 공지 리스트
	 * @param sc
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<NoticeModel> findNoticeList(NoticeSModel sc) throws Exception {
		return baseDAO.queryForList("notice.findNoticeList", sc);
	}
	
	/**
	 * getNotice
	 * ===================
	 * 공지사항 상세보기
	 * @param sc
	 * @return Notice
	 * @throws Exception
	 */
	@Override
	public NoticeModel getNotice(String noticeId) throws Exception {
		NoticeModel nid = (NoticeModel) baseDAO.queryForObject("notice.getNotice", noticeId);
		return nid;
	}

	
	/**
	 * saveNotice
	 * ===================
	 * 공지사항 등록/상세보기
	 * @param notice
	 * @throws Exception
	 */
	public void saveNotice(NoticeModel notice) throws Exception {
		
		if (!notice.getNoticeId().equals(""))
			baseDAO.queryForList("notice.getNotice", notice); //상세보기
		else{
 			List<NoticeModel> obj1 = baseDAO.queryForList("notice.getLastNoticeId", null);
			NoticeModel lastNotice = obj1.get(0);
 			notice.setNoticeId(lastNotice.getNoticeId());
 			baseDAO.insert("notice.insertNotice", notice);
 			
		}
	}

	/**
	 * deleteNotice
	 * ===================
	 * 공지사항 삭제
	 * @param ids
	 * @throws Exception
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void deleteNotice(String[] noticeIds) throws Exception{
		/*
		//초기데이터는 삭제 안됨
		String disableMessage = "";
 		for (String noticeId : noticeIds) {
			Notice notice = getNotice(noticeId);
			if (Constants.SYSTEM_USER_NO.equalsIgnoreCase(notice.getCreateBy())) {
				if (disableMessage.length() > 0)
					disableMessage += ", ";
				disableMessage += notice.getNoticeId();
			}
		}
		if (disableMessage.length() > 0)
			throw new ServiceException("삭제가 실패하였습니다.\n시스템 기본message는 삭제할 수 없습니다.\n기본message:[" + disableMessage + "]");
        */
	
		Map data = new HashMap();
  		data.put("noticeIds", noticeIds);
		
		baseDAO.update("notice.deleteNotice", data);
	}

	@Override
	public List<NoticeModel> findNoticeList2(NoticeSModel sc) throws Exception {
		return baseDAO.queryForList("notice.findNoticeList2", sc);
	}
}


package com.kpc.eos.service.dataMng;

import java.util.List;

import com.kpc.eos.model.dataMng.MessageModel;
import com.kpc.eos.model.dataMng.MessageGroupModel;
import com.kpc.eos.model.dataMng.MessageSModel;

/**
 * MessageService
 * =================
 * Description : message service interface
 * Methods :
 */
public interface MessageService {
	
	/**
	 * findMessageList
	 * ===================
	 * message 목록 검색
	 * @param sc
	 * @return
	 * @throws Exception
	 */
	public List<MessageModel> findMessageList(MessageSModel sc) throws Exception;
	public List<MessageModel> findMessageListFromMsgGroupId(MessageSModel sc) throws Exception;
	/**
	 * getMessage
	 * ===================
	 * message 검색
	 * @param msgId
	 * @return
	 * @throws Exception
	 */
	public MessageModel getMessage(String msgId) throws Exception;
	
	/**
	 * saveMessage
	 * ===================
	 * message 등록/수정
	 * @param msg
	 * @throws Exception
	 */
	public void saveMessage(MessageModel msg) throws Exception;
	
	/**
	 * deleteMessage
	 * ===================
	 * message 삭제
	 * @param deleteMsgIds
	 * @param actorNo 수정자
	 * @throws Exception
	 */
	public void deleteMessage(String[] deleteMsgIds, String actorNo) throws Exception;
	
	/**
	 * findMessageGroupList
	 * ===================
	 * message Group 목록 검색
	 * @param sc
	 * @return
	 * @throws Exception
	 */
	public List<MessageGroupModel> findMessageGroupList(MessageSModel sc) throws Exception;
	
	/**
	 * getMessageGroup
	 * ===================
	 * message Group 검색
	 * @param msgGroupId
	 * @return
	 * @throws Exception
	 */
	public MessageGroupModel getMessageGroup(String msgGroupId) throws Exception;
	
	/**
	 * saveMessageGroup
	 * ===================
	 * message Group 등록/수정
	 * @param msgGroup
	 * @throws Exception
	 */
	public void saveMessageGroup(MessageGroupModel msgGroup) throws Exception;
	
	/**
	 * deleteMessage
	 * ===================
	 * message Group 삭제
	 * @param deleteMsgIds
	 * @param actorNo 수정자
	 * @throws Exception
	 */
	public void deleteMessageGroup(String[] deleteMsgGroupIds, String actorNo) throws Exception;
}

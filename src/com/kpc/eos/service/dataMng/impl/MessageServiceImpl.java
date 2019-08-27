
package com.kpc.eos.service.dataMng.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import com.kpc.eos.core.Constants;
import com.kpc.eos.core.exception.ServiceException;
import com.kpc.eos.core.service.BaseService;
import com.kpc.eos.model.dataMng.MessageModel;
import com.kpc.eos.model.dataMng.MessageGroupModel;
import com.kpc.eos.model.dataMng.MessageSModel;
import com.kpc.eos.service.dataMng.MessageService;

/**
 * MessageServiceImpl
 * =================
 * Description : message service 구현 객체
 * Methods :
 */
@Transactional
public class MessageServiceImpl extends BaseService implements MessageService {

	@SuppressWarnings("unchecked")
	@Override
	public List<MessageModel> findMessageList(MessageSModel sc) throws Exception {
		List<MessageModel> messageList = baseDAO.queryForList("message.findMessageList", sc);
		return messageList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MessageModel> findMessageListFromMsgGroupId(MessageSModel sc) throws Exception {
		List<MessageModel> messageList = baseDAO.queryForList("message.findMessageListFromMsgGroupId", sc);
		return messageList;
	}
	
	@Override
	public MessageModel getMessage(String msgId) throws Exception {
		MessageModel msg =  (MessageModel) baseDAO.queryForObject("message.getMessage", msgId);
		return msg;
	}

	@Override
	public void saveMessage(MessageModel msg) throws Exception {
		//message
		MessageModel already = getMessage(msg.getMsgId());
		if (already == null)
		{
			baseDAO.insert("message.insertMessage", msg);
		}
		else
		{
			baseDAO.update("message.updateMessage", msg);
		}
	}

	@Override
	public void deleteMessage(String[] deleteMsgIds, String actorNo) throws Exception {
		
		//초기데이터는 삭제 안됨
		String disableMessage = "";
		for (String msgId : deleteMsgIds) {
			MessageModel msg = getMessage(msgId);
			if (Constants.SYSTEM_USER_NO.equalsIgnoreCase(msg.getCreateBy())) {
				if (disableMessage.length() > 0)
					disableMessage += ", ";
				disableMessage += msg.getMsgId();
			}
		}
		if (disableMessage.length() > 0)
			throw new ServiceException("삭제가 실패하였습니다.\n시스템 기본message는 삭제할 수 없습니다.\n기본message:[" + disableMessage + "]");

		Map data = new HashMap();
		data.put("lastUpdateBy", 	actorNo);
		data.put("msgIds", 		deleteMsgIds);
		
		baseDAO.update("message.deleteMessage", data);
	}	
	
	@Override
	public List<MessageGroupModel> findMessageGroupList(MessageSModel sc) throws Exception {
		List<MessageGroupModel> messageGroupList = baseDAO.queryForList("message.findMessageGroupList", sc);
		return messageGroupList;
	}

	@Override
	public MessageGroupModel getMessageGroup(String msgGroupId) throws Exception {
		MessageGroupModel msgGroup =  (MessageGroupModel) baseDAO.queryForObject("message.getMessageGroup", msgGroupId);
		return msgGroup;
	}

	@Override
	public void saveMessageGroup(MessageGroupModel msgGroup) throws Exception {
		//messageGroup
		MessageGroupModel already = getMessageGroup(msgGroup.getMsgGroupId());
		if (already == null)
		{
			baseDAO.insert("message.insertMessageGroup", msgGroup);
		}
		else
		{
			baseDAO.update("message.updateMessageGroup", msgGroup);
		}
	}

	@Override
	public void deleteMessageGroup(String[] deleteMsgGroupIds, String actorNo) throws Exception {
		
		//초기데이터는 삭제 안됨
		String disableMessage = "";
		for (String msgGroupId : deleteMsgGroupIds) {
			MessageGroupModel msgGroup = getMessageGroup(msgGroupId);
			if (Constants.SYSTEM_USER_NO.equalsIgnoreCase(msgGroup.getCreateBy())) {
				if (disableMessage.length() > 0)
					disableMessage += ", ";
				disableMessage += msgGroup.getMsgGroupId();
			}
		}
		if (disableMessage.length() > 0)
			throw new ServiceException("삭제가 실패하였습니다.\n시스템 기본message는 삭제할 수 없습니다.\n기본message:[" + disableMessage + "]");

		Map data = new HashMap();
		data.put("lastUpdateBy", 	actorNo);
		data.put("msgIds", 		deleteMsgGroupIds);
		
		baseDAO.update("message.deleteMessageGroup", data);
	}	
}

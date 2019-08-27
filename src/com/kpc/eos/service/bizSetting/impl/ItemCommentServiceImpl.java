
package com.kpc.eos.service.bizSetting.impl;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.kpc.eos.core.service.BaseService;
import com.kpc.eos.model.bizSetting.ItemCommentModel;
import com.kpc.eos.service.bizSetting.ItemCommentService;

@Transactional
public class ItemCommentServiceImpl extends BaseService implements ItemCommentService {
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ItemCommentModel> getUserItemCommentList(ItemCommentModel sc) throws Exception {
		List<ItemCommentModel> itemCommentList = baseDAO.queryForList("itemComment.getUserItemCommentList", sc);
		return itemCommentList;
	}

}

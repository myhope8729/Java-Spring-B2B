
package com.kpc.eos.service.bizSetting;

import java.util.List;

import com.kpc.eos.model.bizSetting.ItemCommentModel;

public interface ItemCommentService {
	
	public List<ItemCommentModel> getUserItemCommentList(ItemCommentModel sc) throws Exception;
	
}

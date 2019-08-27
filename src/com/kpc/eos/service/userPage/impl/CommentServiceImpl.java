package com.kpc.eos.service.userPage.impl;

import java.util.List;

import com.kpc.eos.core.service.BaseService;
import com.kpc.eos.model.userPage.CommentModel;
import com.kpc.eos.model.userPage.CommentPicModel;
import com.kpc.eos.model.userPage.CommentSModel;
import com.kpc.eos.service.userPage.CommentService;

public class CommentServiceImpl extends BaseService implements CommentService {
	@Override
	public Integer getCommentCount(CommentSModel cs) throws Exception {
		Integer count = (Integer)baseDAO.queryForObject("comment.getCommentCount", cs);
		return count;
	}
	@Override
	public String getLastCommentId() throws Exception {
		return (String)baseDAO.queryForObject("comment.getLastCommentId", null);
	}
	@Override
	public String getLastCommentPicId() throws Exception {
		return (String)baseDAO.queryForObject("comment.getLastCommentPicId", null);
	}
	@Override
	public void	insertComment(CommentModel comment) throws Exception {
		baseDAO.insert("comment.insertComment", comment);
	}
	@Override
	public void	insertCommentPic(CommentPicModel commentPic) throws Exception {
		baseDAO.insert("comment.insertCommentPic", commentPic);
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<CommentModel> getCommentList(CommentSModel cs) throws Exception {
		return (List<CommentModel>)baseDAO.queryForList("comment.getComment", cs);
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<CommentPicModel> getCommentPicList(String commentId) throws Exception {
		return (List<CommentPicModel>)baseDAO.queryForList("comment.getCommentPic", commentId);
	}
	@Override
	public CommentModel getCommentById(String commentId) throws Exception {
		return (CommentModel)baseDAO.queryForObject("comment.getCommentById", commentId);
	}
	@Override
	public void deleteComment(String commentId) throws Exception {
		baseDAO.delete("comment.deleteComment", commentId);
	}
	@Override
	public void deleteCommentPic(String commentId) throws Exception {
		baseDAO.delete("comment.deleteCommentPic", commentId);
	}
}

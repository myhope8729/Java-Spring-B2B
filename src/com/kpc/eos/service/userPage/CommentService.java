package com.kpc.eos.service.userPage;

import java.util.List;

import com.kpc.eos.model.userPage.CommentModel;
import com.kpc.eos.model.userPage.CommentPicModel;
import com.kpc.eos.model.userPage.CommentSModel;

public interface CommentService {
	
	public Integer 	getCommentCount(CommentSModel cs) throws Exception;
	public String 	getLastCommentId() throws Exception;
	public String	getLastCommentPicId() throws Exception;
	
	public List<CommentModel> getCommentList(CommentSModel cs) throws Exception;
	public List<CommentPicModel> getCommentPicList(String commentId) throws Exception;
	public CommentModel getCommentById(String commentId) throws Exception;
	
	public void	insertComment(CommentModel comment) throws Exception;
	public void	insertCommentPic(CommentPicModel commentPic) throws Exception;
	
	public void deleteComment(String commentId) throws Exception;
	public void deleteCommentPic(String commentId) throws Exception;
}

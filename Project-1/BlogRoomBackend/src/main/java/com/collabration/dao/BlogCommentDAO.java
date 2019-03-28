package com.collabration.dao;

import java.util.List;

import com.collabration.model.BlogComment;

public interface BlogCommentDAO {

	public void addComment(BlogComment blogComment);
	List<BlogComment> getListOfComment(int blogId);
}

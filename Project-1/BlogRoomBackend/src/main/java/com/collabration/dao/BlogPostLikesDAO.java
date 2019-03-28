package com.collabration.dao;

import com.collabration.model.BlogPost;
import com.collabration.model.BlogPostLikes;

public interface BlogPostLikesDAO {

	public BlogPostLikes hasUserLikedPost(int blogId,String email);
	public BlogPost updateLikes(int blogId,String email);
}

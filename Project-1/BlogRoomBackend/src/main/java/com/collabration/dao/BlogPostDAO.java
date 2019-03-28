package com.collabration.dao;

import java.util.List;

import com.collabration.model.BlogPost;

public interface BlogPostDAO {

	public void addBlog(BlogPost blogPost);
	public List<BlogPost> getListOfBlogsApproved();
	public BlogPost getBlogById(int blogId);
	public List<BlogPost> getListOfBlogsWaitingForApproval();
	public void updateBlog(BlogPost blogPost);     //for updating the approval statuso of Blog which is Approved by ADMIN
	public void deleteBlog(BlogPost blogPost);     //for deleting particular Blog which is Rejected by ADMIN
	
}

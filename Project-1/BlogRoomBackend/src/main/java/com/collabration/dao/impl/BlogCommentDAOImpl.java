package com.collabration.dao.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.collabration.dao.BlogCommentDAO;
import com.collabration.model.BlogComment;

@Repository("blogCommentDAO")
@Transactional
public class BlogCommentDAOImpl implements BlogCommentDAO {

	@Autowired
	SessionFactory sessionFactory;
	
	@Override
	public void addComment(BlogComment blogComment) {
		
		try
		{
			Session session = sessionFactory.getCurrentSession();
			session.save(blogComment);
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		
	}

	@Override
	public List<BlogComment> getListOfComment(int blogId) {
		
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("from BlogComment where blogPost.blogId=?");
		query.setInteger(0,blogId);
		List<BlogComment> listOfComments = query.list();
		
		return listOfComments;
	}

}

package com.collabration.dao.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.collabration.dao.BlogPostDAO;
import com.collabration.model.BlogPost;

import oracle.net.aso.b;

@Repository("blogPostDAO")
@Transactional
public class BlogPostDAOImpl implements BlogPostDAO {

	@Autowired
	SessionFactory sessionFactory;
	
	@Override
	public void addBlog(BlogPost blogPost) {
		
		try
		{
			Session session = sessionFactory.getCurrentSession();
			session.save(blogPost);
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		
	}

	@Override
	public List<BlogPost> getListOfBlogsApproved() {
		
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("from BlogPost where approvalStatus=true");
		List<BlogPost> listOfBlogsApproved = query.list();
		return listOfBlogsApproved;
	}

	@Override
	public BlogPost getBlogById(int blogId) {
		
		Session session = sessionFactory.getCurrentSession();
		BlogPost blogPost =(BlogPost) session.get(BlogPost.class,blogId);
		return blogPost;
	}

	@Override
	public List<BlogPost> getListOfBlogsWaitingForApproval() {
		
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("from BlogPost where approvalStatus=false");
		List<BlogPost> listOfBlogsWaitingForApproval = query.list();
		return listOfBlogsWaitingForApproval;
	}

	@Override
	public void updateBlog(BlogPost blogPost) {
		
		try
		{
			Session session = sessionFactory.getCurrentSession();
			session.update(blogPost);
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		
	}

	@Override
	public void deleteBlog(BlogPost blogPost) {
		
		try
		{
			Session session = sessionFactory.getCurrentSession();
			session.delete(blogPost);
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		
	}

/*	@Override
	public void incLikes(BlogPost blogPost) {
		
		try
		{
			Session session = sessionFactory.getCurrentSession();
			session.update(blogPost);
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}

	@Override
	public void disLikes(BlogPost blogPost) {
		
		try
		{
			Session session = sessionFactory.getCurrentSession();
			session.update(blogPost);
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}*/

}

package com.collabration.dao.impl;

import javax.transaction.Transactional;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.collabration.dao.BlogPostLikesDAO;
import com.collabration.model.BlogPost;
import com.collabration.model.BlogPostLikes;
import com.collabration.model.User;

@Repository("blogPostLikesDAO")
@Transactional
public class BlogPostLikesDAOImpl implements BlogPostLikesDAO {

	@Autowired
	SessionFactory sessionFactory;
	
	@Override
	public BlogPostLikes hasUserLikedPost(int blogId, String email) {

		Session session=sessionFactory.getCurrentSession();
		
		Query query=session.createQuery("from BlogPostLikes where blogPost.blogId=? and user.email=?");
		query.setInteger(0,blogId);
		query.setString(1, email);
		BlogPostLikes blogPostLikes = (BlogPostLikes)query.uniqueResult();
		return blogPostLikes;
	}

	@Override
	public BlogPost updateLikes(int blogId, String email) {
		
		Session session = sessionFactory.getCurrentSession();

		BlogPostLikes blogPostLikes=hasUserLikedPost(blogId, email);

		BlogPost blogPost=(BlogPost)session.get(BlogPost.class, blogId);

		if(blogPostLikes==null)
		{
			BlogPostLikes likes=new BlogPostLikes();//id, blogpost_id, user_email
			User user=(User)session.get(User.class, email);
			likes.setBlogPost(blogPost);
			likes.setUser(user);
			session.save(likes);//insert into blogpostlikes 
			blogPost.setLikes(blogPost.getLikes() + 1); //increment the likes
			session.update(blogPost);//update blogpost set likes=likes +1 where id=?
		}
		else
		{
			session.delete(blogPostLikes);//delete from blogPostLikes where likesid=?
			blogPost.setLikes(blogPost.getLikes() - 1);//decrement the number of likes
			session.update(blogPost);//update blogpost set likes = likes -1 where id=?
		}
		return blogPost;
	}

}

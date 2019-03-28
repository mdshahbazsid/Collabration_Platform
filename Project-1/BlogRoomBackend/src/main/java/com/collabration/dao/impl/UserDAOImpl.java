package com.collabration.dao.impl;


import javax.transaction.Transactional;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.collabration.dao.UserDAO;
import com.collabration.model.User;

@Repository("userDAO")
@Transactional
public class UserDAOImpl implements UserDAO {

	@Autowired
	SessionFactory sessionfactory;
	
	@Override
	public void registerUser(User user) {
		
		try 
		{
			Session session = sessionfactory.getCurrentSession();
			session.save(user);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	@Override
	public boolean isEmailUnique(String email) {
		
		Session session = sessionfactory.getCurrentSession();
		Query query = session.createQuery("from User where email=?");
		query.setString(0,email);
		User user = (User)query.uniqueResult();
		if(user!=null)
			return false;
		else
			return true;
	}
	
	@Override
	public User loginUser(User user) {

		Session session = sessionfactory.getCurrentSession();
		Query query = session.createQuery("from User where email=? and password=?");
		query.setString(0,user.getEmail());
		query.setString(1,user.getPassword());
		
		return (User)query.uniqueResult();
		
	}

	@Override
	public void updateUser(User user) {
		Session session = sessionfactory.getCurrentSession();
		session.update(user);
	}

	@Override
	public User getUser(String email) {
		
		Session session = sessionfactory.getCurrentSession();
		User user = session.get(User.class,email);
		return user;
	}
}

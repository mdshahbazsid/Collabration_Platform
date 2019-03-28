package com.collabration.dao.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.collabration.dao.NotificationDAO;
import com.collabration.model.Notification;

@Repository("notificationDAO")
@Transactional
public class NotificationDAOImpl implements NotificationDAO {

	@Autowired
	SessionFactory sessionFactory;
	
	@Override
	public void addNotification(Notification notification) {
		
		try
		{
			Session session = sessionFactory.getCurrentSession();
			session.save(notification);
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}

	@Override
	public void updateNotification(int id) {
		
		Session session = sessionFactory.getCurrentSession();
		Notification notification = (Notification) session.get(Notification.class,id);
		notification.setViewed(true);
		session.update(notification);
		
	}

	@Override
	public Notification getNotification(int id) {
		
		Session session = sessionFactory.getCurrentSession();
		Notification notification = (Notification) session.get(Notification.class,id);
		return notification;
	}

	@Override
	public List<Notification> listOfNotificationsWhichAreNotViewed(String email) {
		
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("from Notification where viewed=0 and email=:e");
		query.setString("e",email);
		return query.list();
	}

}

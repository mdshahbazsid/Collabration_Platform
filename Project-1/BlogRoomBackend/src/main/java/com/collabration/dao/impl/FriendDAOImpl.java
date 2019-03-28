package com.collabration.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.collabration.dao.FriendDAO;
import com.collabration.model.Friend;
import com.collabration.model.User;

@Repository("friendDAO")
@Transactional
public class FriendDAOImpl implements FriendDAO {

	@Autowired
	SessionFactory sessionFactory;
	
	@Override
	public List<User> listOfSuggestedUser(String email) {
		
		Session session = sessionFactory.getCurrentSession();
		String query = "select * from user_table where email in(\r\n" + 
				"select email from user_table where email!=?\r\n" + 
				"minus\r\n" + 
				"(select fromId_email from friend where toId_email=?\r\n" + 
				"union\r\n" + 
				"select toId_email from friend where fromId_email=?))";
		
		SQLQuery sqlQuery = session.createSQLQuery(query);
		sqlQuery.setString(0,email);
		sqlQuery.setString(1,email);
		sqlQuery.setString(2,email);

		sqlQuery.addEntity(User.class); //convert record to user object

		return sqlQuery.list();
	}

	@Override
	public void addFriendRequest(Friend friend) {
		
		Session session = sessionFactory.getCurrentSession();
		session.save(friend);
	}

	@Override
	public List<Friend> getPendingRequest(String email) {
		
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("from Friend where toId=? and status=?");
		query.setString(0,email);
		query.setCharacter(1,'P');
		
		List<Friend> listOfPendingRequest  = query.list();		
		return listOfPendingRequest;
	}

	@Override
	public void acceptRequest(Friend friend) {

		Session session = sessionFactory.getCurrentSession();
		session.update(friend);
		
	}

	@Override
	public void deleteRequest(Friend friend) {
		
		Session session = sessionFactory.getCurrentSession();
		session.delete(friend);
	}

	@Override
	public List<User> getFriends(String email) {
		
		Session session = sessionFactory.getCurrentSession();
		
		String query ="select * from user_table where email\r\n" + 
				"in\r\n" + 
				"(select fromId_email from friend where toId_email=? and status='A'\r\n" + 
				"union\r\n" + 
				"select toId_email from friend where fromId_email=? and status='A')";
		SQLQuery sqlQuery = session.createSQLQuery(query);
		sqlQuery.setString(0, email);
		sqlQuery.setString(1, email);
		sqlQuery.addEntity(User.class);
		
		List<User> friends=sqlQuery.list();
		
		return friends;
	}

}

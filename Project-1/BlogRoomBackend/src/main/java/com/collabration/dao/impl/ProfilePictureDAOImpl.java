package com.collabration.dao.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.collabration.dao.ProfilePictureDAO;
import com.collabration.model.ProfilePicture;

@Repository("profilePictureDAO")
@Transactional
public class ProfilePictureDAOImpl implements ProfilePictureDAO {

	@Autowired
	SessionFactory sessionFactory;
	
	@Override
	public void uploadProfilePicture(ProfilePicture profilePicture) {
		Session session = sessionFactory.getCurrentSession();
		session.saveOrUpdate(profilePicture);
		
	}

	@Override
	public ProfilePicture getProfilePicture(String email) {
		
		Session session = sessionFactory.getCurrentSession();
		ProfilePicture pp =(ProfilePicture) session.get(ProfilePicture.class,email);
		return pp;
	}

	
}

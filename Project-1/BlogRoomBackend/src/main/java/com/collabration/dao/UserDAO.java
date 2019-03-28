package com.collabration.dao;

import com.collabration.model.User;

public interface UserDAO {

	public void registerUser(User user);
	boolean isEmailUnique(String email);
	
	public User loginUser(User user);
	public void updateUser(User user);
	
	public User getUser(String email);
}

package com.collabration.dao;

import java.util.List;

import com.collabration.model.Friend;
import com.collabration.model.User;

public interface FriendDAO {

	public List<User> listOfSuggestedUser(String email);
	public void addFriendRequest(Friend friend);
	public List<Friend> getPendingRequest(String email);
	public void acceptRequest(Friend friend);
	public void deleteRequest(Friend friend);
	public List<User> getFriends(String email);
}

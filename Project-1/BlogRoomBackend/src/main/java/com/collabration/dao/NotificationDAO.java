package com.collabration.dao;

import java.util.List;

import com.collabration.model.Notification;

public interface NotificationDAO {

	public void addNotification(Notification notification);
	public void updateNotification(int id);
	public Notification getNotification(int id);
	public List<Notification> listOfNotificationsWhichAreNotViewed(String email);
}

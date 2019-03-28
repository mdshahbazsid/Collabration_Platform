package com.collabration.controllers;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.collabration.dao.NotificationDAO;
import com.collabration.model.ErrorClazz;
import com.collabration.model.Notification;

@RestController
public class NotificationController {

	@Autowired
	NotificationDAO notificationDAO;
	
	@RequestMapping(value="/notifications",method=RequestMethod.GET)
	public ResponseEntity<?> getNotificationsWhichAreNotViewed(HttpSession session){
		
		String email = (String) session.getAttribute("loggedInUser");
		if(email == null) 
		{
			ErrorClazz errorClazz = new ErrorClazz(4,"Unauthorized Access...please login");
			return new ResponseEntity<ErrorClazz>(errorClazz,HttpStatus.UNAUTHORIZED);
		}

		List<Notification> listOfNotificationsWhichAreNotViewed = notificationDAO.listOfNotificationsWhichAreNotViewed(email);
		return new ResponseEntity<List<Notification>>(listOfNotificationsWhichAreNotViewed,HttpStatus.OK);
	}
	
	@RequestMapping(value="/getnotification/{id}",method=RequestMethod.GET)
	public ResponseEntity<?> getNotificationById(@PathVariable("id") int id,HttpSession session){
		
		String email = (String) session.getAttribute("loggedInUser");
		if(email == null) 
		{
			ErrorClazz errorClazz = new ErrorClazz(4,"Unauthorized Access...please login");
			return new ResponseEntity<ErrorClazz>(errorClazz,HttpStatus.UNAUTHORIZED);
		}
		Notification notification = notificationDAO.getNotification(id);
		return new ResponseEntity<Notification>(notification,HttpStatus.OK);
	}
	
	@RequestMapping(value="/updatenotification/{id}",method=RequestMethod.GET)
	public ResponseEntity<?> updateNotificationById(@PathVariable("id") int id,HttpSession session){
		
		String email = (String) session.getAttribute("loggedInUser");
		if(email == null) 
		{
			ErrorClazz errorClazz = new ErrorClazz(4,"Unauthorized Access...please login");
			return new ResponseEntity<ErrorClazz>(errorClazz,HttpStatus.UNAUTHORIZED);
		}
		notificationDAO.updateNotification(id);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
}

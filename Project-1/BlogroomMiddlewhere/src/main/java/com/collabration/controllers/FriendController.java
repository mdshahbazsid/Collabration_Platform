package com.collabration.controllers;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.hibernate.SQLQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.collabration.dao.FriendDAO;
import com.collabration.dao.UserDAO;
import com.collabration.model.ErrorClazz;
import com.collabration.model.Friend;
import com.collabration.model.User;

@RestController
public class FriendController {

	@Autowired
	FriendDAO friendDAO;
	
	@Autowired
	UserDAO userDAO;
	
	@RequestMapping(value="/suggestedusers",method=RequestMethod.GET)
	public ResponseEntity<?> getSuggestedUsers(HttpSession session){
		
		String email = (String) session.getAttribute("loggedInUser");
		if(email == null) 
		{
			ErrorClazz errorClazz = new ErrorClazz(4,"Unauthorized Access...please login");
			return new ResponseEntity<ErrorClazz>(errorClazz,HttpStatus.UNAUTHORIZED);
		}
		List<User> listOfSuggestedUser = friendDAO.listOfSuggestedUser(email);
		return new ResponseEntity<List<User>>(listOfSuggestedUser,HttpStatus.OK);
	}
	
	@RequestMapping(value="/friendrequest",method=RequestMethod.POST)
	public ResponseEntity<?> friendRequest(@RequestBody User userToId,HttpSession session){
		
		String email = (String) session.getAttribute("loggedInUser");
		if(email == null) 
		{
			ErrorClazz errorClazz = new ErrorClazz(4,"Unauthorized Access...please login");
			return new ResponseEntity<ErrorClazz>(errorClazz,HttpStatus.UNAUTHORIZED);
		}
		
		User fromId = userDAO.getUser(email);
		
		Friend friend = new Friend();
		friend.setFromId(fromId);
		friend.setToId(userToId);
		friend.setStatus('P');
		
		friendDAO.addFriendRequest(friend);
		return new ResponseEntity<Friend>(friend,HttpStatus.OK);
	}
	
	@RequestMapping(value="/pendingrequest",method=RequestMethod.GET)
	public ResponseEntity<?> getPendingRequest(HttpSession session){
		
		String email = (String) session.getAttribute("loggedInUser");
		if(email == null) 
		{
			ErrorClazz errorClazz = new ErrorClazz(4,"Unauthorized Access...please login");
			return new ResponseEntity<ErrorClazz>(errorClazz,HttpStatus.UNAUTHORIZED);
		}
		
		List<Friend> listOfPendingRequest = friendDAO.getPendingRequest(email);
		return new ResponseEntity<List<Friend>>(listOfPendingRequest,HttpStatus.OK);
	}
	
	@RequestMapping(value="/acceptrequest",method=RequestMethod.PUT)
	public ResponseEntity<?> acceptPendingRequest(@RequestBody Friend friend,HttpSession session){
		
		String email = (String) session.getAttribute("loggedInUser");
		if(email == null) 
		{
			ErrorClazz errorClazz = new ErrorClazz(4,"Unauthorized Access...please login");
			return new ResponseEntity<ErrorClazz>(errorClazz,HttpStatus.UNAUTHORIZED);
		}
		
		friend.setStatus('A');
		friendDAO.acceptRequest(friend);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
	
	@RequestMapping(value="/deleterequest",method=RequestMethod.PUT)
	public ResponseEntity<?> deletePendingRequest(@RequestBody Friend friend,HttpSession session){
		
		String email = (String) session.getAttribute("loggedInUser");
		if(email == null) 
		{
			ErrorClazz errorClazz = new ErrorClazz(4,"Unauthorized Access...please login");
			return new ResponseEntity<ErrorClazz>(errorClazz,HttpStatus.UNAUTHORIZED);
		}
		
		friendDAO.deleteRequest(friend);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
	
	@RequestMapping(value="/friends",method=RequestMethod.GET)
	public ResponseEntity<?> getfriends(HttpSession session){
		
		String email = (String) session.getAttribute("loggedInUser");
		if(email == null) 
		{
			ErrorClazz errorClazz = new ErrorClazz(4,"Unauthorized Access...please login");
			return new ResponseEntity<ErrorClazz>(errorClazz,HttpStatus.UNAUTHORIZED);
		}
		
		List<User> listOfFriends = friendDAO.getFriends(email);
		return new ResponseEntity<List<User>>(listOfFriends,HttpStatus.OK);
	}
}

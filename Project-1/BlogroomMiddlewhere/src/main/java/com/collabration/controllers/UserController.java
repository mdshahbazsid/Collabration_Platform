package com.collabration.controllers;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.collabration.dao.UserDAO;
import com.collabration.model.ErrorClazz;
import com.collabration.model.User;

@RestController
public class UserController {

	@Autowired
	private UserDAO userDAO;
	
	@RequestMapping(value="/register",method=RequestMethod.POST)
	public ResponseEntity<?> userRegistration(@RequestBody User user){

		try
		{
			if(!userDAO.isEmailUnique(user.getEmail()))
			{
				ErrorClazz errorClazz = new ErrorClazz(2,"Email already exist...please enter new Email Id");	
				return new ResponseEntity<ErrorClazz>(errorClazz,HttpStatus.CONFLICT);
			}
			userDAO.registerUser(user);
			return new ResponseEntity<User>(user,HttpStatus.CREATED);
		} 
		catch (Exception e) 
		{
			ErrorClazz errorClazz = new ErrorClazz(1,"User Details not inserted...something went wrong");
			return new ResponseEntity<ErrorClazz>(errorClazz,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	@RequestMapping(value="/login",method=RequestMethod.PUT)
	public ResponseEntity<?> userLogin(@RequestBody User user,HttpSession session){
		User validUser = userDAO.loginUser(user);
		if(validUser==null) 
		{
			ErrorClazz errorClazz = new ErrorClazz(4,"Invalid Credentials..!");
			return new ResponseEntity<ErrorClazz>(errorClazz,HttpStatus.UNAUTHORIZED);
		}
		else  
		{
			validUser.setOnline(true);
			userDAO.updateUser(validUser);
			session.setAttribute("loggedInUser",validUser.getEmail());
			return new ResponseEntity<User>(validUser,HttpStatus.OK);
		}
	}
	
	@RequestMapping(value="/logout",method=RequestMethod.PUT)
	public ResponseEntity<?> userLogout(HttpSession session){
		
		String email = (String) session.getAttribute("loggedInUser");
		if(email == null) 
		{
			ErrorClazz errorClazz = new ErrorClazz(4,"Unauthorized Access...please login");
			return new ResponseEntity<ErrorClazz>(errorClazz,HttpStatus.UNAUTHORIZED);
		}
		User user = userDAO.getUser(email);
		user.setOnline(false);
		userDAO.updateUser(user);
		session.removeAttribute("loggedInUser");
		session.invalidate();
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
	
	@RequestMapping(value="/updateprofile",method=RequestMethod.PUT)
	public ResponseEntity<?> updateUser(@RequestBody User user,HttpSession session){
		
		String email = (String) session.getAttribute("loggedInUser");
		if(email == null) 
		{
			ErrorClazz errorClazz = new ErrorClazz(4,"Unauthorized Access...please login");
			return new ResponseEntity<ErrorClazz>(errorClazz,HttpStatus.UNAUTHORIZED);
		}
		try
		{
			userDAO.updateUser(user);
		} 
		catch (Exception e) 
		{
			ErrorClazz errorClazz = new ErrorClazz(5,"UserDetails Not updated Successfully....");
			return new ResponseEntity<ErrorClazz>(errorClazz,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return new ResponseEntity<User>(user,HttpStatus.OK);
	}
}

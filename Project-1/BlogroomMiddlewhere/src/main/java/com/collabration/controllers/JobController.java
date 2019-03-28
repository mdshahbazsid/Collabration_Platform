package com.collabration.controllers;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.collabration.dao.JobDAO;
import com.collabration.dao.UserDAO;
import com.collabration.model.ErrorClazz;
import com.collabration.model.Job;
import com.collabration.model.User;

@RestController
public class JobController {

	@Autowired
	private JobDAO jobDAO;
	
	@Autowired
	private UserDAO userDAO;
	
	@RequestMapping(value="/addjob",method=RequestMethod.POST)
	public ResponseEntity<?> addJob(@RequestBody Job job,HttpSession session){
		
		String email = (String) session.getAttribute("loggedInUser");
		if(email == null) 
		{
			ErrorClazz errorClazz = new ErrorClazz(4,"Unauthorized Access...please login");
			return new ResponseEntity<ErrorClazz>(errorClazz,HttpStatus.UNAUTHORIZED);
		}
		User user = userDAO.getUser(email);
		if(!user.getRole().equals("ADMIN")) 
		{
			ErrorClazz errorClazz = new ErrorClazz(5,"Access Denied...You are not Authorized to post a Job");
			return new ResponseEntity<ErrorClazz>(errorClazz,HttpStatus.UNAUTHORIZED);
		}
		try
		{
			job.setPostedOn(new Date());
			jobDAO.addJob(job);
		} 
		catch (Exception e) 
		{
			ErrorClazz errorClazz = new ErrorClazz(6,"Job details not inserted...something went wrong");
			return new ResponseEntity<ErrorClazz>(errorClazz,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<Job>(job,HttpStatus.OK);
	}
	
	@RequestMapping(value="/getalljobs",method=RequestMethod.GET)
	public ResponseEntity<?> getAllJobs(HttpSession session){
		
		String email = (String) session.getAttribute("loggedInUser");
		if(email == null) 
		{
			ErrorClazz errorClazz = new ErrorClazz(4,"Unauthorized Access...please login");
			return new ResponseEntity<ErrorClazz>(errorClazz,HttpStatus.UNAUTHORIZED);
		}
	
		List<Job> listOfJobs = jobDAO.getAllJobs();
		return new ResponseEntity<List<Job>>(listOfJobs,HttpStatus.OK);
	}
}

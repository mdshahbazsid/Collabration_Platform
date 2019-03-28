package com.collabration.controllers;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.collabration.dao.ProfilePictureDAO;
import com.collabration.model.ErrorClazz;
import com.collabration.model.ProfilePicture;

@RestController
public class ProfilePictureController {

	@Autowired
	ProfilePictureDAO profilePictureDAO;
	
	@RequestMapping(value="/uploadprofilepic",method=RequestMethod.POST)
	public ResponseEntity<?> uploadProfilePictureOrUpdate(@RequestParam CommonsMultipartFile image,HttpSession session){
		
		String email = (String) session.getAttribute("loggedInUser");
		if(email == null) 
		{
			ErrorClazz errorClazz = new ErrorClazz(4,"Unauthorized Access...please login");
			return new ResponseEntity<ErrorClazz>(errorClazz,HttpStatus.UNAUTHORIZED);
		}
		
		ProfilePicture pp = new ProfilePicture();
		pp.setEmail(email);
		pp.setImage(image.getBytes());
		
		profilePictureDAO.uploadProfilePicture(pp);

		return new ResponseEntity<ProfilePicture>(pp,HttpStatus.OK);	
	}
	
	@RequestMapping(value="/getprofilepic/{email}",method=RequestMethod.GET)
	public @ResponseBody byte[] getProfileImage(@PathVariable("email") String email,HttpSession session){
		
		String email2 = (String) session.getAttribute("loggedInUser");
		if(email2 == null) 
		{
			return null;
		}
		
		ProfilePicture profilePicture = profilePictureDAO.getProfilePicture(email);
		if(profilePicture == null) 
		{
			return null;
		}
		else
			return profilePicture.getImage();
	}
}

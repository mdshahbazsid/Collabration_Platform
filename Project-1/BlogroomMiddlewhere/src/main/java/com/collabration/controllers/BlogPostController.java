package com.collabration.controllers;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.collabration.dao.BlogPostDAO;
import com.collabration.dao.BlogPostLikesDAO;
import com.collabration.dao.NotificationDAO;
import com.collabration.dao.UserDAO;
import com.collabration.model.BlogPost;
import com.collabration.model.BlogPostLikes;
import com.collabration.model.ErrorClazz;
import com.collabration.model.Notification;
import com.collabration.model.User;

@RestController
public class BlogPostController {

	@Autowired
	BlogPostDAO blogPostDAO;
	
	@Autowired
	UserDAO userDAO;
	
	@Autowired
	NotificationDAO notificationDAO;
	
	@Autowired
	BlogPostLikesDAO blogPostLikesDAO;
	
	@RequestMapping(value="/addblog",method=RequestMethod.POST)
	public ResponseEntity<?> addBlog(@RequestBody BlogPost blogPost,HttpSession session){
		
		String email = (String) session.getAttribute("loggedInUser");
		if(email == null) 
		{
			ErrorClazz errorClazz = new ErrorClazz(4,"Unauthorized Access...please login");
			return new ResponseEntity<ErrorClazz>(errorClazz,HttpStatus.UNAUTHORIZED);
		}
		try 
		{
			blogPost.setPostedOn(new Date());
			blogPost.setPostedBy(userDAO.getUser(email));
			blogPostDAO.addBlog(blogPost);
			
			return new ResponseEntity<BlogPost>(blogPost,HttpStatus.OK);
		} 
		catch (Exception e) 
		{
			ErrorClazz errorClazz = new ErrorClazz(6,"Blog is not posted...something went wrong");
			return new ResponseEntity<ErrorClazz>(errorClazz,HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value="/getblog/{blogId}",method=RequestMethod.GET)
	public ResponseEntity<?> getBlog(@PathVariable("blogId") int blogId,HttpSession session){
		
		String email = (String) session.getAttribute("loggedInUser");
		if(email == null) 
		{
			ErrorClazz errorClazz = new ErrorClazz(4,"Unauthorized Access...please login");
			return new ResponseEntity<ErrorClazz>(errorClazz,HttpStatus.UNAUTHORIZED);
		}
		BlogPost blogPost = blogPostDAO.getBlogById(blogId);
		return new ResponseEntity<BlogPost>(blogPost,HttpStatus.OK);
	}
	
	@RequestMapping(value="/blogswaitingforapproval",method=RequestMethod.GET)
	public ResponseEntity<?> blogsWaitingForApproval(HttpSession session){
		
		//for AUTHENTICATION --> whether the user is loggedIn or not
		String email = (String) session.getAttribute("loggedInUser");
		if(email == null) 
		{
			ErrorClazz errorClazz = new ErrorClazz(4,"Unauthorized Access...please login");
			return new ResponseEntity<ErrorClazz>(errorClazz,HttpStatus.UNAUTHORIZED);
		}
		User user = userDAO.getUser(email);
		//for AUTHORIZATION --> only ADMIN can view list of Blogs which are waiting for Approval
		if(!user.getRole().equals("ADMIN")) 
		{
			ErrorClazz errorClazz = new ErrorClazz(5,"Unauthorized Access...Only ADMIN can access");
			return new ResponseEntity<ErrorClazz>(errorClazz,HttpStatus.UNAUTHORIZED);
		}
		List<BlogPost> listOfBlogsWaitingForApproval = blogPostDAO.getListOfBlogsWaitingForApproval();
		return new ResponseEntity<List<BlogPost>>(listOfBlogsWaitingForApproval,HttpStatus.OK);
	}
	
	@RequestMapping(value="/approvedblogs",method=RequestMethod.GET)
	public ResponseEntity<?> approvedBlog(HttpSession session){
		
		String email = (String) session.getAttribute("loggedInUser");
		if(email == null) 
		{
			ErrorClazz errorClazz = new ErrorClazz(4,"Unauthorized Access...please login");
			return new ResponseEntity<ErrorClazz>(errorClazz,HttpStatus.UNAUTHORIZED);
		}
		List<BlogPost> listOfBlogsApproved = blogPostDAO.getListOfBlogsApproved();
		return new ResponseEntity<List<BlogPost>>(listOfBlogsApproved,HttpStatus.OK);
	}
	
	@RequestMapping(value="/updateblog",method=RequestMethod.PUT)
	public ResponseEntity<?> updatingApprovalStatus(@RequestBody BlogPost blogPost,HttpSession session){
		
		//for AUTHENTICATION --> whether the user is loggedIn or not
		String email = (String) session.getAttribute("loggedInUser");
		if(email == null) 
		{
			ErrorClazz errorClazz = new ErrorClazz(4,"Unauthorized Access...please login");
			return new ResponseEntity<ErrorClazz>(errorClazz,HttpStatus.UNAUTHORIZED);
		}
		//for AUTHORIZATION --> only ADMIN can view list of Blogs which are waiting for Approval
		User user = userDAO.getUser(email);
		if(!user.getRole().equals("ADMIN")) 
		{
			ErrorClazz errorClazz = new ErrorClazz(5,"Unauthorized Access...Only ADMIN can access");
			return new ResponseEntity<ErrorClazz>(errorClazz,HttpStatus.UNAUTHORIZED);
		}
		
		blogPost.setApprovalStatus(true);
		blogPostDAO.updateBlog(blogPost);
		
		//Notification 
		Notification notification = new Notification();
		notification.setApprovalStatus("APPROVED");
		notification.setBlogTitle(blogPost.getBlogTitle());
		notification.setEmail(blogPost.getPostedBy().getEmail());
		notificationDAO.addNotification(notification);
		
		return new ResponseEntity<BlogPost>(blogPost,HttpStatus.OK);
	}
	
	@RequestMapping(value="/deleteblog",method=RequestMethod.PUT)
	public ResponseEntity<?> rejectingBlog(@RequestBody BlogPost blogPost,@RequestParam String rejectionReason,HttpSession session){
		
		//for AUTHENTICATION --> whether the user is loggedIn or not
		String email = (String) session.getAttribute("loggedInUser");
		if(email == null) 
		{
			ErrorClazz errorClazz = new ErrorClazz(4,"Unauthorized Access...please login");
			return new ResponseEntity<ErrorClazz>(errorClazz,HttpStatus.UNAUTHORIZED);
		}
		//for AUTHORIZATION --> only ADMIN can view list of Blogs which are waiting for Approval
		User user = userDAO.getUser(email);
		if(!user.getRole().equals("ADMIN")) 
		{
			ErrorClazz errorClazz = new ErrorClazz(5,"Unauthorized Access...Only ADMIN can access");
			return new ResponseEntity<ErrorClazz>(errorClazz,HttpStatus.UNAUTHORIZED);
		}
		
		//Notification 
		Notification notification = new Notification();
		notification.setApprovalStatus("REJECTED");
		notification.setBlogTitle(blogPost.getBlogTitle());
		notification.setEmail(blogPost.getPostedBy().getEmail());
		notification.setRejectionReason(rejectionReason);
		notificationDAO.addNotification(notification);
		
		blogPostDAO.deleteBlog(blogPost);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
	
	@RequestMapping(value="/haspostliked/{blogId}",method=RequestMethod.GET)
	public ResponseEntity<?> hasUserLikedBlogPost(@PathVariable("blogId") int blogId,HttpSession session){
		
		String email = (String) session.getAttribute("loggedInUser");
		if(email == null) 
		{
			ErrorClazz errorClazz = new ErrorClazz(4,"Unauthorized Access...please login");
			return new ResponseEntity<ErrorClazz>(errorClazz,HttpStatus.UNAUTHORIZED);
		}
		
		BlogPostLikes blogPostLikes = blogPostLikesDAO.hasUserLikedPost(blogId, email);
		return new ResponseEntity<BlogPostLikes>(blogPostLikes,HttpStatus.OK);
		
		//BlogPostLikes ==null, response.data='', $scope.liked=false, glyphicon-thumps-up in black color
		//blogpostlikes !=null, $scope.liked=true,glyphicon-thumps-up in blue color
	}
	@RequestMapping(value="/updatelikes/{blogId}",method=RequestMethod.PUT)
	public ResponseEntity<?> updateLikes(@PathVariable("blogId") int blogId,HttpSession session){
		//id is 735
		String email = (String) session.getAttribute("loggedInUser");
		if(email == null) 
		{
			ErrorClazz errorClazz = new ErrorClazz(4,"Unauthorized Access...please login");
			return new ResponseEntity<ErrorClazz>(errorClazz,HttpStatus.UNAUTHORIZED);
		}
		//  if logged in user is john.s@xyz.com updateLikes(735,john.s@xyz.com)
		BlogPost blogPost=blogPostLikesDAO.updateLikes(blogId, email);
		return new ResponseEntity<BlogPost>(blogPost,HttpStatus.OK);
	}
}

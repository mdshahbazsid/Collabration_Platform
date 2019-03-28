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

import com.collabration.dao.BlogCommentDAO;
import com.collabration.dao.UserDAO;
import com.collabration.model.BlogComment;
import com.collabration.model.BlogPost;
import com.collabration.model.ErrorClazz;

@RestController
public class BlogCommentController {

	@Autowired
	BlogCommentDAO blogCommentDAO;
	
	@Autowired
	UserDAO userDAO;
	
	@RequestMapping(value="/addcomment",method=RequestMethod.POST)
	public ResponseEntity<?> addBlogComment(@RequestBody BlogPost blogPost,@RequestParam String commentTxt,HttpSession session){
		
		String email = (String) session.getAttribute("loggedInUser");
		if(email == null) 
		{
			ErrorClazz errorClazz = new ErrorClazz(4,"Unauthorized Access...please login");
			return new ResponseEntity<ErrorClazz>(errorClazz,HttpStatus.UNAUTHORIZED);
		}
		
		BlogComment blogComment = new BlogComment();
		blogComment.setCommentTxt(commentTxt);
		blogComment.setBlogPost(blogPost);
		blogComment.setCommentedBy(userDAO.getUser(email));
		blogComment.setCommentedOn(new Date());
		blogCommentDAO.addComment(blogComment);
		
		return new ResponseEntity<BlogComment>(blogComment,HttpStatus.OK);
	}
	
	@RequestMapping(value="/getcomments/{blogId}",method=RequestMethod.GET)
	public ResponseEntity<?> getCommentsByBlogId(@PathVariable("blogId") int blogId,HttpSession session){
		
		String email = (String) session.getAttribute("loggedInUser");
		if(email == null) 
		{
			ErrorClazz errorClazz = new ErrorClazz(4,"Unauthorized Access...please login");
			return new ResponseEntity<ErrorClazz>(errorClazz,HttpStatus.UNAUTHORIZED);
		}
		
		List<BlogComment> listOfComments =  blogCommentDAO.getListOfComment(blogId);
		
		return new ResponseEntity<List<BlogComment>>(listOfComments,HttpStatus.OK);
	}
}

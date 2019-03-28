package com.collabration.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;

import com.collabration.model.Chat;

@Controller
public class SockController {

	private final SimpMessagingTemplate messsagingTemplate;
	private List<String> users = new ArrayList<String>();
	
	@Autowired
	public SockController(SimpMessagingTemplate messagingTemplate) {
		
		super();
		this.messsagingTemplate = messagingTemplate;
	}
	
	@SubscribeMapping("/join/{username}")
	public List<String> join(@DestinationVariable String username){
		System.out.println("Newly User Joined : "+username);
		if(!users.contains(username))   //username not already exist
			users.add(username);
		messsagingTemplate.convertAndSend("/topic/join",username);    //other previous users in chatroom
		return users;
	}
	
	@MessageMapping("/chat")
	public void chatRecieved(Chat chat) {
		
		if(chat.getTo().equals("all"))    //gp chat
		{   
			messsagingTemplate.convertAndSend("/queue/chats",chat);
		}
		else      //private chat
		{
			messsagingTemplate.convertAndSend("/queue/chats/"+chat.getTo(),chat);
			messsagingTemplate.convertAndSend("/queue/chats/"+chat.getFrom(),chat);
		}
	}
	
}

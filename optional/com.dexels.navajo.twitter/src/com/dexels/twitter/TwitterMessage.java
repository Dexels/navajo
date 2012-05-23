package com.dexels.twitter;

import java.util.Date;

import winterwell.jtwitter.Twitter.Message;

public class TwitterMessage {
	private Date 			createdAt;
	private TwitterUser 	recipient;
	private TwitterUser 	sender;
	private String 			text;
	private TwitterUser 	user;
	
	
	public TwitterMessage(Message m){
		this.createdAt 	= m.getCreatedAt();
		this.recipient	= new TwitterUser(m.getRecipient());
		this.text 		= m.getText();
		this.user 		= new TwitterUser(m.getUser());
		this.sender 	= new TwitterUser(m.getSender());
	}
	
	public Date getCreatedAt(){
		return createdAt;
	}
	
	public TwitterUser getRecipient(){
		return recipient;
	}
	
	public TwitterUser getSender(){
		return sender;
	}
	
	public String getText(){
		return text;
	}
	
	public TwitterUser getUser(){
		return user;
	}
	
}

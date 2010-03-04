package com.dexels.twitter;

import java.util.Date;
import java.util.List;

import winterwell.jtwitter.Twitter.Status;

public class TwitterStatus {
	private Date 			createdAt;
	private String 			text;
	private TwitterUser 	user;
	private List<String> 	mentions;
	
	
	public TwitterStatus(Status s){
		this.createdAt 	= s.getCreatedAt();
		this.text 		= s.getText();
		this.user 		= new TwitterUser(s.getUser());
		this.mentions 	= s.getMentions();
	}
	
	public String getText(){
		return text;
	}
	
	public Date getCreatedAt(){
		return createdAt;
	}
	
	public TwitterUser getUser(){
		return user;
	}
	
	public List<String> getMentions(){
		return mentions;
	}
}

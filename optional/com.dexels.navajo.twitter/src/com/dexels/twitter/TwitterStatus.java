package com.dexels.twitter;

import java.util.Date;
import java.util.List;

import winterwell.jtwitter.Status;

public class TwitterStatus {
	private Date 			createdAt;
	private String 			text;
	private TwitterUser 	user;
	private List<String> 	mentions;
	
	
	public TwitterStatus(Status s){
		setProperties(s, null);
	}
	
	public TwitterStatus(Status s, TwitterUser u){
		setProperties(s, u);
	}
	
	private void setProperties(Status s, TwitterUser u){
		this.createdAt 	= s.getCreatedAt();
		this.text 		= s.getText();
		if(u == null && s.getUser() != null){
			this.user 		= new TwitterUser(s.getUser(), this);
		} else {
			this.user = u;
		}
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

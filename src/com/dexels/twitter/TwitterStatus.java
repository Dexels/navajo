package com.dexels.twitter;

import java.util.Date;
import java.util.List;

import winterwell.jtwitter.Twitter.Status;

public class TwitterStatus {
	private Status myStatus;
	
	public TwitterStatus(Status s){
		myStatus = s;
	}
	
	public String getText(){
		return myStatus.getText();
	}
	
	public Date getCreatedAt(){
		return myStatus.getCreatedAt();
	}
	
	public TwitterUser getUser(){
		return new TwitterUser(myStatus.getUser());
	}
	
	// This might nog work properly
	public List<String> getMentions(){
		return myStatus.getMentions();
	}
}

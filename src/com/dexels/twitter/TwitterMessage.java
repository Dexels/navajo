package com.dexels.twitter;

import java.util.Date;

import winterwell.jtwitter.Twitter.Message;

public class TwitterMessage {
	private Message myMessage;
	
	public TwitterMessage(Message m){
		myMessage = m;
	}
	
	public Date getCreatedAt(){
		return myMessage.getCreatedAt();
	}
	
	public TwitterUser getRecipient(){
		return new TwitterUser(myMessage.getRecipient());
	}
	
	public TwitterUser getSender(){
		return new TwitterUser(myMessage.getSender());
	}
	
	public String getText(){
		return myMessage.getText();
	}
	
	public TwitterUser getUser(){
		return new TwitterUser(myMessage.getUser());
	}
	
}

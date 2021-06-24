/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.twitter;

import java.util.Date;

import winterwell.jtwitter.Message;

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

package com.dexels.twitter;

import java.net.URI;

import winterwell.jtwitter.Twitter.User;

public class TwitterUser {
	private User myUser;
	
	public TwitterUser(User u){
		myUser = u;
	}

	public TwitterStatus getStatus(){
		return new TwitterStatus(myUser.getStatus());
	}
	
	public String getScreenName(){
		return myUser.getScreenName();
	}
	
	public String getCreatedAt(){
		return myUser.getCreatedAt();
	}
	
	public String getDescription(){
		return myUser.getDescription();
	}
	
	public int getFavoritesCount(){
		return myUser.getFavoritesCount();
	}
	
	public int getFollowersCount(){
		return myUser.getFollowersCount();
	}
	
	public int getFriedsCount(){
		return myUser.getFriendsCount();
	}
	
	public String getLocation(){
		return myUser.getLocation();
	}
	
	public String getName(){
		return myUser.getName();
	}
	
	public String getProfileImageUrl(){
		if(myUser.getProfileImageUrl() != null){
			return myUser.getProfileImageUrl().toString();
		}
		return null;
	}
	
	public boolean getProtectedUser(){
		return myUser.getProtectedUser();
	}
	
	public int getStatusesCount(){
		return myUser.getStatusesCount();
	}
	
	public String getTimezone(){
		return myUser.getTimezone();
	}
	
	public int getTimezoneOffset(){
		return myUser.getTimezoneOffSet();
	}
	
	public String getWebsiteUrl(){
		if(myUser.getWebsite() != null){
			return myUser.getWebsite().toString();
		}
		return null;
	}
}

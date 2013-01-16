package com.dexels.twitter;

import java.net.URI;
import java.util.Date;

import winterwell.jtwitter.User;

public class TwitterUser {
	private String 			screenName;
	private Date 			createdAt;
	private String 			description;
	private int 			favoritesCount;
	private int 			followersCount;
	private int 			friendsCount;
	private String 			location;
	private String 			name;
	private URI 			profileImageUrl;
	private boolean 		protectedUser;
	private TwitterStatus 	status;
	private int 			statusesCount;
	private String 			timezone;
	private double 			timezoneOffset;
	private URI 			websiteUrl;
	
	public TwitterUser(User u){
		setProperties(u, null);
	}

	public TwitterUser(User u, TwitterStatus s){
		setProperties(u, s);
	}
	
	private void setProperties(User u, TwitterStatus s){
		this.screenName			= u.getScreenName();
		this.createdAt			= u.getCreatedAt();
		this.description		= u.getDescription();
		this.favoritesCount		= u.getFavoritesCount();
		this.followersCount		= u.getFollowersCount();
		this.friendsCount		= u.getFriendsCount();
		this.location			= u.getLocation();
		this.name				= u.getName();
		this.profileImageUrl	= u.getProfileImageUrl();
		this.protectedUser		= u.getProtectedUser();
		if(s == null && u.getStatus() != null){
			this.status				= new TwitterStatus(u.getStatus(), this);
		} else {
			this.status = s;
		}
		this.statusesCount		= u.getStatusesCount();
		this.timezone			= u.getTimezone();
		this.timezoneOffset		= u.getTimezoneOffSet();
		this.websiteUrl			= u.getWebsite();
	}
	
	public TwitterStatus getStatus(){
		return status;
	}
	
	public String getScreenName(){
		return screenName;
	}
	
	public Date getCreatedAt(){
		return createdAt;
	}
	
	public String getDescription(){
		return description;
	}
	
	public int getFavoritesCount(){
		return favoritesCount;
	}
	
	public int getFollowersCount(){
		return followersCount;
	}
	
	public int getFriendsCount(){
		return friendsCount;
	}
	
	public String getLocation(){
		return location;
	}
	
	public String getName(){
		return name;
	}
	
	public String getProfileImageUrl(){
		if(profileImageUrl != null){
			return profileImageUrl.toString();
		}
		return null;
	}
	
	public boolean getProtectedUser(){
		return protectedUser;
	}
	
	public int getStatusesCount(){
		return statusesCount;
	}
	
	public String getTimezone(){
		return timezone;
	}
	
	public double getTimezoneOffset(){
		return timezoneOffset;
	}
	
	public String getWebsiteUrl(){
		if(websiteUrl != null){
			return websiteUrl.toString();
		}
		return null;
	}
}

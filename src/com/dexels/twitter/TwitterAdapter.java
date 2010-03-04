package com.dexels.twitter;

import java.util.Date;
import java.util.List;

import winterwell.jtwitter.Twitter;
import winterwell.jtwitter.Twitter.Message;
import winterwell.jtwitter.Twitter.Status;
import winterwell.jtwitter.Twitter.User;

public class TwitterAdapter {
	private Twitter twit;
	private String username, password;
	private String currentUser;

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public void setCurrentUser(String userName){
		this.currentUser = userName;
	}
	
	// This will not work as expected. the Status class is a final static subclass
	public TwitterStatus getStatus(){
		if (twit == null) {
			twit = new Twitter(username, password);
		}
		Status s;
		if(currentUser == null){
			s = twit.getStatus();
		}else{
			s = twit.getStatus(currentUser);
		}
		return new TwitterStatus(s);
	}
	

	public void setStatus(String status) {
		try {
			String statusText = status;
			if (statusText.length() > 140) {
				statusText = statusText.substring(0, 140);
			}
			if (twit == null) {
				twit = new Twitter(username, password);
			}
			twit.setSource("Navajo Integrator");
			twit.updateStatus(statusText);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public TwitterMessage[] getDirectMessages(){
		List<Message> messages = twit.getDirectMessages();
		TwitterMessage[] twm = new TwitterMessage[messages.size()];
		for(int i=0;i<messages.size();i++){
			twm[i] = new TwitterMessage(messages.get(i));
		}
		return twm;
	}
	
	public TwitterMessage[] getDirectMessagesSent(){
		List<Message> messages = twit.getDirectMessagesSent();
		TwitterMessage[] twm = new TwitterMessage[messages.size()];
		for(int i=0;i<messages.size();i++){
			twm[i] = new TwitterMessage(messages.get(i));
		}
		return twm;
	}
	
	public TwitterStatus[] getFavorites(){
		List<Status> favos;
		if(currentUser == null){
			favos = twit.getFavorites();
		} else {
			favos = twit.getFavorites(currentUser);
		}
		
		TwitterStatus[] tws = new TwitterStatus[favos.size()];
		for(int i=0;i<favos.size();i++){
			tws[i] = new TwitterStatus(favos.get(i));
		}
		return tws;
	}
	
	public TwitterUser[] getFeatured(){
		List<User> users = twit.getFeatured();
		TwitterUser[] twu = new TwitterUser[users.size()];
		for(int i=0;i<users.size();i++){
			twu[i] = new TwitterUser(users.get(i));
		}
		return twu;
	}
	
	public TwitterUser[] getFollowers(){
		List<User> users;
		if(currentUser == null){
			users = twit.getFollowers();
		} else {
			users = twit.getFollowers(currentUser);
		}
		
		TwitterUser[] twu = new TwitterUser[users.size()];
		for(int i=0;i<users.size();i++){
			twu[i] = new TwitterUser(users.get(i));
		}
		return twu;
	}
	
	public TwitterUser[] getFriends(){
		List<User> users;
		if(currentUser == null){
			users = twit.getFriends();
		} else {
			users = twit.getFriends(currentUser);
		}
		
		TwitterUser[] twu = new TwitterUser[users.size()];
		for(int i=0;i<users.size();i++){
			twu[i] = new TwitterUser(users.get(i));
		}
		return twu;
	}
	
	public TwitterStatus[] getFriendsTimeline(){
		List<Status> timeline = twit.getFriendsTimeline();		
		TwitterStatus[] tws = new TwitterStatus[timeline.size()];
		for(int i=0;i<timeline.size();i++){
			tws[i] = new TwitterStatus(timeline.get(i));
		}
		return tws;
	}
	
	public TwitterStatus[] getHomeTimeline(){
		List<Status> timeline = twit.getHomeTimeline();		
		TwitterStatus[] tws = new TwitterStatus[timeline.size()];
		for(int i=0;i<timeline.size();i++){
			tws[i] = new TwitterStatus(timeline.get(i));
		}
		return tws;
	}
	
	public TwitterStatus[] getPublicTimeline(){
		List<Status> timeline = twit.getPublicTimeline();		
		TwitterStatus[] tws = new TwitterStatus[timeline.size()];
		for(int i=0;i<timeline.size();i++){
			tws[i] = new TwitterStatus(timeline.get(i));
		}
		return tws;
	}
	
	public int getRateLimitStatus(){
		return twit.getRateLimitStatus();
	}
	
	public TwitterStatus[] getReplies(){
		List<Status> replies = twit.getReplies();		
		TwitterStatus[] tws = new TwitterStatus[replies.size()];
		for(int i=0;i<replies.size();i++){
			tws[i] = new TwitterStatus(replies.get(i));
		}
		return tws;
	}
	
	public TwitterStatus[] getRetweetsOfMe(){
		List<Status> retweets = twit.getRetweetsOfMe();		
		TwitterStatus[] tws = new TwitterStatus[retweets.size()];
		for(int i=0;i<retweets.size();i++){
			tws[i] = new TwitterStatus(retweets.get(i));
		}
		return tws;
	}
	
	public String getScreenName(){
		return twit.getScreenName();
	}
	
	public String[] getTrends(){
		List<String> trends = twit.getTrends();		
		String[] tws = new String[trends.size()];
		for(int i=0;i<trends.size();i++){
			tws[i] = new String(trends.get(i));
		}
		return tws;
	}
	
	public Date getUntilDate(){
		return twit.getUntilDate();
	}
	
	public TwitterUser getUser(){
		if(currentUser == null){
			return null;
		} else {
			return new TwitterUser(twit.getUser(currentUser));
		}
	}
	
	public TwitterStatus[] getUserTimeline(){
		List<Status> users;
		if(currentUser == null){
			users = twit.getUserTimeline();
		} else {
			users = twit.getUserTimeline(currentUser);
		}		
		TwitterStatus[] tws = new TwitterStatus[users.size()];
		for(int i=0;i<users.size();i++){
			tws[i] = new TwitterStatus(users.get(i));
		}
		return tws;
	}
	

}

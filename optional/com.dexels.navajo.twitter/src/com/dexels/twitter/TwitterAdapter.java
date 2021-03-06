/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.twitter;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URI;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import oauth.signpost.basic.DefaultOAuthProvider;
import winterwell.jtwitter.Message;
import winterwell.jtwitter.OAuthSignpostClient;
import winterwell.jtwitter.Status;
import winterwell.jtwitter.Twitter;
import winterwell.jtwitter.TwitterException;
import winterwell.jtwitter.User;

import com.dexels.navajo.document.types.Binary;
import com.dexels.navajo.script.api.Access;
import com.dexels.navajo.script.api.Mappable;
import com.dexels.navajo.script.api.MappableException;
import com.dexels.navajo.script.api.UserException;

public class TwitterAdapter implements Mappable{
	private Twitter twit;
	private String username = null; //, status;
	private String currentUser;
	private String token1;
	private String token2;
	
	// When develop_mode is true, the tweets are not actually sent but instead logged
	private boolean develop_mode;

	
	private OAuthSignpostClient mySignPost = null; 
		
	
	private final static Logger logger = LoggerFactory.getLogger(TwitterAdapter.class);
	private final static Logger devlogger = LoggerFactory.getLogger("twitter_dev");
	
	final static String API_KEY = "UVyOkSE0F1i2YcqaPc0jYg";
	private final static String API_SECRET = "7Uhm0fVFrSesY0Czamuy86ZnyetVPkYjLLgG8N3rabE";
	
	
	public void setCurrentUser(String userName){
		this.currentUser = userName;
	}
	
	// This will not work as expected. the Status class is a final static subclass
	@SuppressWarnings("deprecation")
	public TwitterStatus getStatus(){
		if (twit == null) {
			twit = new Twitter(username, (String)null);
		}
		Status s;
		if(currentUser == null){
			s = twit.getStatus();
		}else{
			s = twit.getStatus(currentUser);
		}
		if (s == null) {
		    throw new TwitterException("Unable to get twitter obj");
		}
		return new TwitterStatus(s);
	}
	
	public void setUsername(String s) {
		this.username = s;
	}
	
	public void setToken1(String t) {
		token1 = t;
	}
	
	public void setToken2(String t) {
		token2 = t;
		OAuthSignpostClient oauthClient = new OAuthSignpostClient(API_KEY,  API_SECRET, token1, token2);		
		twit = new Twitter(null, oauthClient);
	}

	public void setStatus(String status) {
		try {
			String statusText = status;
			if (statusText.length() > 140) {
				statusText = statusText.substring(0, 140);
			}
			twit.setSource("Navajo Integrator");
			if (develop_mode) {
			    devlogger.info("New status: {}", statusText);
			    return;
			}
			twit.updateStatus(statusText);
		} catch (Exception e) {
			logger.error("Error: ", e);
		}
	}
	
	public void setStatus(Object status) {
	    if (develop_mode) {
            devlogger.info("New status: {}", status.toString());
            return;
        }
		try {
			setStatus(status.toString());
		} catch (Exception e) {
			logger.error("Error: ", e);
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
	
//	public TwitterUser[] getFeatured(){
//		List<User> users = twit.getFeatured();
//		TwitterUser[] twu = new TwitterUser[users.size()];
//		for(int i=0;i<users.size();i++){
//			twu[i] = new TwitterUser(users.get(i));
//		}
//		return twu;
//	}
	
	@Deprecated
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
	
	@SuppressWarnings("deprecation")
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
	
	@SuppressWarnings("deprecation")
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
	
//	public TwitterStatus[] getPublicTimeline(){
//		List<Status> timeline = twit.getPublicTimeline();		
//		TwitterStatus[] tws = new TwitterStatus[timeline.size()];
//		for(int i=0;i<timeline.size();i++){
//			tws[i] = new TwitterStatus(timeline.get(i));
//		}
//		return tws;
//	}
	
	@SuppressWarnings("deprecation")
	public int getRateLimitStatus(){
		return twit.getRateLimitStatus();
	}
	
	@Deprecated
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
	
	public String getDisplayName(){
		if ( twit == null ) {
			return null;
		}
		if ( twit.getStatus() != null ) {
			return twit.getStatus().getUser().getName();
		} else {
			twit.setStatus(".");
			return twit.getStatus().getUser().getName();
		}
	}
	
	public String getScreenName(){
		return twit.getScreenName();
	}
	
	public String getUserName() {
		if ( twit == null ) {
			return null;
		}
		if ( twit.getStatus() != null ) {
			return twit.getStatus().getUser().getScreenName();
		} else {
			
			
			twit.setStatus(".");
			return twit.getStatus().getUser().getScreenName();
		}
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
	

	@Deprecated
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
	
	private void createSignPostObkect() {
		if ( mySignPost == null ) {
			DefaultOAuthProvider provider = new DefaultOAuthProvider(
	                "https://api.twitter.com/oauth/request_token",
	                "https://api.twitter.com/oauth/access_token",
	                "https://api.twitter.com/oauth/authorize");
	
			mySignPost =	new OAuthSignpostClient(API_KEY, API_SECRET, "oob");
			mySignPost.setProvider(provider);
		}
	}
	
	public String getBrowserURL() {
		createSignPostObkect();
		URI url = mySignPost.authorizeUrl();
		
		return url + "";
	}
	
	public Binary getSignPost() throws UserException {
		if ( mySignPost != null ) {
			Binary b = new Binary();
			try {
			ObjectOutputStream oos = new ObjectOutputStream(b.getOutputStream());
			oos.writeObject(mySignPost);
			oos.close();
			} catch (Exception e) {
				throw new UserException(-1, e.getMessage(),e);
			}
			return b;
		} else {
			throw new UserException(-1, "Signpost not set.");
		}
	}
	
	public void setSignPost(Binary b) throws UserException {
		ObjectInputStream ois;
		try {
			ois = new ObjectInputStream(b.getDataAsStream());
			mySignPost = (OAuthSignpostClient) ois.readObject();
		} catch (Exception e) {
			throw new UserException(-1, e.getMessage(),e);
		}
		
	}
	
	public void setPincode(String pin) throws UserException {
	
		if ( mySignPost == null ) {
			throw new UserException(-1, "Set signpost object first.");
		}
		
		mySignPost.setAuthorizationCode(pin);
		// Store the authorisation token details for future use
		String[] accessToken = mySignPost.getAccessToken();
		token1 = accessToken[0];
		token2 = accessToken[1];
		
	}
	
	public String getToken1() {
		return token1;
	}
	
	public String getToken2() {
		return token2;
	}

	@Override
	public void load(Access access) throws MappableException, UserException {
	    develop_mode = "true".equals(System.getenv("DEVELOP_MODE"));
	}

	@Override
	public void store() throws MappableException, UserException {
		
	}

	@Override
	public void kill() {
		
	}

}

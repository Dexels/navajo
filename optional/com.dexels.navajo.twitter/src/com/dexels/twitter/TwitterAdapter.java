package com.dexels.twitter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URI;
import java.util.Date;
import java.util.List;

import winterwell.jtwitter.OAuthSignpostClient;
import winterwell.jtwitter.Twitter;
import winterwell.jtwitter.Twitter.Message;
import winterwell.jtwitter.Twitter.Status;
import winterwell.jtwitter.Twitter.User;

import com.dexels.navajo.document.types.Binary;
import com.dexels.navajo.server.UserException;

public class TwitterAdapter {
	private Twitter twit = null;
	private String username = null;//, status;
	private String currentUser;
	private String token1;
	private String token2;
//	private Binary signPost;
	
	private OAuthSignpostClient mySignPost = null; 
		
	private final static String API_KEY = "UVyOkSE0F1i2YcqaPc0jYg";
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
		System.err.println("Login in with token1: " + token1 + ", token2: " + token2);
		
		twit = new Twitter(username, oauthClient);
	}

	public void setStatus(String status) {
		try {
			String statusText = status;
			if (statusText.length() > 140) {
				statusText = statusText.substring(0, 140);
			}
			twit.setSource("Navajo Integrator");
			twit.updateStatus(statusText);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void setStatus(Object status) {
		try {
			setStatus(status.toString());
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
	
	public String getUserName() {
		return twit.getUser(twit.getScreenName()).getName();
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
	
	private void createSignPostObkect() {
		if ( mySignPost == null ) {
			mySignPost =	new OAuthSignpostClient(API_KEY, API_SECRET, "oob");
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
				throw new UserException(-1, e.getMessage());
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
			throw new UserException(-1, e.getMessage());
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
	
	public static void main(String [] args) throws Exception {
		// Make an oauth client (you'll want to change this bit)
		
		OAuthSignpostClient oauthClient = 
		new OAuthSignpostClient(API_KEY, API_SECRET, "oob");
		   
			// Open the authorisation page in the user's browser
		// On Android, you'd direct the user to URI url = client.authorizeUrl();
		// On a desktop, we can do that like this:
		
		//URI url = oauthClient.authorizeUrl();
		//System.err.println("url = " + url);
		
		oauthClient.authorizeDesktop();
		// get the pin
		String v = OAuthSignpostClient.askUser("Please enter the verification PIN from Twitter");
		oauthClient.setAuthorizationCode(v);
		// Store the authorisation token details for future use
		String[] accessToken = oauthClient.getAccessToken();
		
		System.err.println("accessToken[0]: " + accessToken[0]);
		System.err.println("accessToken[1]: " + accessToken[1]);
//		
		// Next time we can use new OAuthSignpostClient(OAUTH_KEY, OAUTH_SECRET, 
//		      accessToken[0], accessToken[1]) to avoid authenticating again.

		// Make a Twitter object
		Twitter twitter = new Twitter("bbfw63x", oauthClient);
		// Print Daniel Winterstein's status
		System.out.println(twitter.getStatus("bbfw63x"));
		// Set my status
		twitter.setSource("Navajo Integrator");
		Status s = twitter.updateStatus("Nog een keer ietsie wat ook...");
		
	  System.err.println(s.getText());
	  
		
	  List<User> followers = twitter.getFollowers();
	  for ( int i = 0; i < followers.size(); i++ ) {
		  System.err.println(followers.get(i));
	  }
	  
	  ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File("/Users/arjenschoneveld/oauth.class")));
	  oos.writeObject(oauthClient);
	  oos.close();
	  
	}

}

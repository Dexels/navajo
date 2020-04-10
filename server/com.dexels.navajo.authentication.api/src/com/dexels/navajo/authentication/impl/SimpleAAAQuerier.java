package com.dexels.navajo.authentication.impl;

import java.util.HashMap;
import java.util.Map;

import com.dexels.navajo.authentication.api.AAAQuerier;
import com.dexels.navajo.authentication.api.AuthenticationResult;
import com.dexels.navajo.authentication.api.AuthenticationType;
import com.dexels.navajo.script.api.Access;
import com.dexels.navajo.script.api.AuthorizationException;

public class SimpleAAAQuerier implements AAAQuerier {

	private class User {
		public final String username;
		public final int userID;

		User(String username, String password, int userID) {
			this.username = username;
			this.userID = userID;
		}
	}

	private final Map<String,User> usersByUserName = new HashMap<>();

	private final Map<Integer,User> usersByUserId = new HashMap<>();

	@Override
	public AuthenticationResult authenticateUsernamePassword(Access access) {
		String userName = access.getRpcUser();
		System.err.println("UserName: "+userName);
		return AuthenticationResult.AUTHENTICATION_OK;
	}

	public void activate(Map<String,Object> settings) {
		usersByUserId.clear();
		usersByUserName.clear();
		settings.entrySet()
			.stream()
			.filter(e->e.getKey().startsWith("user."))
			.map(e->{
				String username = e.getKey().substring("user.".length());
				String[] parts = ((String)e.getValue()).split(",");
				return new User(username,parts[1],Integer.parseInt(parts[0]));
			}).forEach(user->{
				usersByUserId.put(user.userID, user);
				usersByUserName.put(user.username, user);
			});
	}

	/**
     * Perform the full authentication and authorization stack
     */
    public void process(Access access) throws AuthorizationException {
    	String requestedUser = access.getRpcUser();
    	User u = usersByUserName.get(requestedUser);
    	if(u==null) {
    		throw new AuthorizationException(true, false, requestedUser, "Unknown user: "+requestedUser);
    	}
    }

    /**
     * Skips authentication
     */
    public void authorize(Access access, Integer userid) throws AuthorizationException {

    }


	@Override
	public void reset() {
	}

	@Override
	public void resetCachedUserCredential(String tenant, String username) {
	}

	@Override
	public Integer getUserId(Access a) {
		User u = this.usersByUserName.get(a.getRpcUser());
		if(u==null) {
			return -1;
		}
		return u.userID;
	}

	@Override
	public boolean isFirstUseAccount(Access access) {
		return false;
	}

	@Override
	public int getDaysUntilExpiration(Access access) {
		return 0;
	}

	@Override
	public AuthenticationType type() {
		return AuthenticationType.PASSWORD;
	}

}

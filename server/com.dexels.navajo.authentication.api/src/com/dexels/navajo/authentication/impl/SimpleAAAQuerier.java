/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.authentication.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.dexels.navajo.authentication.api.AAAQuerier;
import com.dexels.navajo.authentication.api.AuthenticationResult;
import com.dexels.navajo.authentication.api.AuthenticationType;
import com.dexels.navajo.script.api.Access;
import com.dexels.navajo.script.api.AuthorizationException;

public class SimpleAAAQuerier implements AAAQuerier {

	private final List<String> usernames = new ArrayList<>();


	@Override
	public AuthenticationResult authenticateUsernamePassword(Access access) {
		String userName = access.getRpcUser();
		System.err.println("UserName: "+userName);
		return AuthenticationResult.AUTHENTICATION_OK;
	}

	public void activate(Map<String,Object> settings) {
	    usernames.clear();
	    settings.entrySet()
           .stream()
           .filter(e->e.getKey().startsWith("user."))
           .map(e->{
               String username = e.getKey().substring("user.".length());
               return username;
           }).forEach(username->{
               usernames.add(username);
           });
	}

	/**
     * Perform the full authentication and authorization stack
     */
    public void process(Access access) throws AuthorizationException {
    	String requestedUser = access.getRpcUser();
    	if(!usernames.contains(requestedUser)) {
    		throw new AuthorizationException(true, false, requestedUser, "Unknown user: "+requestedUser);
    	}
    }

    /**
     * Skips authentication
     */
    public void authorize(Access access, String username) throws AuthorizationException {

    }


	@Override
	public void reset() {
	}

	@Override
	public void resetCachedUserCredential(String tenant, String username) {
	}

	@Override
	public String getUsername(Access a) {
		return a.getRpcUser();
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

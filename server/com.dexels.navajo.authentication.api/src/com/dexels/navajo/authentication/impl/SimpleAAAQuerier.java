package com.dexels.navajo.authentication.impl;

import com.dexels.navajo.authentication.api.AAAQuerier;
import com.dexels.navajo.authentication.api.AuthenticationResult;
import com.dexels.navajo.script.api.Access;
import com.dexels.navajo.script.api.AuthorizationException;

public class SimpleAAAQuerier implements AAAQuerier {

	@Override
	public AuthenticationResult authenticateUsernamePassword(Access access) {
		String userName = access.getRpcUser();
		System.err.println("UserName: "+userName);
		return AuthenticationResult.AUTHENTICATION_OK;
	}

    /**
     * Perform the full authentication and authorization stack
     */
    public void process(Access access) throws AuthorizationException {
    	
    }
    
    /**
     * Skips authentication 
     */
    public void authorize(Access access, Integer userid) throws AuthorizationException {
    	
    }


	@Override
	public void reset() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resetCachedUserCredential(String tenant, String username) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Integer getUserId(Access a) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isFirstUseAccount(Access access) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int getDaysUntilExpiration(Access access) {
		// TODO Auto-generated method stub
		return 0;
	}

}

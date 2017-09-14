package com.dexels.navajo.authentication.impl;

import com.dexels.navajo.authentication.api.AAAQuerier;
import com.dexels.navajo.script.api.Access;
import com.dexels.navajo.script.api.AuthorizationException;

public class DefaultAAAQuerier implements AAAQuerier {

	@Override
	public int authenticateUsernamePassword(Access access) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void process(Access access) throws AuthorizationException {
		// TODO Auto-generated method stub

	}

    @Override
    public void authorize(Access access, Integer userid) throws AuthorizationException {
        // TODO Auto-generated method stub
        
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

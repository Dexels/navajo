package com.dexels.navajo.authentication.impl;

import com.dexels.navajo.authentication.api.AAAQuerier;
import com.dexels.navajo.script.api.Access;
import com.dexels.navajo.script.api.AuthorizationException;

public class DefaultAAAQuerier implements AAAQuerier {

	@Override
	public int authenticateUsernamePassword(Access access) {
		return 0;
	}

	@Override
	public void process(Access access) throws AuthorizationException {

	}

    @Override
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
		return null;
	}

	@Override
	public boolean isFirstUseAccount(Access access) {
		return false;
	}

	@Override
	public int getDaysUntilExpiration(Access access) {
		return 0;
	}


}

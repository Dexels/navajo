package com.dexels.navajo.authentication.impl;

import java.util.List;

import com.dexels.navajo.authentication.api.AAAException;
import com.dexels.navajo.authentication.api.AAAQuerier;
import com.dexels.navajo.script.api.Access;
import com.dexels.navajo.script.api.AuthorizationException;
import com.dexels.navajo.script.api.SystemException;

public class DefaultAAAQuerier implements AAAQuerier {

	@Override
	public int authenticateUsernamePassword(Access access, String username, String password) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void process(Access access) throws SystemException, AuthorizationException {
		// TODO Auto-generated method stub

	}

	@Override
	public List<String> getUserDistricts(Access a, String tenant, String username) throws AAAException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getRegion(String tenant, String username) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void reset(String tenant) {
		// TODO Auto-generated method stub

	}

	@Override
	public void resetCachedUserCredential(String tenant, String username) {
		// TODO Auto-generated method stub

	}

	@Override
	public Integer getUserId(String tenant, String username) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getUserAuthMethod(Access a) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isFirstUseAccount(String tenant, String username) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int getDaysUntilExpiration(String tenant, String username) {
		// TODO Auto-generated method stub
		return 0;
	}

    @Override
    public String getPersonId(Access a) {
        // TODO Auto-generated method stub
        return null;
    }

}

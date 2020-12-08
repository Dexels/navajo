/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.server;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.Header;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.script.api.ClientInfo;
import com.dexels.navajo.script.api.FatalException;
import com.dexels.navajo.script.api.LocalClient;

public class LocalClientDispatcherWrapper implements LocalClient {

	private DispatcherInterface dispatcherInterface;

	private static final Logger logger = LoggerFactory
			.getLogger(LocalClientDispatcherWrapper.class);
	protected String user;
	protected String pass;

	public LocalClientDispatcherWrapper() {
	}

	public void setDispatcher(DispatcherInterface di) {
		this.dispatcherInterface = di;
	}

	public void clearDispatcher(DispatcherInterface di) {
		this.dispatcherInterface = null;
	}

	@Override
	public Navajo call(Navajo n) throws FatalException {
		if (n.getHeader().getRPCUser() == null
				|| n.getHeader().getRPCUser().equals("")) {
			n.getHeader().setRPCUser(user);
		}
		if (n.getHeader().getRPCPassword() == null
				|| n.getHeader().getRPCPassword().equals("")) {
			n.getHeader().setRPCPassword(pass);
		}
		return dispatcherInterface.handle(n);
	}

	@Override
	public Navajo call(String instance, Navajo n) throws FatalException {
		Header h = n.getHeader();
		if (h != null) {
			if(h.getRPCUser()==null) {
				 h.setRPCUser(user);
			}
			if(h.getRPCPassword()==null) {
				h.setRPCPassword(pass);
			}
		}
		return dispatcherInterface.handle(n, instance, null, null);
	}

	public Navajo callWithoutAuth(Navajo n) throws FatalException {
		return dispatcherInterface.handle(n, true);
	}

	@Override
	public Navajo generateAbortMessage(String reason) throws FatalException {
		Navajo outDoc = dispatcherInterface.generateErrorMessage(null, reason,-1, 0, null);
		return outDoc;
	}

	@Override
	public Navajo handleCallback(String tenant, Navajo n, String callback) {
		Navajo result = dispatcherInterface.handleCallbackPointers(n,tenant);
		return result;
	}

    @Override
    public Navajo handleInternal(String instance, Navajo in, Object cert, ClientInfo clientInfo) throws FatalException {
        Navajo outDoc = dispatcherInterface.handle(in, instance, cert, clientInfo);
        try {
        	outDoc.removeInternalMessages();
        }
        catch(Exception e) {
        	logger.error("handleInternal seems to have failed, as outDoc is null.",e);
        }
        return outDoc;
    }

    @Override
    public Navajo handleInternal(String instance, Navajo in, boolean skipAuth) throws FatalException {
        Navajo outDoc = dispatcherInterface.handle(in, instance, skipAuth);
        try {
        	outDoc.removeInternalMessages();
        }
        catch(Exception e) {
        	logger.error("handleInternal seems to have failed, as outDoc is null.",e);
        }
        return outDoc;
    }
	@Override
	public boolean isSpecialWebservice(String name) {
		return Dispatcher.isSpecialwebservice(name);
	}

	@Override
	public String getApplicationId() {
		return dispatcherInterface.getApplicationId();
	}

	public void activate(Map<String, String> properties) {
		// Dictionary properties = cc.getProperties();
		try {
			user = properties.get("user");
			pass = properties.get("password");
		} catch (Throwable e) {
			logger.error("Activation failed");
		}
	}

	public void modified(Map<String, String> properties) {
		try {
			user = properties.get("user");
			pass = properties.get("password");
		} catch (Throwable e) {
			logger.error("Activation failed");
		}
	}

	public void deactivate() {
		try {
			logger.info("Deactivate");
		} catch (Throwable e) {
			logger.error("Activation failed");
		}

	}

}

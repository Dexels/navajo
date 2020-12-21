/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.server.listener.http.standard;

import java.io.IOException;
import java.util.HashMap;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.script.api.AsyncRequest;
import com.dexels.navajo.script.api.LocalClient;
import com.dexels.navajo.script.api.TmlRunnable;
import com.dexels.navajo.server.listener.http.impl.BaseServiceRunner;

public class TmlStandardRunner extends BaseServiceRunner implements TmlRunnable {

	private HashMap<String, Object> attributes = new HashMap<String, Object>();
	

	
	public TmlStandardRunner(AsyncRequest asyncRequest, LocalClient lc) {
		super(asyncRequest,lc);
		
//		this.inputDoc = inputDoc;
		attributes.put("httpRequest", asyncRequest.getHttpRequest());
	}

	public int getActiveCount() {
		return -1;
	}

	@Override
	public void endTransaction() throws IOException {
		getRequest().endTransaction();
	}

	@Override
	public Navajo getInputNavajo() throws IOException {
		return getRequest().getInputDocument();
	}

	@Override
	public String getUrl() {
		return getRequest().getUrl();
	}





}

/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.script.api;

import java.io.IOException;

import javax.servlet.ServletRequest;

import com.dexels.navajo.document.Navajo;

public interface AsyncRequest {

	public static final String COMPRESS_GZIP = "gzip";
	public static final String COMPRESS_JZLIB = "jzlib";
//	public static final String COMPRESS_NONE = "";

	public ClientInfo createClientInfo(long scheduledAt, long startedAt,
			int queueLength, String queueId);

	public Object getCert();
	
	public void writeOutput(Navajo inDoc, Navajo outDoc, long scheduledAt,
			long startedAt, String threadStatus) throws IOException;

	public void fail(Exception e);

	public void endTransaction() throws IOException;

	public Navajo getInputDocument();

	public ServletRequest getHttpRequest();

	public long getConnectedAt();

	public String getUrl();
	
	public String getIpAddress();

	public String getInstance();

}

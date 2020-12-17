/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.listener.http.queuemanager.api;

public interface QueueResponse {
	
	public static final String ACCEPT = "accept";
	public static final String REFUSE = "refuse";
	
	public void setResponse(String status);
	public void setQueueName(String queueName);
	public void cacheDecision(long millis);
}

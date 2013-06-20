package com.dexels.navajo.camel.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.script.api.AsyncRequest;
import com.dexels.navajo.script.api.ClientInfo;
import com.dexels.navajo.script.api.LocalClient;

public class LocalClientProcessor implements Processor {

	private LocalClient localClient;

	@Override
	public void process(Exchange ex) throws Exception {
	      int queueSize = -1;
	      String queueId = "camel";
	      long scheduledAt = (Long) ex.getProperty("scheduledAt");
	      Navajo in = (Navajo) ex.getIn().getBody();
	      AsyncRequest ar = (AsyncRequest) ex.getProperty("asyncRequest");
	      
		  ClientInfo clientInfo = ar.createClientInfo(scheduledAt, System.currentTimeMillis(), queueSize, queueId);
		  Navajo outDoc = getLocalClient().handleInternal("default",in, ar.getCert(), clientInfo);
		  ex.getOut().setBody(outDoc);
	}

	public LocalClient getLocalClient() {
		return localClient;
	}

	public void setLocalClient(LocalClient localClient) {
		this.localClient = localClient;
	}

	public void clearLocalClient(LocalClient localClient) {
		this.localClient = null;
	}
}

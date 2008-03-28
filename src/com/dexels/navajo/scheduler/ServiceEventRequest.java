package com.dexels.navajo.scheduler;

import com.dexels.navajo.server.enterprise.tribe.Answer;
import com.dexels.navajo.server.enterprise.tribe.Request;

/**
 * Use this request class to for synchronous waiting for before webservice events, since
 * the semenatics of a before webservice forces it to be synchronous...
 * 
 * @author arjen
 *
 */
public class ServiceEventRequest extends Request {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8857456245645813580L;

	private WebserviceTrigger myBwt = null;
	
	public ServiceEventRequest(WebserviceTrigger bwt) {
		this.myBwt = bwt;
	}
	
	@Override
	public Answer getAnswer() {
		
		ServiceEventAnswer bsa = new ServiceEventAnswer(this);
		
		return bsa;
	}

	public WebserviceTrigger getMyBwt() {
		return myBwt;
	}

}

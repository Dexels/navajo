package com.dexels.navajo.tribe;

import com.dexels.navajo.scheduler.BeforeWebserviceTrigger;

/**
 * Use this request class to for synchronous waiting for before webservice events, since
 * the semenatics of a before webservice forces it to be synchronous...
 * 
 * @author arjen
 *
 */
public class BeforeServiceEventRequest extends Request {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8857456245645813580L;

	private BeforeWebserviceTrigger myBwt = null;
	
	public BeforeServiceEventRequest(BeforeWebserviceTrigger bwt) {
		this.myBwt = bwt;
	}
	
	@Override
	public Answer getAnswer() {
		
		BeforeServiceEventAnswer bsa = new BeforeServiceEventAnswer(this);
		
		return bsa;
	}

	public BeforeWebserviceTrigger getMyBwt() {
		return myBwt;
	}

}

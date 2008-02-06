package com.dexels.navajo.tribe.map;

import com.dexels.navajo.tribe.Answer;
import com.dexels.navajo.tribe.Request;

public class IntroductionRequest extends Request {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3677395808956459975L;
	
	private SharedTribalMap stm = null;
	
	public IntroductionRequest(SharedTribalMap stm) {
		super.blocking = false;
		this.stm = stm;
	}
	
	@Override
	public Answer getAnswer() {
		System.err.println("Registering SharedTribalMap: " + stm.getId());
		SharedTribalMap.registerMap(stm);
		return null;
	}

}

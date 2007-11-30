package com.dexels.navajo.tribe;

import com.dexels.navajo.document.Navajo;

public class ServiceRequest extends Request {

	private static final long serialVersionUID = 2821719303061929705L;
	
	private final Navajo request;
	
	public ServiceRequest(Navajo request) {
		super();
		this.request = request;
	}
	
	public Answer getAnswer() {
		return new ServiceAnswer(this);
	}

	public Navajo getRequest() {
		return request;
	}

}

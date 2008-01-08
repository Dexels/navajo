package com.dexels.navajo.tribe;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.server.Dispatcher;

public class ServiceRequest extends Request {

	private static final long serialVersionUID = 2821719303061929705L;
	
	private final Navajo request;
	
	public ServiceRequest(Navajo request) {
		super();
		this.request = request;
	}
	
	public Answer getAnswer() {
		
		String origin = request.getHeader().getHeaderAttribute("origin");
		if ( origin != null && !origin.equals("")) {
			if ( origin.equals(Dispatcher.getInstance().getNavajoConfig().getInstanceName())) {
				System.err.println("IGNORING BROADCAST FROM MYSELF................");
				return null;
			}
		}
		return new ServiceAnswer(this);
	}

	public Navajo getRequest() {
		return request;
	}

}

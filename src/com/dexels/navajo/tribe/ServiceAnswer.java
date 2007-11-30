package com.dexels.navajo.tribe;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.server.Dispatcher;
import com.dexels.navajo.server.FatalException;

public class ServiceAnswer extends Answer {

	private static final long serialVersionUID = 2022715399366921183L;
	
	private Navajo response;
	private boolean hasError;
	private String errorMessage;
	
	public ServiceAnswer(ServiceRequest q) {
		super(q);
		try {
			response = Dispatcher.getInstance().handle(q.getRequest());
		} catch (FatalException e) {
			hasError = true;
			errorMessage = e.getMessage();
		}
	}

	public boolean acknowledged() {
		return true;
	}

	public Navajo getResponse() {
		return response;
	}

	public void setResponse(Navajo response) {
		this.response = response;
	}

	public boolean isHasError() {
		return hasError;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

}

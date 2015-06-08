package com.dexels.navajo.document.base;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.document.Operation;

public class BaseOperationImpl extends BaseNode implements Operation {

	private static final long serialVersionUID = -8365703860293478856L;

	protected String method;
	protected String service;
	protected String validationService;
	protected String entityName;
	protected Message extraMessage;
	protected boolean debugInput;
	protected boolean debugOutput;

	public BaseOperationImpl(Navajo n) {
		super(n);
	}

	@Override
	public void setMethod(String method) {
		this.method = method;
	}

	@Override
	public String getMethod() {
		return method;
	}

	@Override
	public void setService(String service) {
		this.service = service;
	}

	@Override
	public String getService() {
		return service;
	}

	@Override
	public void setEntityName(String entity) {
		this.entityName = entity;
	}

	@Override
	public String getEntityName() {
		return entityName;
	}

	@Override
	public Map<String, String> getAttributes() {
		 Map<String,String> m = new HashMap<String,String>();
	      m.put("method", method);
	      m.put("service", service);
	      m.put("validationService", validationService);
	      m.put("entity", entityName);
	      return m;
	}

	@Override
	public List<? extends BaseNode> getChildren() {
		if ( extraMessage == null ) {
			return null;
		}
		List<BaseNode> children =  new ArrayList<BaseNode>();
		children.add((BaseMessageImpl )extraMessage);
		return children;
	}

	@Override
	public String getTagName() {
		return "operation";
	}

	@Override
	public void setExtraMessage(Message extra) {
		this.extraMessage = extra;
	}

	@Override
	public Message getExtraMessage() {
		return extraMessage;
	}

	@Override
	public Operation copy(Navajo n) {
		BaseOperationImpl m = (BaseOperationImpl) NavajoFactory.getInstance().createOperation(n,
				getMethod(), getService(), getValidationService(), getEntityName(), getExtraMessage().copy());
	    return m;
	}

	@Override
	public void setValidationService(String service) {
		this.validationService = service;
		
	}

	@Override
	public String getValidationService() {
		return validationService;
	}

	@Override
    public void setDebug(String debugString) {
        if (debugString.trim().equals("true")) {
            debugInput = true;
            debugOutput = true;
            return;
        }
        if (debugString.contains("request")) {
            debugInput = true;
        }
        if (debugString.contains("response")) {
            debugOutput = true;
        }
    }
	

    @Override
    public boolean debugInput() {
        return debugInput;
    }

    @Override
    public boolean debugOutput() {
        return debugOutput;
    }
}

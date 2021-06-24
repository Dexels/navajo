/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.document.base;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.document.Operation;

public class BaseOperationImpl extends BaseNode implements Operation {

	private static final long serialVersionUID = -8365703860293478856L;

	protected String method;
	protected String service;
	protected String tenant;
	protected String validationService;
	protected String entityName;
	protected String description;
	protected Message extraMessage;
	protected boolean debugInput;
	protected boolean debugOutput;

    private Set<String> scopes= new HashSet<>();

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
		 Map<String,String> m = new HashMap<>();
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
		List<BaseNode> children =  new ArrayList<>();
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
		return NavajoFactory.getInstance().createOperation(n,
				getMethod(), getService(), getValidationService(), getEntityName(), getExtraMessage().copy());
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
    public void setScopes(String scopesString) {
        if (scopesString != null) {
            String[] arr = scopesString.split(",");
            for (String element : arr) {
                this.scopes.add(element);
            }
        }
    }

	@Override
    public void setScopes( Set<String> newScopes) {
        if (newScopes != null) {
            this.scopes = newScopes;
        }
    }
	
	@Override
	public  Set<String> getScopes() {
	    return scopes;
	}
	

    @Override
    public boolean debugInput() {
        return debugInput;
    }

    @Override
    public boolean debugOutput() {
        return debugOutput;
    }

    @Override
    public void setTenant(String tenant) {
        this.tenant = tenant; 
    }

    @Override
    public String getTenant() {
        return tenant;
    }

    @Override
    public void setDescription(String description) {
        this.description = description;
        
    }

    @Override
    public String getDescription() {
        return description;
    }
}

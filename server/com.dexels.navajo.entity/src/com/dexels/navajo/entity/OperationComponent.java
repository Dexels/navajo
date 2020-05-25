package com.dexels.navajo.entity;

import java.util.Map;
import java.util.Set;

import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.document.Operation;
import com.dexels.navajo.document.Property;

public class OperationComponent implements Operation {

    private String method;
    private String service;
    private String entityName;
    private String description;
    private String validationService;
    protected String tenant;
    private Message extraMessage;
    private boolean debugInput;
    private boolean debugOutput;
    private Set<String> scopes;
    private String clubModules;
    private String userModules;

    public void activateComponent(Map<String, Object> parameters) throws Exception {
        method = (String) parameters.get("operation.method");
        service = (String) parameters.get("operation.service");
        validationService = (String) parameters.get("operation.validationService");
        entityName = (String) parameters.get("operation.entity");
        if (parameters.get("operation.clubModules") != null) {
        	clubModules = (String) parameters.get("operation.clubModules");
        }
        if (parameters.get("operation.userModules") != null) {
        	userModules = (String) parameters.get("operation.userModules");
        }
        // How to fetch Message from this??
        if (parameters.get("operation.extramessage") != null) {
            extraMessage = (Message) parameters.get("operation.extramessage");
        }
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

    public void setValidationService(String service) {
        this.validationService = service;

    }

    public String getValidationService() {
        return validationService;
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
    public void setExtraMessage(Message extra) {
        this.extraMessage = extra;
    }

    @Override
    public Message getExtraMessage() {
        if (extraMessage == null) {
            Navajo n = NavajoFactory.getInstance().createNavajo();
            Message m = NavajoFactory.getInstance().createMessage(n, "__OPERATION__");
            Property p = NavajoFactory.getInstance().createProperty(n, "Method", Property.STRING_PROPERTY, method, 0, "", "");
            Property p2 = NavajoFactory.getInstance()
                    .createProperty(n, "Entity", Property.STRING_PROPERTY, entityName, 0, "", "");
            m.addProperty(p);
            m.addProperty(p2);
            n.addMessage(m);
            return m;

        }
        return extraMessage;
    }

    @Override
    public Operation copy(Navajo n) {
        OperationComponent oc = new OperationComponent();
        oc.setMethod(this.method);
        oc.setService(this.service);
        oc.setValidationService(this.validationService);
        oc.setEntityName(this.entityName);
        oc.setExtraMessage(this.extraMessage.copy());
        return oc;
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
    public void setClubModules(String clubModules) {
        if (clubModules != null) {
            this.clubModules = clubModules;
        }
    }
	
	@Override
	public String getClubModules() {
	    return clubModules;
	}

	@Override
    public void setUserModules(String userModules) {
        if (userModules != null) {
        	this.userModules = userModules;
        }
    }
	
	@Override
	public String getUserModules() {
	    return userModules;
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

package com.dexels.navajo.article.command.impl;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.article.APIErrorCode;
import com.dexels.navajo.article.APIException;
import com.dexels.navajo.article.ArticleContext;
import com.dexels.navajo.article.ArticleRuntime;
import com.dexels.navajo.article.command.ArticleCommand;
import com.dexels.navajo.document.Header;
import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.document.Property;
import com.dexels.navajo.document.nanoimpl.XMLElement;
import com.dexels.navajo.script.api.AuthorizationException;
import com.dexels.navajo.script.api.FatalException;
import com.dexels.navajo.script.api.UserException;
import com.dexels.navajo.server.ConditionErrorException;
import com.dexels.navajo.server.DispatcherInterface;
import com.dexels.navajo.server.impl.GlobalManagerImpl;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;

public class ServiceCommand implements ArticleCommand {
    private static final Logger statLogger = LoggerFactory.getLogger("stats");
    private static final Logger logger = LoggerFactory.getLogger(ServiceCommand.class);
    
	private String name;
    private DispatcherInterface dispatcher;
	
    public void setDispatcher(DispatcherInterface di) {
        this.dispatcher = di;
    }
    
    public void clearDispatcher(DispatcherInterface di) {
        this.dispatcher = null;
    }
    
	public ServiceCommand() {
		// default constructor
	}

	// for testing, no need to call activate this way
	public ServiceCommand(String name) {
		this.name = name;
	}

	public void activate(Map<String, String> settings) {
		this.name = settings.get("command.name");
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public JsonNode execute(ArticleRuntime runtime, ArticleContext context,
			Map<String, String> parameters, XMLElement element)
			throws APIException {
	    Long startedAt = System.currentTimeMillis();
	    
		String name = parameters.get("name");
		if (name == null) {
			throw new APIException("Command: " + this.getName()
					+ " can't be executed without required parameters: " + name, null, APIErrorCode.InternalError);
		}
		String refresh = parameters.get("refresh");
		if ("false".equals(refresh)) {
			Navajo res = runtime.getNavajo(name);
			if (res != null) {
				runtime.pushNavajo(name, res);
				return null;
			}
		}
		String input = parameters.get("input");
		Navajo n = null;
		if (input != null) {
			n = runtime.getNavajo(input);
			if (n == null) {
				throw new APIException("Command: " + this.getName()
						+ " supplies an 'input' parameter: " + input
						+ ", but that navajo object can not be found" + name, null, APIErrorCode.InternalError);
			}
		}
		if (runtime.getNavajo() != null) {
			n = runtime.getNavajo();
		} else {
			n = NavajoFactory.getInstance().createNavajo();
		}
		appendTokenAttributes(runtime, n);
		final String username = runtime.getUsername();
		Header h = NavajoFactory.getInstance().createHeader(n, name, username, "", -1);
		if (runtime.getAccess() != null) {
		      h.setHeaderAttribute("parentaccessid", runtime.getAccess().accessID);
		}
		n.addHeader(h);
        h.setHeaderAttribute("application", "article");
		final Navajo result = performCall(runtime, name, n, runtime.getInstance());
		statLogger.info("Finished {} ({}) in {}ms", h.getHeaderAttribute("parentaccessid"), name,
                 (System.currentTimeMillis() - startedAt));
		runtime.pushNavajo(name, result);
		return null;
	}

	private void appendTokenAttributes(ArticleRuntime runtime, Navajo n) {
	    Map<String, Object> extraParams = new HashMap<String, Object>();
	    if (runtime.getToken() != null && runtime.getToken().getUser() != null) {
	        extraParams.put("USERID", runtime.getToken().getUser().getUserId());
	        extraParams.put("USERNAME", runtime.getToken().getUser().getUsername());
	    } else {
	        extraParams.put("USERID", -1);
	        extraParams.put("USERNAME", runtime.getUsername());
	    }
        extraParams.put("TENANT", runtime.getInstance());
        extraParams.put("PERSONID", "");
        extraParams.put("DOMAIN", "");
        extraParams.put("UNIONID", "");
        GlobalManagerImpl.appendMapToAAA(n, extraParams);
        
        if (n.getMessage(Message.MSG_TOKEN_BLOCK) == null) {
            Message tokenMsg = NavajoFactory.getInstance().createMessage(n,Message.MSG_TOKEN_BLOCK);
            n.addMessage(tokenMsg);
        }
        Message tokenMsg = n.getMessage(Message.MSG_TOKEN_BLOCK);
        
        // Add attributes
        for (String key : runtime.getUserAttributes().keySet()) {
            Object value = runtime.getUserAttributes().get(key);
            Property p2 = NavajoFactory.getInstance().createProperty(n, key, "", "", Property.DIR_OUT);
            p2.setAnyValue(value);
            tokenMsg.addProperty(p2);
        }
    }

    protected Navajo performCall(ArticleRuntime runtime, String name, Navajo n, String instance) throws APIException {
     
        try {
            Navajo result =  dispatcher.handle(n, instance, true);
            handleError(result);
            return result;
        } catch (UserException | AuthorizationException | FatalException e) {
            throw new APIException(e.getMessage(), e, APIErrorCode.InternalError);
        } catch (ConditionErrorException e) {
            throw new APIException(e.getMessage(), e, APIErrorCode.ConditionError);
        }       
    }

	private void handleError(Navajo result) throws UserException,
			AuthorizationException, ConditionErrorException {
		Message error = result.getMessage("error");
		  if (error != null) {
		      String errMsg = error.getProperty("message").getValue();
		      String errCode = error.getProperty("code").getValue();
		      int errorCode = -1;
		          try {
					errorCode = Integer.parseInt(errCode);
				} catch (NumberFormatException e) {
					logger.error("Error: ", e);
				}
		      throw new UserException(errorCode, errMsg);
		  }

		  boolean authenticationError = false;
		  Message aaaError = result.getMessage(AuthorizationException.AUTHENTICATION_ERROR_MESSAGE);
		  if (aaaError == null) {
		    aaaError = result.getMessage(AuthorizationException.AUTHORIZATION_ERROR_MESSAGE);
		  } else {
		    authenticationError = true;
		  }
		  if (aaaError != null) {
		    throw new AuthorizationException(authenticationError, !authenticationError,
		                                     aaaError.getProperty("User").getValue(),
		                                     aaaError.getProperty("Message").getValue());
		  }

		  if ( result.getMessage("ConditionErrors") != null) {
		      throw new ConditionErrorException(result);
		  }
	}



	@Override
	public boolean writeMetadata(XMLElement e, ArrayNode outputArgs,
			ObjectMapper mapper) {
		return false;
	}
}

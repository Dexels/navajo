package com.dexels.navajo.article.command.impl;

import java.util.HashMap;
import java.util.Map;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ArrayNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.article.ArticleContext;
import com.dexels.navajo.article.ArticleException;
import com.dexels.navajo.article.ArticleRuntime;
import com.dexels.navajo.article.command.ArticleCommand;
import com.dexels.navajo.client.ClientInterface;
import com.dexels.navajo.client.NavajoClientFactory;
import com.dexels.navajo.document.Header;
import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.document.nanoimpl.XMLElement;
import com.dexels.navajo.script.api.AuthorizationException;
import com.dexels.navajo.script.api.LocalClient;
import com.dexels.navajo.script.api.UserException;
import com.dexels.navajo.server.ConditionErrorException;

public class ServiceCommand implements ArticleCommand {
    private final static Logger statLogger = LoggerFactory.getLogger("stats");
    private final static Logger logger = LoggerFactory.getLogger(ServiceCommand.class);
    
	private String name;
	private LocalClient localClient;
	private final Map<String, LocalClient> instanceClients = new HashMap<String, LocalClient>();

	
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
			throws ArticleException {
	    Long startedAt = System.currentTimeMillis();
	    
		String name = parameters.get("name");
		if (name == null) {
			throw new ArticleException("Command: " + this.getName()
					+ " can't be executed without required parameters: " + name);
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
				throw new ArticleException("Command: " + this.getName()
						+ " supplies an 'input' parameter: " + input
						+ ", but that navajo object can not be found" + name);
			}
		}
		if (runtime.getNavajo() != null) {
			n = runtime.getNavajo();
		} else {
			n = NavajoFactory.getInstance().createNavajo();
		}
		final String username = runtime.getUsername();
		Header h = NavajoFactory.getInstance().createHeader(n, name, username,
				runtime.getPassword(), -1);
		n.addHeader(h);
		final Navajo result = performCall(runtime, name, n, runtime.getInstance());
		statLogger.info("Finished {} ({}) in {}ms", h.getHeaderAttribute("accessId"), name,
                 (System.currentTimeMillis() - startedAt));
		runtime.pushNavajo(name, result);
		return null;
	}

	protected Navajo performCall(ArticleRuntime runtime, String name, Navajo n, String instance) throws ArticleException {
        try {
            Navajo result = null;
            if (runtime.getURL() != null && !runtime.getURL().equals("")) {
                ClientInterface client = NavajoClientFactory.getClient();
                client.setServerUrl(runtime.getURL());
                client.setUsername(n.getHeader().getRPCUser());
                client.setPassword(n.getHeader().getRPCPassword());
                client.setRetryAttempts(0);
                 
                result = client.doSimpleSend(n, name);

            } else {
                if (localClient == null) {
                    throw new ArticleException("Navajo server not (yet?) initialized");
                }
                result = localClient.call(instance, n);
            }

            try {
                handleError(result);
            } catch (UserException e) {
                throw new ArticleException("Error calling service", e);
            } catch (AuthorizationException e) {
                throw new ArticleException("Error calling service", e);
            } catch (ConditionErrorException e) {
                throw new ArticleException("Error calling service", e);
            }
            return result;
        } catch (Exception e) {
            throw new ArticleException("Error calling service: " + name, e);
        }
    }

	private void handleError(Navajo result) throws UserException,
			AuthorizationException, ConditionErrorException {
		result.write(System.err);
		Message error = result.getMessage("error");
		  if (error != null  ) {
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

	public void removeLocalClient(LocalClient localClient, Map<String, String> setting) {
		if (setting != null) {
			String instance = setting.get("instance");
			if (instance == null) {
				this.localClient = null;
			} else {
				instanceClients.remove(instance);
			}
		} else {
			this.localClient = localClient;
		}

	}

	public void addLocalClient(LocalClient localClient,
			Map<String, String> setting) {
		if (setting != null) {
			String instance = setting.get("instance");
			if (instance == null) {
				this.localClient = localClient;
			} else {
				instanceClients.put(instance, localClient);
			}
		} else {
			this.localClient = localClient;
		}
	}

	@Override
	public boolean writeMetadata(XMLElement e, ArrayNode outputArgs,
			ObjectMapper mapper) {
		return false;
	}
}

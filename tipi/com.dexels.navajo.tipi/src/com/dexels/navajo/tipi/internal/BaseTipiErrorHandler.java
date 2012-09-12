package com.dexels.navajo.tipi.internal;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.MissingResourceException;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoException;
import com.dexels.navajo.document.Property;
import com.dexels.navajo.tipi.TipiContext;
import com.dexels.navajo.tipi.TipiErrorHandler;

//import com.dexels.navajo.document.nanoimpl.*;
/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2003
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author not attributable
 * @version 1.0
 */
public class BaseTipiErrorHandler implements TipiErrorHandler, Serializable {

	private static final long serialVersionUID = -2568512270962339576L;
	private String errorMessage;
	private TipiContext context;
	private transient ResourceBundle errorMessageBundle;
	
	private final static Logger logger = LoggerFactory
			.getLogger(BaseTipiErrorHandler.class);
	
	public BaseTipiErrorHandler() {
		// initResource();
	}

	public void initResource() {
		try {

			InputStream tipiResourceStream = context
					.getTipiResourceStream("validation.properties");
			if (tipiResourceStream == null) {
				return;
			}
			errorMessageBundle = new PropertyResourceBundle(tipiResourceStream);
		} catch (Exception ex) {
			logger.warn("No validation bundle found. No problem.");
			errorMessageBundle = null;
		}
	}

	public String hasErrors(Navajo n) {
		if (n != null) {
			Message error = n.getMessage("error");
			Message conditions = n.getMessage("ConditionErrors");
			Message authentication = n.getMessage("AuthenticationError");
			Message authorization = n.getMessage("AuthorizationError");
			
			if (error != null) {
				errorMessage = error.getProperty("message").getValue();
				return errorMessage;
			} else if (conditions != null) {
				ArrayList<Message> failures = conditions.getAllMessages();
				errorMessage = "";
				for (int i = 0; i < failures.size(); i++) {
					Message current = failures.get(i);
					// String expression =
					// current.getProperty("FailedExpression").getValue();
					String id = current.getProperty("Id").getValue();
					errorMessage = errorMessage
							+ getConditionErrorDescription(id, current) + "\n";
					try {
						conditions.write(System.err);
					} catch (NavajoException e) {
						logger.error("Error: ",e);
					}
				}
				return errorMessage;
			} else if(authentication!=null) {
				errorMessage = "Authentication error: "+authentication.getProperty("Message").getTypedValue()+" user: "+authentication.getProperty("User").getTypedValue()
						+" webservice: "+authentication.getProperty("Webservice").getTypedValue();
				return errorMessage;
			} else if(authorization!=null) {
				errorMessage = "Authorization error: "+authorization.getProperty("Message").getTypedValue()+" user: "+authorization.getProperty("User").getTypedValue()
						+" webservice: "+authorization.getProperty("Webservice").getTypedValue();
				return errorMessage;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	private String getConditionErrorDescription(String id, Message current) {
		// String description = id;
		try {
			if(errorMessageBundle==null) {
				initResource();
			}
			String found = errorMessageBundle.getString(id);
			if (found != null) {
				return found;
			}
		} catch (MissingResourceException ex) {
			System.err
					.println("----> Cannot find reference for condition errorId: "
							+ id);
		}
		Property description = current.getProperty("Description");
		if (description != null) {
			return "" + description.getTypedValue();
		}
		return id;
	}

	public void setContext(TipiContext c) {
		context = c;
		if (errorMessageBundle == null) {
			// attempt remote propertyresource bundle;
			try {
				InputStream tipiResourceStream = c
						.getTipiResourceStream("validation.properties");
				if (tipiResourceStream != null) {
					errorMessageBundle = new PropertyResourceBundle(
							tipiResourceStream);
				} else {
				}
			} catch (IOException e) {
				logger.warn("Getting validation.properties from server failed.");
			}
		}
	}

	public TipiContext getContext() {
		return context;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

}

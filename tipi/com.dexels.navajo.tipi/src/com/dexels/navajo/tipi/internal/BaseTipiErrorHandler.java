package com.dexels.navajo.tipi.internal;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.List;
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
    private final static Logger logger = LoggerFactory.getLogger(BaseTipiErrorHandler.class);
    private static final String ERROR_MESSAGE = "Error_click_details";
    private static final String ERROR_TITLE = "Error_click_details_title";
    private static final String ERROR_MORE_DETAILS = "Details";
    private static final String ERROR_LESS_DETAILS = "LessDetails";
    private static final String ERROR_OK = "Ok";
	private static final long serialVersionUID = -2568512270962339576L;
	
	private String errorMessage;
	private TipiContext context;
	private transient ResourceBundle errorMessageBundle;
	
	public BaseTipiErrorHandler() {
		// initResource();
	}

	@Override
	public Boolean hasServerErrors(Navajo n) {
	    return n.getMessage("error") != null;
	}

	@Override
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
			    List<Message> failures = conditions.getAllMessages();
				errorMessage = "";
				for (int i = 0; i < failures.size(); i++) {
					Message current = failures.get(i);
					// String expression =
					// current.getProperty("FailedExpression").getValue();
					String id = current.getProperty("Id").getValue();
					errorMessage = errorMessage + getConditionErrorDescription(id, current) + "\n";
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
	
	public String getGenericErrorDescription() {
        // String description = id;
	    String s = retrieveFromMessageBundle(ERROR_MESSAGE);
        if (s != null) {
            return s;
        }
        return "An error occured. ";
    }
	
	public String getGenericErrorTitle() {
        String s = retrieveFromMessageBundle(ERROR_TITLE);
        if (s != null) {
            return s;
        }
        return "Error: ";
    }

    private String retrieveFromMessageBundle(String key) {
        try {
            if(errorMessageBundle==null) {
                logger.error("Serious problem in Error handler - can't retrieve genericErrorTitle.");
            } else {
                String found = errorMessageBundle.getString(key);
                if (found != null) {
                    return found;
                }
            }
        } catch (MissingResourceException ex) {
            logger.warn("Cannot find reference for  {}", ERROR_TITLE);
        }
        return null;
    }
	
	public String getErrorMoreDetailsText() {
        // String description = id;
	    String s = retrieveFromMessageBundle(ERROR_MORE_DETAILS);
        if (s != null) {
            return s;
        }
        return "Details";
    }
	
	public String getErrorLessDetailsText() {
        // String description = id;
        String s = retrieveFromMessageBundle(ERROR_LESS_DETAILS);
        if (s != null) {
            return s;
        }
        return "Minder details";
    }
	
	public String getErrorCloseText() {
        // String description = id;
	    String s = retrieveFromMessageBundle(ERROR_OK);
        if (s != null) {
            return s;
        }
        return "OK";
    }
	
	

	private String getConditionErrorDescription(String id, Message current) {
		// String description = id;
	    String found = retrieveFromMessageBundle(id);
	    if (found != null) {
            return found;
        }
	    
		Property description = current.getProperty("Description");
		if (description != null) {
			return "" + description.getTypedValue();
		}
		return id;
	}

	@Override
	public void setContext(TipiContext c) {
		context = c;
		String lcode = c.getApplicationInstance().getLocaleCode();
		if(!getValidationsForLocale(c, null)) {
			getValidationsForLocale(c, lcode);
		}
	}


	private boolean getValidationsForLocale(TipiContext c, String lcode) {
		String lcSpecificValidations = lcode ==null? "validation.properties" : "validation_" + lcode + ".properties";
		if (errorMessageBundle == null) {
			// attempt remote propertyresource bundle;
			try {
				InputStream tipiResourceStream = null;
				tipiResourceStream = c.getTipiResourceStream(lcSpecificValidations);
				if ( tipiResourceStream == null ) {
					tipiResourceStream = c.getTipiResourceStream("validation.properties");
				}
				if (tipiResourceStream != null) {
					errorMessageBundle = new PropertyResourceBundle(
							tipiResourceStream);
				} else {
					logger.error("Getting validation.properties from server failed. Is validation.properties in *resources*, not *tipi*?");
				}
				return true;
			} catch (IOException e) {
				logger.error("Getting validation.properties from server failed. Is validation.properties in *resources*, not *tipi*?",e);
				return false;
			}
		}
		return true;
	}

	@Override
	public TipiContext getContext() {
		return context;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

}

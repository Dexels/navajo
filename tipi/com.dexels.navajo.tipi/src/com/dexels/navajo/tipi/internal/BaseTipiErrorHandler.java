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
    private static final int VALIDATIONPROPERTIES_RETRY_INTERVAL = 30000;
    private static final long serialVersionUID = -2568512270962339576L;
    private final static Logger logger = LoggerFactory.getLogger(BaseTipiErrorHandler.class);
    
    private static final String ERROR_MESSAGE = "Error_click_details";
    private static final String ERROR_TITLE = "Error_click_details_title";
    private static final String ERROR_MORE_DETAILS = "Details";
    private static final String ERROR_LESS_DETAILS = "LessDetails";
    private static final String ERROR_OK = "Ok";
    private static final String INACTIVITY_MSG = "Inactive_msg";
    private static final String INACTIVITY_TITLE =  "Inactive_title";
	
	private String errorMessage;
	private TipiContext context;
	private transient ResourceBundle errorMessageBundle;
    private Thread retrieveValidationThread;
    private volatile boolean keepValidationRunning = false;
	
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
            if (key == null || !"-1".equals(key))
            {   // Don't error on -1, this is probably meant to not be found
                logger.warn("Cannot find reference for {}", key);
            }
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
		if (c == null) {
		    logger.warn("Null tipi context!", new Exception());
		    return;
		}
        retrieveValidationThread = new Thread(new Runnable() {
            private int attemptsLeft = 30;
            @Override
            public void run() {
                // Get validation.properties from the remote server.
                // If this fails for whatever reason, keep trying to see if we can
                // retrieve it at a later moment.
                getRemoteValidationProperties();
                
                while (errorMessageBundle == null && attemptsLeft > 0 && keepValidationRunning) {
                    logger.info("Retrying retrieval of remote validation.properties...");
                    getRemoteValidationProperties();
                    try {
                        Thread.sleep(VALIDATIONPROPERTIES_RETRY_INTERVAL);
                    } catch (InterruptedException e) {
                        keepValidationRunning = false;
                        break;
                    }
                    attemptsLeft--;
                }
               
            }
        });
        retrieveValidationThread.start();
	}
	
	@Override
    public void removeContext(TipiContext c) {
	    keepValidationRunning = false;
	    retrieveValidationThread.interrupt();
	    retrieveValidationThread = null;
	    context = null;
	}


	private void getRemoteValidationProperties() {
	    if (context == null) {
	        // This is weird
	        logger.warn("Null context in retrieving remote validation - stopping");
	        return;
	    }
	    final String lcode;
	    final String url;
	    final String union;
	    try {
	        lcode = context.getApplicationInstance().getLocaleCode();
	        url = context.getSystemProperty("tipi.resourceurl");
	        union = context.getSystemProperty("tipi.profile");
	    } catch (Throwable t) {
	        logger.error("Exception in getting critical properties! stopping", t);
	        return;
	    }
       
        
        if (url == null) {
            logger.warn("Empty url for tipi.resourceurl - cannot load validation.properties");
            return;
        }
        
        RemoteValidationPropertiesHandler remoteHandler = new RemoteValidationPropertiesHandler(url, union, lcode);
        InputStream tipiResourceStream = remoteHandler.getContents() ;
        
		if (errorMessageBundle == null) {
			try {				
				if (tipiResourceStream!= null) {
					errorMessageBundle = new PropertyResourceBundle(tipiResourceStream);
					logger.info("Retrieved validation.properties from server");
				} else {
					logger.warn("Empty inputstream - cannot retrieve validation.properties");
				}
			} catch (IOException e) {
				logger.error("IOException creating PropertyResourceBundle from remote validation.properties",e);
			}
		}
	}

	@Override
	public TipiContext getContext() {
		return context;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

    @Override
    public String getInactivityTitleText() {
        String s = retrieveFromMessageBundle(INACTIVITY_TITLE);
        if (s != null) {
            return s;
        }
        return "De applicatie wordt afgesloten ";
    }

    @Override
    public String getInactivityMsgText() {
        String s = retrieveFromMessageBundle(INACTIVITY_MSG);
        if (s != null) {
            return s;
        }
        return "De applicatie wordt afgesloten vanwege inactiviteit.";
    }

   
}

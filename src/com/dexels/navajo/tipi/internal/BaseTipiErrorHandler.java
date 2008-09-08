package com.dexels.navajo.tipi.internal;

import java.io.*;
import java.util.*;

import com.dexels.navajo.document.*;
import com.dexels.navajo.tipi.*;

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
public class BaseTipiErrorHandler implements TipiErrorHandler {
	private String  errorMessage;
	private TipiContext context;
	private ResourceBundle errorMessageBundle;

	public BaseTipiErrorHandler() {
//		initResource();
	}

	public void initResource() {
		try {
			errorMessageBundle = new PropertyResourceBundle( context.getTipiResourceStream("validation.properties"));
//			errorMessageBundle = ResourceBundle.getBundle("tipi.validation");
		} catch (Exception ex) {
			ex.printStackTrace();
			System.err.println("No validation bundle found.");
			errorMessageBundle = null;
		}
	}

	public String hasErrors(Navajo n) {
		if (n != null) {
			Message error = n.getMessage("error");
			Message conditions = n.getMessage("ConditionErrors");
			if (error != null) {
				errorMessage = error.getProperty("message").getValue();
				return errorMessage;
			} else if (conditions != null) {
				ArrayList<Message> failures = conditions.getAllMessages();
				errorMessage = "";
				for (int i = 0; i < failures.size(); i++) {
					Message current = failures.get(i);
//					String expression = current.getProperty("FailedExpression").getValue();
					String id = current.getProperty("Id").getValue();
					errorMessage = errorMessage + getConditionErrorDescription(id,current) + "\n";
					// current.write(System.err);

				}
				// System.err.println("Message: "+errorMessage);
				// Thread.dumpStack();
				return errorMessage;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	private String getConditionErrorDescription(String id, Message current) {
//		String description = id;
		try {
			if(errorMessageBundle!=null) {
				String found = errorMessageBundle.getString(id);
				if(found!=null) {
					return found;
				}
			}
		} catch (MissingResourceException ex) {
			System.err.println("----> Cannot find reference for condition errorId: " + id);
		}
		Property description = current.getProperty("Description");
		if(description!=null) {
			return ""+description.getTypedValue();
		}
		return id;
	}

	public void setContext(TipiContext c) {
		context = c;
		if(errorMessageBundle==null) {
			// attempt remote propertyresource bundle;
			try {
				InputStream tipiResourceStream = c.getTipiResourceStream("validation.properties");
				if(tipiResourceStream!=null) {
					errorMessageBundle = new PropertyResourceBundle(tipiResourceStream);
				} else {
//					System.err.println("Remote retrieve of validation.properties failed.");
				}
			} catch (IOException e) {
				System.err.println("Getting resource from server failed.");
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

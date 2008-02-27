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
public abstract class BaseTipiErrorHandler implements TipiErrorHandler {
	private String errorCode, errorMessage;
	private TipiContext context;
	private ResourceBundle res;

	public BaseTipiErrorHandler() {
		initResource();
	}

	private void initResource() {
		try {
			res = ResourceBundle.getBundle("tipi.validation");
			
		} catch (Exception ex) {
//			System.err.println("No tipi.validation resource found.");
			res = null;
		}
	}

	public boolean hasErrors(Navajo n) {
		if (n != null) {
			// System.err.println("Checking for errors");
			Message error = n.getMessage("error");
			Message conditions = n.getMessage("ConditionErrors");
			if (error != null) {
				errorCode = error.getProperty("code").getValue();
				errorMessage = error.getProperty("message").getValue();
				// System.err.println("Found:" + getErrorMessage());
				return true;
			} else if (conditions != null) {
				ArrayList<Message> failures = conditions.getAllMessages();
				errorMessage = "Conditionele fouten:\n";
				for (int i = 0; i < failures.size(); i++) {
					Message current = failures.get(i);
//					String expression = current.getProperty("FailedExpression").getValue();
					String id = current.getProperty("Id").getValue();
					errorMessage = errorMessage + getConditionErrorDescription(id) + "\n";
					// current.write(System.err);

				}
				// System.err.println("Message: "+errorMessage);
				// Thread.dumpStack();
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	private String getConditionErrorDescription(String id) {
		String description = id;
		try {
			if(res!=null) {
				String found = res.getString(id);
				description = found;
			}
		} catch (MissingResourceException ex) {
			System.err.println("----> Cannot find reference for condition errorId: " + id);
		}
		return description;
	}

	public void setContext(TipiContext c) {
		context = c;
		if(res==null) {
			// attempt remote propertyresource bundle;
			try {
				InputStream tipiResourceStream = c.getTipiResourceStream("validation.properties");
				if(tipiResourceStream!=null) {
					res = new PropertyResourceBundle(tipiResourceStream);
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

	public String getErrorCode() {
		return errorCode;
	}
}

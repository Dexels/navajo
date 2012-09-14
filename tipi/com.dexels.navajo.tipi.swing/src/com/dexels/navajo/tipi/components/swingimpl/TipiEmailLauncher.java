/*
 * Created on March 21, 2005 (it's springtime!!)
 *
 */
package com.dexels.navajo.tipi.components.swingimpl;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.Message;
import com.dexels.navajo.tipi.TipiComponentMethod;
import com.dexels.navajo.tipi.internal.TipiEvent;

/**
 * @author matthijs (!)
 * @deprecated should be replaced with an appropriate action
 * 
 */

@Deprecated
public class TipiEmailLauncher extends TipiSwingDataComponentImpl {

	private static final long serialVersionUID = -8942278089469288268L;
	
	private final static Logger logger = LoggerFactory
			.getLogger(TipiEmailLauncher.class);
	private Message recipient = null;

	protected void performComponentMethod(String name,
			TipiComponentMethod compMeth, TipiEvent event) {

		String propertyName = null;
		String emailSubject = null;
		String emailBody = null;

		if (name.equals("setEmailParameters")) {
			propertyName = (String) compMeth.getEvaluatedParameter(
					"propertyname", event).value;
			emailSubject = (String) compMeth.getEvaluatedParameter("subject",
					event).value;
			emailBody = (String) compMeth.getEvaluatedParameter("body", event).value;

			try {
				recipient = (Message) compMeth.getEvaluatedParameter(
						"messagepath", event).value;

				if (recipient == null) {
					logger.warn("TipiPersonEmail does not recieve a message as input! Aborting...");
					return;
				}
			} catch (Exception ex) {
				logger.debug("Could not find data in 'messagepath'! \n");
				logger.error("Error detected",ex);
				return;
			}
			createEmail(propertyName, emailSubject, emailBody);
		}
	}

	private void createEmail(String propertyName, String subject, String body) {

		String emailAddress;
		String emailString = "mailto:";
		boolean recipientsFound = false;

		try {

			for (int i = 0; i < recipient.getArraySize(); i++) {
				Message current = recipient.getMessage(i);
				emailAddress = current.getProperty(propertyName).getValue();
				if (emailAddress != null && emailAddress.trim() != "") {
					recipientsFound = true;
					emailString = emailString + emailAddress + ",";
				}
			}
			if (recipientsFound) {
				emailString = emailString.substring(0,
						(emailString.length() - 1));
				emailString = emailString + "?subject=" + subject + "&body="
						+ body;
				logger.debug("Generated email string: " + emailString);
				String cmd = "rundll32 url.dll,FileProtocolHandler "
						+ emailString;
				try {
					Runtime.getRuntime().exec(cmd);
				} catch (IOException ex) {
					logger.debug("Could not launch email (rundll32)");
				}
			} else {
				logger.warn("No recipients found that have an email address");
			}

		} catch (Exception e) {
			logger.error("Error detected",e);
		}
	}

	public Object createContainer() {
		return null;
	}

	public static void main(String[] args) {

	}

}
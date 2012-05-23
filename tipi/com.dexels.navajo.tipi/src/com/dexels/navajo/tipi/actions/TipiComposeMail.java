package com.dexels.navajo.tipi.actions;

import metadata.BinaryOpener;

import com.dexels.navajo.document.Message;
import com.dexels.navajo.tipi.internal.TipiAction;
import com.dexels.navajo.tipi.internal.TipiEvent;

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
public class TipiComposeMail extends TipiAction {

	private static final long serialVersionUID = -1491806387584733104L;

	public void execute(TipiEvent event)
			throws com.dexels.navajo.tipi.TipiException,
			com.dexels.navajo.tipi.TipiBreakException {
		String recipientProperty = (String) getEvaluatedParameter("recipientProperty", event).value;
		Message arrayList = (Message) getEvaluatedParameter("recipientList", event).value;
		String subject = (String) getEvaluatedParameter("subject", event).value;
		String body = (String) getEvaluatedParameter("body", event).value;
		createEmail(arrayList, recipientProperty, subject, body);
	}
	
	private void createEmail(Message recipient, String propertyName, String subject, String body) {

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
				System.err.println("Generated email string: " + emailString);
				BinaryOpener.displayURL(emailString);
			} else {
				System.err.println("No recipients found that have an email address");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}


}
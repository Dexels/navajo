/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.tipi.actions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.BinaryOpenerFactory;
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
	
	private final static Logger logger = LoggerFactory
			.getLogger(TipiComposeMail.class);
	
	@Override
	public void execute(TipiEvent event)
			throws com.dexels.navajo.tipi.TipiException,
			com.dexels.navajo.tipi.TipiBreakException {
		String recipientProperty = (String) getEvaluatedParameter("recipientProperty", event).value;
		String subject = (String) getEvaluatedParameter("subject", event).value;
		String body = (String) getEvaluatedParameter("body", event).value;
		String recipientsDelimiter = (String) getEvaluatedParameter("recipientsDelimiter", event).value;

		// Check if TO or BCC needs to be applied
		Message arrayList;
		String mailRecipientType = "TO";
		if (getEvaluatedParameter("recipientListBcc", event) != null && ((Message) getEvaluatedParameter("recipientListBcc", event).value).getArraySize() != 0) {
			mailRecipientType = "BCC";
			arrayList = (Message) getEvaluatedParameter("recipientListBcc", event).value;
		} else {
			arrayList = (Message) getEvaluatedParameter("recipientList", event).value;
		}
		
		createEmail(arrayList, recipientProperty, subject, body, recipientsDelimiter, mailRecipientType);
	}
	
	private void createEmail(Message recipient, String propertyName, String subject, String body, String recipientsDelimiter, String mailRecipientType) {

		String emailAddress;
		// "mailto:" is added by BinaryOpener
		String emailString = "";
		boolean recipientsFound = false;

		try {
			// Make sure there is a valid delimiter
			if ( recipientsDelimiter == null || ( !recipientsDelimiter.equals(",") && !recipientsDelimiter.equals(";") ) ) {
				recipientsDelimiter = ",";
			}

			for (int i = 0; i < recipient.getArraySize(); i++) {
				Message current = recipient.getMessage(i);
				emailAddress = current.getProperty(propertyName).getValue();
				if (emailAddress != null && !"".equals(emailAddress.trim())) {
					recipientsFound = true;
					emailString = emailString + emailAddress + recipientsDelimiter;
				}
			}
			if (recipientsFound) {
				emailString = emailString.substring(0, (emailString.length() - 1));
				String params = "subject=" + subject + "&body=" + body;
				if (mailRecipientType.equalsIgnoreCase("BCC")) {
					emailString = "?bcc=" + emailString + "&" + params;
				} else {
					emailString = emailString + "?" + params;
				}
				logger.info("Generated email string: " + emailString);
				BinaryOpenerFactory.getInstance().mail(emailString);
			} else {
				logger.info("No recipients found that have an email address");
			}

		} catch (Exception e) {
			logger.error("Error: ", e);
		}
	}


}
/*
 * Created on March 21, 2005 (it's springtime!!)
 *
 */
package com.dexels.navajo.tipi.components.swingimpl;

import java.io.*;

import com.dexels.navajo.document.*;
import com.dexels.navajo.tipi.*;
import com.dexels.navajo.tipi.internal.*;


/**
 * @author matthijs (!)
 * @deprecated should be replaced with an appropriate action
 * 
 */

@Deprecated
public class TipiEmailLauncher extends TipiSwingDataComponentImpl {

    private Message recipient = null;       

    protected void performComponentMethod(String name, TipiComponentMethod compMeth, TipiEvent event) {
    	
    	String propertyName = null;
    	String emailSubject = null;
    	String emailBody    = null;
    	
    	if (name.equals("setEmailParameters")) {
    		propertyName = (String) compMeth.getEvaluatedParameter("propertyname", event).value;
    		emailSubject = (String) compMeth.getEvaluatedParameter("subject", event).value;
            emailBody    = (String) compMeth.getEvaluatedParameter("body", event).value;
            
            try {
                recipient = (Message) compMeth.getEvaluatedParameter("messagepath", event).value;
                
                if (recipient == null) {
                    System.err.println("TipiPersonEmail does not recieve a message as input! Aborting...");
                    return;
                }
            } catch (Exception ex) {
                System.err.println("Could not find data in 'messagepath'! \n");
                ex.printStackTrace();
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
                emailString = emailString.substring(0, (emailString.length() - 1));
                emailString = emailString + "?subject=" + subject + "&body=" + body;
                System.err.println("Generated email string: " + emailString);
                String cmd = "rundll32 url.dll,FileProtocolHandler " + emailString;
                try {
                    Runtime.getRuntime().exec(cmd);
                } catch (IOException ex) {
                    System.err.println("Could not launch email (rundll32)");
                }
            } else {
                System.err.println("No recipients found that have an email address");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Object createContainer() {
        return null;
    }

    public static void main(String[] args) {

    }

}
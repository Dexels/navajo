/*
 * Created on March 21, 2005 (it's springtime!!)
 *
 */
package com.dexels.navajo.tipi.components.echoimpl;

import java.io.IOException;

import com.dexels.navajo.document.Message;
import com.dexels.navajo.tipi.TipiComponentMethod;
import com.dexels.navajo.tipi.internal.TipiEvent;


/**
 * @author matthijs (!)
 *  
 */

public class TipiEmailLauncher extends TipiEchoDataComponentImpl {

	private static final long serialVersionUID = -3833165821431412062L;
	private Message recipient = null;
    private String propertyName;
    private String clubName;

    protected void performComponentMethod(String name, TipiComponentMethod compMeth, TipiEvent event) {
        if (name.equals("setEmailParameters")) {
            try {
                recipient = (Message) compMeth.getEvaluatedParameter("messagepath", event).value;
                propertyName = (String) compMeth.getEvaluatedParameter("propertyname", event).value;
                clubName = (String) compMeth.getEvaluatedParameter("clubname", event).value;
                if (recipient == null) {
                    System.err.println("TipiPersonEmail does not recieve a message as input! Aborting...");
                    return;
                }
            } catch (Exception ex) {
                System.err.println("Could not find data in 'messagepath'! \n");
                ex.printStackTrace();
                return;
            }
            createEmail(propertyName, clubName);
        }
    }

    private void createEmail(String propertyName, String clubName) {

        String EmailAddress;
        String Subject = "[" + clubName + "]";
        String EmailString = "mailto:";
        boolean recipientsFound = false;

        try {

            for (int i = 0; i < recipient.getArraySize(); i++) {
                Message current = recipient.getMessage(i);
                EmailAddress = current.getProperty(propertyName).getValue();
                if (EmailAddress != null && EmailAddress.trim() != "") {
                    recipientsFound = true;
                    EmailString = EmailString + EmailAddress + ",";
                }
            }
            // In echo, this is kinda ridiculous, right?
            if (recipientsFound) {
                EmailString = EmailString.substring(0, (EmailString.length() - 1));
                EmailString = EmailString + "?subject=" + Subject;
                System.err.println("Generated email string: " + EmailString);
                String cmd = "rundll32 url.dll,FileProtocolHandler " + EmailString;
                try {
                    Runtime.getRuntime().exec(cmd);
                } catch (IOException ex) {
                    System.err.println("Could not launch rundll32");
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
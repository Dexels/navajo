package com.dexels.navajo.server;

import javax.security.auth.callback.*;
import org.dexels.grus.DbConnectionBroker;

/**
 * Title:        Navajo
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:      Dexels
 * @author Arjen Schoneveld en Martin Bergman
 * @version 1.0
 */

public class NavajoCallBackHandler implements CallbackHandler {

  private String username;
  private String password;
  private String service;

  private DbConnectionBroker broker;

  /**
   *   // Check for certificate.
      String certs = (String) request.getAttribute("javax.servlet.request.X509Certificate");
      Util.debugLog(this, "Certificate: " + certs);
      javax.security.cert.X509Certificate cert = null;
      try {
         cert = javax.security.cert.X509Certificate.getInstance(new StringBufferInputStream(certs));
      } catch (Exception e) {
          Util.debugLog(this, e.getMessage());
      }

      String subjectDN = "";
      String CN = "";
      if (cert != null) {
        Util.debugLog(this, "Got certificate");
        subjectDN = cert.getSubjectDN().getName();
        Util.debugLog(this, "Subject: " + subjectDN);
        CN = getDNAttribute(subjectDN, "CN");
        Util.debugLog(this, "CN: " + CN);
      }
   */

  public NavajoCallBackHandler(String username, String password, String service, DbConnectionBroker broker) {
    this.username = username;
    this.password = password;
    this.broker = broker;
    this.service = service;

  }

  public void handle(Callback[] callbacks) throws java.io.IOException, javax.security.auth.callback.UnsupportedCallbackException {
    for (int i = 0; i < callbacks.length; i++) {

            if (callbacks[i] instanceof NameCallback) {

 		NameCallback nc = (NameCallback)callbacks[i];
 		nc.setName(username);

 	    } else if (callbacks[i] instanceof PasswordCallback) {

 		PasswordCallback pc = (PasswordCallback)callbacks[i];
 		pc.setPassword(password.toCharArray());

            } else if (callbacks[i] instanceof ServiceCallback) {
                ServiceCallback sc = (ServiceCallback)callbacks[i];
                sc.setService(service);
 	    } else {
 		throw new UnsupportedCallbackException
 			(callbacks[i], "Unrecognized Callback");
 	    }
	}
  }

  public DbConnectionBroker getDBBroker() {
    return this.broker;
  }
}
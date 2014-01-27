/****************************************************************
 * Licensed to the Apache Software Foundation (ASF) under one   *
 * or more contributor license agreements.  See the NOTICE file *
 * distributed with this work for additional information        *
 * regarding copyright ownership.  The ASF licenses this file   *
 * to you under the Apache License, Version 2.0 (the            *
 * "License"); you may not use this file except in compliance   *
 * with the License.  You may obtain a copy of the License at   *
 *                                                              *
 *   http://www.apache.org/licenses/LICENSE-2.0                 *
 *                                                              *
 * Unless required by applicable law or agreed to in writing,   *
 * software distributed under the License is distributed on an  *
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY       *
 * KIND, either express or implied.  See the License for the    *
 * specific language governing permissions and limitations      *
 * under the License.                                           *
 ****************************************************************/
package com.dexels.navajo.camel.smtpserver;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import org.apache.camel.util.URISupport;
import org.apache.james.protocols.smtp.SMTPConfiguration;


/**
 * Parse a given uri and set the configuration for the specified parameters in the uri
 *
 */
public class SMTPURIConfiguration implements SMTPConfiguration{

    private String bindIP;
    private int bindPort;
    private boolean enforceHeloEhlo = true;
    private boolean enforceBrackets = true;
    private String greeting = "Camel SMTP 0.1";
    private int resetLength = 0;
    private long maxMessageSize = 0;
    private String helloName = "Camel SMTP";
    private List<String> localDomains;

    /**
     * Parse the given uri and set the configuration for it
     * 
     * @param uri
     * @param parameters
     * @param component
     * @throws Exception
     */
    public void parseURI(URI uri, Map<String, Object> parameters, SMTPComponent component) throws Exception {
        System.out.println(uri);

        bindIP = uri.getHost();
        bindPort = uri.getPort();

     
        Map<String, Object> settings = URISupport.parseParameters(uri);
        if (settings.containsKey("enforceHeloEhlo")) {
            enforceHeloEhlo = Boolean.valueOf((String) settings.get("enforceHeloEhlo"));
        }
        if (settings.containsKey("greeting")) {
            greeting = (String) settings.get("greeting");
        }
        if (settings.containsKey("enforceBrackets")) {
            enforceBrackets = Boolean.valueOf((String) settings.get("enforceBrackets"));
        }
        if (settings.containsKey("maxMessageSize")) {
            maxMessageSize = (Integer.parseInt((String) settings.get("maxMessageSize")));
        }
        if (settings.containsKey("helloName")) {
            helloName = (String) settings.get("helloName");
        }
        if (settings.containsKey("localDomains")) {
            String domainString = (String) settings.get("localDomains");
            StringTokenizer tokenizer = new StringTokenizer(domainString,",");
            localDomains = new ArrayList<String>();
            while (tokenizer.hasMoreTokens()) {
                localDomains.add(tokenizer.nextToken().trim());
            }
        }
    }

    /*
     * (non-Javadoc)
     * @see org.apache.james.protocols.smtp.SMTPConfiguration#getHelloName()
     */
    @Override
	public String getHelloName() {
        return helloName;
    }

    /*
     * (non-Javadoc)
     * @see org.apache.james.protocols.smtp.SMTPConfiguration#getMaxMessageSize()
     */
    @Override
	public long getMaxMessageSize() {
        return maxMessageSize;
    }

    /*
     * (non-Javadoc)
     * @see org.apache.james.protocols.smtp.SMTPConfiguration#getResetLength()
     */
    public int getResetLength() {
        return resetLength;
    }

    /*
     * (non-Javadoc)
     * @see org.apache.james.protocols.smtp.SMTPConfiguration#getSMTPGreeting()
     */
    public String getSMTPGreeting() {
        return greeting;
    }

    /**
     * Auth is not required 
     */
    @Override
	public boolean isAuthRequired(String arg0) {
        return false;
    }

    /**
     * Relaying is allowed
     */
    @Override
	public boolean isRelayingAllowed(String arg0) {
        return true;
    }

    /**
     * No StartTLS is supported
     */
    public boolean isStartTLSSupported() {
        return false;
    }

    /*
     * (non-Javadoc)
     * @see org.apache.james.protocols.smtp.SMTPConfiguration#useAddressBracketsEnforcement()
     */
    @Override
	public boolean useAddressBracketsEnforcement() {
        return enforceBrackets;
    }

    /*
     * (non-Javadoc)
     * @see org.apache.james.protocols.smtp.SMTPConfiguration#useHeloEhloEnforcement()
     */
    @Override
	public boolean useHeloEhloEnforcement() {
        return enforceHeloEhlo;
    }
    
    /**
     * Return the IP address to bind the SMTP server to
     * 
     * @return bindIP
     */
    public String getBindIP() {
        return bindIP;
    }
   
    
    /**
     * Return the port to bind the SMTP server to
     * 
     * @return bindPort
     */
    public int getBindPort() {
        return bindPort;
    }
    
    /**
     * Return the domains for which we want to accept mails
     * 
     * @return domains
     */
    public List<String> getLocalDomains() {
        return localDomains;
    }

	@Override
	public String getGreeting() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getSoftwareName() {
		// TODO Auto-generated method stub
		return null;
	}
}

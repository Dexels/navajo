package com.dexels.navajo.client;

import java.security.KeyStore;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.net.ssl.SSLSocketFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.client.sessiontoken.SessionTokenFactory;
import com.dexels.navajo.client.sessiontoken.SessionTokenProvider;
import com.dexels.navajo.client.systeminfo.SystemInfoFactory;
import com.dexels.navajo.client.systeminfo.SystemInfoProvider;
import com.dexels.navajo.document.Guid;
import com.dexels.navajo.document.Header;
import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoException;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.document.Property;

public abstract class NavajoClient implements ClientInterface{
    private final static Logger logger = LoggerFactory.getLogger(NavajoClient.class);

    protected String username = null;
    protected String password = null;
    protected String[] serverUrls;
    protected boolean useHttps = false;
    protected int currentServerIndex;

    // Warning: Not thread safe!
    protected final Set<Map<String, String>> piggyBackData = new HashSet<Map<String, String>>();
    protected Map<String, String> httpHeaders;
    protected Map<String, String> navajoHeaders;

    protected boolean allowCompression = true;
    protected boolean forceGzip = true;
    protected boolean useBasicAuth;
    protected String bearerToken;
    protected boolean generateConditionErrors = true;
    
    protected SystemInfoProvider systemInfoProvider;
    protected SessionTokenProvider sessionTokenProvider;
    protected SSLSocketFactory socketFactory;
    protected KeyStore keyStore;


    
    @Override
    public final void setUsername(String s) {
        username = s;
    }
    
    /**
     * Set the server URL
     * 
     * @param url String USE SET SERVERURLS
     */
    @Override
    public final void setServerUrl(String url) {
        serverUrls = new String[] { url };
        setServerUrls(serverUrls);

    }

    @Override
    public final void setPassword(String pw) {
        password = pw;
    }

    

    @Override
	public void setBearerToken(String token) {
		this.bearerToken = token;
		
	}

	@Override
	public void useBasicAuthentication(boolean enableBasicAuth) {
		this.useBasicAuth = enableBasicAuth;
		
	}

	@Override
    public final Navajo doSimpleSend(Navajo out, String method) throws ClientException {
    	if (out == null) {
    		out = NavajoFactory.getInstance().createNavajo();
    	}
        return doSimpleSend(out, method, 0);
    }
    

    @Override
    public final Navajo doSimpleSend(Navajo out, String method, Integer retries) throws ClientException {
        if (bearerToken == null){
        	if (username == null) {
            throw new ClientException(1, 1, "No username set!");
	        }
	        if (password == null) {
	            throw new ClientException(1, 1, "No password set!");
	        }
        }
        if (getCurrentHost() == null) {
            throw new ClientException(1, 1, "No host set!");
        }
        return doSimpleSend(out, method, retries);

    }
    


   
    protected final Navajo doSimpleSend(Navajo out, String method, int retries) throws ClientException {
        // NOTE: prefix persistence key with method, because same Navajo object
        // could be used as a request
        // for multiple methods!

        // ============ compared services ===================

        /**
         * Make sure that same Navajo is not used simultaneously.
         */
        synchronized (out) {
            // ====================================================

            Header header = out.getHeader();
            String callingService = null;
            if (header == null) {
                header = NavajoFactory.getInstance().createHeader(out, method, username, password, -1);
                out.addHeader(header);
            } else {
                callingService = header.getRPCName();
                header.setRPCName(method);
                header.setRPCUser(username);
                header.setRPCPassword(password);
            }
            // ALWAY SET REQUEST ID AT THIS POINT.
            if (header.getRequestId() != null && header.getRequestId().equals("42")) {
                System.err.println("ENCOUNTERED TEST!!!");
            } else {
                header.setRequestId(Guid.create());
            }
            String sessionToken = getSessionTokenProvider().getSessionToken();
            header.setHeaderAttribute("clientToken", sessionToken);
            header.setHeaderAttribute("clientInfo", getSystemInfoProvider().toString());
            
            for (String key : navajoHeaders.keySet()) {
            	header.setHeaderAttribute(key, navajoHeaders.get(key));
            }
            // ========= Adding globalMessages

            long clientTime = 0;
            try {
                if (out.getHeader() != null) {
                    processPiggybackData(out.getHeader());
                }

                Navajo n = null;

                long timeStamp = System.currentTimeMillis();

                n = doTransaction(out, allowCompression, retries, 0);
                
                if (n.getHeader() != null) {
                    n.getHeader().setHeaderAttribute("sourceScript", callingService);
                    clientTime = (System.currentTimeMillis() - timeStamp);
                    n.getHeader().setHeaderAttribute("clientTime", "" + clientTime);
                    String tot = n.getHeader().getHeaderAttribute("serverTime");                        
                    long totalTime = -1;
                    if (tot != null && !"".equals(tot)) {
                        totalTime = Long.parseLong(tot);
                        n.getHeader().setHeaderAttribute("transferTime", "" + (clientTime - totalTime));
                    }
                    Map<String, String> headerAttributes = n.getHeader().getHeaderAttributes();
                    Map<String, String> pbd = new HashMap<String, String>(headerAttributes);
                    pbd.put("type", "performanceStats");
                    pbd.put("service", method);
                    synchronized (piggyBackData) {
                        piggyBackData.add(pbd);
                    }
                } else {
                    logger.info("Null header in input message?");
                }
                
                return n;
            } catch (ClientException e) {
                throw e;
            } catch (Exception e) {
                logger.error("Error: ", e);
                throw new ClientException(-1, -1, e.getMessage(), e);
            }
        }
    }
    
    protected Navajo doTransaction(Navajo d, boolean useCompression, int retries, int exceptions) throws ClientException {
        throw new UnsupportedOperationException();
    }

    protected SessionTokenProvider getSessionTokenProvider() {
        if (sessionTokenProvider == null) {
            return SessionTokenFactory.getSessionTokenProvider();
        }
        return this.sessionTokenProvider;
    }

 
    protected void generateConnectionError(Navajo n, int id, String description) {
        try {
            Message conditionError = NavajoFactory.getInstance().createMessage(n, "ConditionErrors", Message.MSG_TYPE_ARRAY);
            n.addMessage(conditionError);
            Message conditionErrorElt = NavajoFactory.getInstance().createMessage(n, "ConditionErrors");
            conditionError.addMessage(conditionErrorElt);
            Property p1 = NavajoFactory.getInstance().createProperty(n, "Id", Property.INTEGER_PROPERTY, id + "", 10, "Id", Property.DIR_OUT);
            Property p2 = NavajoFactory.getInstance().createProperty(n, "Description", Property.INTEGER_PROPERTY, description, 10, "Omschrijving",
                    Property.DIR_OUT);
            Property p3 = NavajoFactory.getInstance().createProperty(n, "FailedExpression", Property.INTEGER_PROPERTY, "", 10, "FailedExpression",
                    Property.DIR_OUT);
            Property p4 = NavajoFactory.getInstance().createProperty(n, "EvaluatedExpression", Property.INTEGER_PROPERTY, "", 10, "EvaluatedExpression",
                    Property.DIR_OUT);
            conditionErrorElt.addProperty(p1);
            conditionErrorElt.addProperty(p2);
            conditionErrorElt.addProperty(p3);
            conditionErrorElt.addProperty(p4);
        } catch (NavajoException ex) {
            logger.error("Error: ", ex);
        }
    }
  
    

    /**
     * Add piggyback data to header.
     * 
     * @param header
     */
    protected final void processPiggybackData(Header header) {

        synchronized (piggyBackData) {
            // Clear previous piggyback data.
            header.clearPiggybackData();
            for (Iterator<Map<String, String>> iter = piggyBackData.iterator(); iter.hasNext();) {
                Map<String, String> element = iter.next();
                header.addPiggyBackData(element);
            }
            // remove piggyback data.
            piggyBackData.clear();
        }

    }
   

    @Override
    public void setServerUrls(String[] servers) {
        serverUrls = servers;
        if (servers.length > 0) {
            currentServerIndex = 0;
        }
    }

    protected String getCurrentHost() {
        if (serverUrls != null && serverUrls.length > 0) {
            String currentServer = serverUrls[currentServerIndex];
            
            if (!currentServer.startsWith("http") && currentServer.length() > 0) {
                if (useHttps) {
                    return "https://" + currentServer;
                } else {
                    return "http://" + currentServer;
                }
            }
            return currentServer;
        }
        return null;
    }

    @Override
    /**
     * I think only used in testing
     */
    public void setCurrentHost(String host) {
        for (int i = 0; i < serverUrls.length; i++) {
            if (serverUrls[i].equals(host)) {
                currentServerIndex = i;
                logger.info("SET CURRENT SERVER TO: " + host + "(" + currentServerIndex + ")");
                break;
            }
        }
    }

    @Override
    public void setAllowCompression(boolean allowCompression) {
        this.allowCompression = allowCompression;
    }

    @Override
    public void setForceGzip(boolean forceGzip) {
        this.forceGzip = forceGzip;
    }

    private SystemInfoProvider getSystemInfoProvider() {
        if (this.systemInfoProvider == null) {
            return SystemInfoFactory.getSystemInfo();
        }
        return systemInfoProvider;
    }


	@Override
	public void setHeader(String key, Object value) {
		if (httpHeaders == null) {
			httpHeaders = new HashMap<>();
		
		}
		httpHeaders.put(key, value.toString());
		
	}
	

	@Override
	public void setNavajoHeader(String key, Object value) {
		if (navajoHeaders == null) {
			httpHeaders = new HashMap<>();
		
		}
		httpHeaders.put(key, value.toString());
	}

	@Override
	public void setGenerateConditionErrors(boolean set) {
		this.generateConditionErrors  = set;
		
	}
	
	

}

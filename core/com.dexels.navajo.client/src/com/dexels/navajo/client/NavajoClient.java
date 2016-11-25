package com.dexels.navajo.client;

import java.security.KeyStore;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import javax.net.ssl.SSLSocketFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.client.sessiontoken.SessionTokenProvider;
import com.dexels.navajo.client.systeminfo.SystemInfoFactory;
import com.dexels.navajo.client.systeminfo.SystemInfoProvider;
import com.dexels.navajo.document.Header;
import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoException;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.document.Property;
import com.dexels.navajo.document.types.Binary;

public abstract class NavajoClient implements ClientInterface{
    private final static Logger logger = LoggerFactory.getLogger(NavajoClient.class);

    protected String username = null;
    protected String password = null;
    protected String[] serverUrls;
    protected boolean useHttps = false;
    protected int currentServerIndex;

    protected final Random randomize = new Random(System.currentTimeMillis());
    // Warning: Not thread safe!
    protected final Set<Map<String, String>> piggyBackData = new HashSet<Map<String, String>>();

    protected String localeCode = null;
    protected String subLocale;
    protected String application;
    protected boolean allowCompression = true;
    protected boolean forceGzip = true;
    protected SystemInfoProvider systemInfoProvider;
    protected SessionTokenProvider sessionTokenProvider;
    protected SSLSocketFactory socketFactory;
    protected KeyStore keyStore;
    protected String organization;
    
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
        setServers(serverUrls);

    }

    @Override
    public final void setPassword(String pw) {
        password = pw;
    }

    @Override
    public final void setRetryAttempts(int attempts) {
        // unsupported
    }

    @Override
    public final Navajo doSimpleSend(String method) throws ClientException {
        return doSimpleSend(NavajoFactory.getInstance().createNavajo(), method, 0);
    }

    @Override
    public final Navajo doSimpleSend(Navajo out, String method) throws ClientException {
        return doSimpleSend(out, method, 0);
    }
    

    @Override
    public final Navajo doSimpleSend(Navajo out, String method, Integer retries) throws ClientException {
        if (username == null) {
            throw new ClientException(1, 1, "No username set!");
        }
        if (password == null) {
            throw new ClientException(1, 1, "No password set!");
        }
        if (getCurrentHost() == null) {
            throw new ClientException(1, 1, "No host set!");
        }
        return doSimpleSend(out, method, -1, retries);

    }
    
    @Override
    public final Navajo doSimpleSend(Navajo n, String method, ConditionErrorHandler v, long expirationInterval) throws ClientException {
        if (v != null) {
            v.clearConditionErrors();
        }

        if (username == null) {
            throw new ClientException(1, 1, "No username set!");
        }
        if (password == null) {
            throw new ClientException(1, 1, "No password set!");
        }
        if (getCurrentHost() == null) {
            throw new ClientException(1, 1, "No host set!");
        }
        Navajo result = doSimpleSend(n, method, expirationInterval, 0);

        if (v != null) {
            checkValidation(result, v);
        }
        return result;
    }
    


    protected Navajo doTransaction(Navajo d, boolean useCompression, int retries, int exceptions) throws Throwable {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean useHttps() {
        return useHttps;
    }

    @Override
    public void setHttps(boolean useHttps) {
        this.useHttps = useHttps;
    }
    


    
    

    protected Navajo doSimpleSend(Navajo out, String method, int i, Integer retries) {
        throw new UnsupportedOperationException();
    }

    
 
    

    
    
    protected Navajo doSimpleSend(Navajo n, String method, long expirationInterval, int retries) throws ClientException {
        throw new UnsupportedOperationException();
    }

    /**
     * Schedule a webservice @ a certain time. Note that this method does NOT return the response of the scheduled webservice. It contains a Navajo with the
     * status of the scheduling.
     * 
     * @out contains the request Navajo
     * @method defines the webservice
     * @schedule defines a timestamp of the format: HH:mm:ss dd-MM-yyyy. If null assume immediate execution.
     * 
     */
    @Override
    public Navajo doScheduledSend(Navajo out, String method, String schedule, String description, String clientId) throws ClientException {

        String triggerURL = null;

        if (schedule == null) {
            schedule = "now";
        }

        Header h = out.getHeader();
        if (h == null) {
            h = NavajoFactory.getInstance().createHeader(out, method, username, password, -1);
            out.addHeader(h);
        }
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm dd-MM-yyyy");
        Calendar c = Calendar.getInstance();
        if (!schedule.equals("now")) {
            try {
                c.setTime(sdf.parse(schedule));
                triggerURL = "time:" + (c.get(Calendar.MONTH) + 1) + "|" + c.get(Calendar.DAY_OF_MONTH) + "|" + c.get(Calendar.HOUR_OF_DAY) + "|"
                        + c.get(Calendar.MINUTE) + "|*|" + c.get(Calendar.YEAR);
            } catch (ParseException e) {
                throw new ClientException(-1, -1, "Unknown schedule timestamp format: " + schedule);
            }
        } else {
            triggerURL = "time:" + schedule;
        }
        if (description != null) {
            h.setHeaderAttribute("description", description);
        }
        if (clientId != null) {
            h.setHeaderAttribute("clientid", clientId);
        }
        h.setHeaderAttribute("keeprequestresponse", "true");
        h.setSchedule(triggerURL);

        return doSimpleSend(out, method);
    }
    
    
    @Override
    public Binary getArrayMessageReport(Message m, String[] propertyNames, String[] propertyTitles, int[] columnWidths, String format, String orientation,
            int[] margins) throws NavajoException {
        // Message m = in.getMessage(messagePath);
        if (m == null) {
            throw NavajoFactory.getInstance().createNavajoException("Message not found. Can not run report.");
        }
        Navajo n = NavajoFactory.getInstance().createNavajo();
        Message cp = m.copy(n);
        Header h = NavajoFactory.getInstance().createHeader(n, "Irrelevant", "Irrelevant", "Irrelevant", -1);
        n.addHeader(h);
        h.setHeaderAttribute("sourceScript", "Irrelevant");
        n.addMessage(cp);
        Message repDef = NavajoFactory.getInstance().createMessage(n, "__ReportDefinition");
        n.addMessage(repDef);
        StringBuffer sz = new StringBuffer();
        for (int i = 0; i < columnWidths.length; i++) {
            if (i != 0) {
                sz.append(",");
            }
            sz.append(columnWidths[i]);
        }
        Property sizeProp = NavajoFactory.getInstance().createProperty(n, "PropertySizes", Property.STRING_PROPERTY, sz.toString(), 0, "", Property.DIR_IN);
        repDef.addProperty(sizeProp);

        sz = new StringBuffer();
        for (int i = 0; i < propertyNames.length; i++) {
            if (i != 0) {
                sz.append(",");
            }
            sz.append(propertyNames[i]);
        }
        String propertyNamesString = sz.toString();
        Property namesProp = NavajoFactory.getInstance().createProperty(n, "PropertyNames", Property.STRING_PROPERTY, propertyNamesString, 0, "",
                Property.DIR_IN);
        repDef.addProperty(namesProp);

        sz = new StringBuffer();
        if (propertyTitles != null) {
            for (int i = 0; i < propertyTitles.length; i++) {
                if (i != 0) {
                    sz.append(",");
                }
                sz.append(propertyTitles[i]);
            }
        } else {
            // If no titles supplied, use property names
            sz.append(propertyNamesString);
        }
        Property titlesProp = NavajoFactory.getInstance().createProperty(n, "PropertyTitles", Property.STRING_PROPERTY, sz.toString(), 0, "", Property.DIR_IN);
        repDef.addProperty(titlesProp);

        Property messagePathProp = NavajoFactory.getInstance().createProperty(n, "MessagePath", Property.STRING_PROPERTY, cp.getName(), 0, "", Property.DIR_IN);
        repDef.addProperty(messagePathProp);

        Property reportFormatProp = NavajoFactory.getInstance().createProperty(n, "OutputFormat", Property.STRING_PROPERTY, format, 0, "", Property.DIR_IN);
        repDef.addProperty(reportFormatProp);

        if (margins != null) {
            Property marginProperty = NavajoFactory.getInstance().createProperty(n, "Margin", Property.STRING_PROPERTY,
                    margins[0] + "," + margins[1] + "," + margins[2] + "," + margins[3], 0, "", Property.DIR_IN);
            repDef.addProperty(marginProperty);
        }
        if (orientation != null) {
            Property orientationProperty = NavajoFactory.getInstance().createProperty(n, "Orientation", Property.STRING_PROPERTY, orientation, 0, "",
                    Property.DIR_IN);
            repDef.addProperty(orientationProperty);
        }

        try {
            Navajo result = NavajoClientFactory.getClient().doSimpleSend(n, "ProcessPrintTableBirt");
            Property data = result.getProperty("/Result/Data");
            if (data == null) {
                result.write(System.err);
                throw NavajoFactory.getInstance().createNavajoException("No report property found.");
            }
            Binary b = (Binary) data.getTypedValue();
            return b;
        } catch (ClientException e) {
            throw NavajoFactory.getInstance().createNavajoException(e);
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
    
    private final void checkValidation(Navajo result, ConditionErrorHandler v) {
        Message conditionErrors = result.getMessage("ConditionErrors");
        if (conditionErrors != null && v != null) {
            v.checkValidation(conditionErrors);
        }
    }

    @Override
    public void setServers(String[] servers) {
        serverUrls = servers;
        if (servers.length > 0) {
            currentServerIndex = randomize.nextInt(servers.length);
        }
    }

    @Override
    public String getCurrentHost() {
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
    public void setLocaleCode(String locale) {
        this.localeCode = locale;
    }

    @Override
    public String getLocaleCode() {
        return this.localeCode;
    }

    @Override
    public void setSubLocaleCode(String locale) {
        this.subLocale = locale;
    }

    @Override
    public String getSubLocaleCode() {
        return this.subLocale;
    }

    public String getApplication() {
        return application;
    }

    @Override
    public void setApplication(String application) {
        this.application = application;
    }

    @Override
    public void setOrganization(String organization) {
        this.organization = organization;

    }

    public String getOrganization() {
        return organization;
    }
    

    @Override
    public void setAllowCompression(boolean allowCompression) {
        this.allowCompression = allowCompression;
    }

    @Override
    public void setForceGzip(boolean forceGzip) {
        this.forceGzip = forceGzip;
    }

    @Override
    public SystemInfoProvider getSystemInfoProvider() {
        if (this.systemInfoProvider == null) {
            return SystemInfoFactory.getSystemInfo();
        }
        return systemInfoProvider;
    }

    @Override
    public void setSystemInfoProvider(SystemInfoProvider sip) {
        this.systemInfoProvider = sip;
    }

  

    @Override
    public void setSessionTokenProvider(SessionTokenProvider stp) {
        this.sessionTokenProvider = stp;
    }

    
}

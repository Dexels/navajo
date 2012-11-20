/*
 * Created on Jul 29, 2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.dexels.navajo.studio.eclipse;

import java.util.StringTokenizer;

import com.dexels.navajo.client.ClientException;
import com.dexels.navajo.client.ClientInterface;
import com.dexels.navajo.client.NavajoClientFactory;
import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoException;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.document.types.Binary;
import com.dexels.navajo.studio.script.plugin.NavajoScriptPluginPlugin;

public class ServerEntry {
    private final String protocol;
    private final String server;
    private final String username;
    private final String password;
    private final String name;

    public static final char SEP_CHAR = '|';

    public ServerEntry(String name, String protocol, String server, String username, String password) {
        this.name = name;
        this.protocol = protocol;
        this.server = server;
        this.username = username;
        this.password = password;
    }

    public ServerEntry(String data) {
        StringTokenizer st = new StringTokenizer(data,"|");
        name = st.nextToken();
        protocol = st.nextToken();
        if (st.hasMoreTokens()) {
            server = st.nextToken();
        } else {
            server = "";
        }
        if (st.hasMoreTokens()) {
            username = st.nextToken();
        } else {
            username = "";
        }
        if (st.hasMoreTokens()) {
            password = st.nextToken();
        } else {
            password = "";
        }
    }
    
    public String toDataString() {
        StringBuffer sb = new StringBuffer();
        sb.append(name);
        sb.append(SEP_CHAR);
        sb.append(protocol);
        sb.append(SEP_CHAR);
        sb.append(server);
        sb.append(SEP_CHAR);
        sb.append(username);
        sb.append(SEP_CHAR);
        sb.append(password);
        return sb.toString();
    }

    public Navajo runInit(String scriptName) throws ClientException {
        NavajoClientFactory.resetClient();
        System.setProperty(NavajoScriptPluginPlugin.DOC_IMPL,NavajoScriptPluginPlugin.QDSAX);
        NavajoFactory.resetImplementation();
        Navajo input = NavajoFactory.getInstance().createNavajo();
        return runProcess(scriptName, input);
    }
    
    public String toDebug() {
        return "Protocol: "+protocol+" server: "+server+" username: "+username+" password: "+password+" name: "+name;
    }
    public Navajo runProcess(String scriptName, Navajo input) throws ClientException {
        ClientInterface nc = null; 
        System.setProperty(NavajoScriptPluginPlugin.DOC_IMPL,NavajoScriptPluginPlugin.QDSAX);
        NavajoFactory.resetImplementation();
       System.err.println("Running process: "+scriptName+" >>> "+toDebug());
        System.err.println("username: "+username+" pass: "+password);
        NavajoClientFactory.resetClient();
        if ("http".equals(protocol)) {
            nc = NavajoClientFactory.createClient("com.dexels.navajo.client.NavajoClient", null,null);
        }
        if ("socket".equals(protocol)) {
            nc = NavajoClientFactory.createClient("com.dexels.navajo.client.NavajoSocketClient", null,null);
        }
        if ("local".equals(protocol)) {
            nc = NavajoClientFactory.createClient("com.dexels.navajo.client.NavajoSocketClient", null,null);
        }
        if(nc==null) {
        	throw new ClientException(-1, -1, "Unknown protocol: "+protocol);
        }
        nc.setLocaleCode(NavajoScriptPluginPlugin.getDefault().getSelectedLocale());
        nc.setServerUrl(server);
        nc.setUsername(username);
        nc.setPassword(password);
        Navajo n = nc.doSimpleSend(input.copy(), scriptName);
        if (n.getMessage("error")==null && n.getMessage("ConditionErrors")==null) {
            System.err.println("Parsed correctly. Adding script to CA");
        }
        return n;
    }
    
	public Binary getArrayMessageReport(Message m, String[] propertyNames, String[] propertyTitles, int[] columnWidths, String format, String orientation, int[] margins) throws NavajoException {
	       ClientInterface nc = null;
	        System.setProperty(NavajoScriptPluginPlugin.DOC_IMPL,NavajoScriptPluginPlugin.QDSAX);
	        NavajoFactory.resetImplementation();
	     NavajoClientFactory.resetClient();
	        if ("http".equals(protocol)) {
	            nc = NavajoClientFactory.createClient("com.dexels.navajo.client.NavajoClient", null,null);
	        }
	        if ("socket".equals(protocol)) {
	            nc = NavajoClientFactory.createClient("com.dexels.navajo.client.NavajoSocketClient", null,null);
	        }
	        if ("local".equals(protocol)) {
	            nc = NavajoClientFactory.createClient("com.dexels.navajo.client.NavajoSocketClient", null,null);
	        }
	        if(nc==null) {
	        	throw NavajoFactory.getInstance().createNavajoException("Unknown protocol: "+protocol);
	        }
	        nc.setLocaleCode(NavajoScriptPluginPlugin.getDefault().getSelectedLocale());
	        nc.setServerUrl(server);
	        nc.setUsername(username);
	        nc.setPassword(password);
	        Binary b = nc.getArrayMessageReport(m,propertyNames,propertyTitles,columnWidths,format,orientation,margins);
	        return b;
	}
    
    @Override
	public String toString() {
        return name;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public String getProtocol() {
        return protocol;
    }

    public String getServer() {
        return server;
    }

    public String getUsername() {
        return username;
    }
}


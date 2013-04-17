package com.dexels.navajo.tunnel.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.server.NavajoIOConfig;
import com.dexels.navajo.tunnel.Tunnel;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

public class NavajoTunnelComponentImpl implements Tunnel {

	
	private final static Logger logger = LoggerFactory
			.getLogger(NavajoTunnelComponentImpl.class);
	private JSch jsch;
	private Session session;
	private NavajoIOConfig navajoConfig;
	private String localhost;
	private String name;
	private int localport;
	private String type;
	
	private final Map<String,Object> parameters = new HashMap<String, Object>();
	
	public void activate(Map<String,Object> settings) throws Exception{
		System.err.println("Activate tunnel");
		logger.info("Setting up tunnel with settings: {}",settings);
		String username = (String) settings.get("username");
		String host = (String) settings.get("host");
		String keyfile = (String) settings.get("keyfile");

		this.type = (String) settings.get("type");
		this.name = (String) settings.get("name");
		this.localhost = (String) settings.get("localhost");
		final Object localPortString = settings.get("localport");
		Integer localPort = null;
		if (localPortString instanceof Integer) {
			localPort = (Integer) localPortString;
		} else {
			localPort = Integer.parseInt(""+localPortString);
		}
		this.localport = localPort.intValue();
		
		final Object remotePortString = settings.get("remoteport");
		Integer remotePort = null;
		if (remotePortString instanceof Integer) {
			remotePort = (Integer) remotePortString;
		} else {
			remotePort = Integer.parseInt(""+remotePortString);
		}
		final Object sshPortString = settings.get("sshport");
		Integer sshPort = null;
		if (sshPortString instanceof Integer) {
			sshPort = (Integer) sshPortString;
		} else {
			sshPort = Integer.parseInt(""+sshPortString);
		}
		parameters.clear();
		parameters.putAll(settings);
		
		connect(username, host, remotePort,localhost, localPort, sshPort, keyfile);
	}

	public void deactivate() {
		if(session!=null) {
			session.disconnect();
		}
		localhost = null;
		name = null;
		type = null;
		localport = -1;
	}
	

	public void setNavajoIOConfig(NavajoIOConfig navajoConfig) {
		this.navajoConfig = navajoConfig;
	}
	
	public void clearNavajoIOConfig(NavajoIOConfig navajoConfig) {
		this.navajoConfig = null;
	}

	public NavajoIOConfig getNavajoConfig() {
		return this.navajoConfig;
	}
	
	private void connect(String username, String host, int remotePort,String localhost, int localPort,int sshPort, String privateKey) throws JSchException {
		int assigned = 0;
			JSch.setLogger(new JschLoggerBridge(logger));

			
			jsch = new JSch(); 
			jsch.addIdentity(privateKey);
            session = jsch.getSession(username, host, sshPort);

			java.util.Properties config = new java.util.Properties();
            config.put("StrictHostKeyChecking", "no");
            config.put("Compression", "yes");
            config.put("ConnectionAttempts","2");
             
            session.setConfig(config);
            session.setTimeout(10000);
            session.connect();            
             
            assigned = session.setPortForwardingL(localhost, localPort, 
                    host, remotePort);
            session.setDaemonThread(true);
             System.err.println("assigned: "+assigned);
         
        if (assigned == 0) {
            logger.warn("SSH tunnel failed: ", host);
            return;
        }
	}

	
	public static void main(String[] args) throws InterruptedException, JSchException {
		NavajoTunnelComponentImpl ntci = new NavajoTunnelComponentImpl();
		ntci.connect("flyaruu", "10.0.0.1", 1521, "localhost",21521, 22,"/Users/frank/.ssh/id_rsa");
		Thread.sleep(10000);
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getType() {
		return type;
	}

	@Override
	public String getLocalHost() {
		return localhost;
	}

	@Override
	public int getLocalPort() {
		return localport;
	}

	@Override
	public Object getParameter(String key) {
		return parameters.get(key);
	}
}

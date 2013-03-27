package com.dexels.navajo.tunnel.impl;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.server.NavajoIOConfig;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

public class NavajoTunnelComponentImpl {

	
	private final static Logger logger = LoggerFactory
			.getLogger(NavajoTunnelComponentImpl.class);
	private JSch jsch;
	private Session session;
	private NavajoIOConfig navajoConfig;
	
	public void activate(Map<String,Object> settings) throws Exception{
		logger.info("Setting up tunnel with settings: {}",settings);
		String username = (String) settings.get("username");
		String host = (String) settings.get("host");
		String keyfile = (String) settings.get("keyfile");
		final Object localPortString = settings.get("localport");
		Integer localPort = null;
		if (localPortString instanceof Integer) {
			localPort = (Integer) localPortString;
		} else {
			localPort = Integer.parseInt(""+localPortString);
		}
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
		connect(username, host, remotePort, localPort, sshPort, keyfile);
	}

	public void deactivate() {
		if(session!=null) {
			session.disconnect();
		}
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
	
	public void connect(String username, String host, int remotePort,int localPort,int sshPort, String privateKey) throws JSchException {
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
             
            assigned = session.setPortForwardingL(localPort, 
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
		ntci.connect("flyaruu", "10.0.0.1", 1521, 21521, 22,"/Users/frank/.ssh/id_rsa");
		Thread.sleep(10000);
	}
}

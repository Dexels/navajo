package com.dexels.navajo.tunnel.impl;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;
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
	private final JSch jsch = new JSch();
	private Session session;
	private NavajoIOConfig navajoConfig;

	private final Map<String, Object> parameters = new HashMap<String, Object>();

	static {
		JSch.setLogger(new JschLoggerBridge(logger));
	}

	public NavajoTunnelComponentImpl() {

	}

	public void activate(Map<String, Object> settings) throws Exception {
		System.err.println("Activate tunnel");
		logger.info("Setting up tunnel with settings: {}", settings);
		String ssh = (String) settings.get("ssh");
		String forwards = (String) settings.get("forwards");
		final String fileInstallPath = (String) settings
				.get("felix.fileinstall.filename");

		File storeFolder = findConfiguration(null, fileInstallPath);

		String keyfile = (String) settings.get("keyfile");

		parameters.clear();
		parameters.putAll(settings);
		addIdentity(keyfile, storeFolder);
		Session session = createSession(ssh);
		setupForwards(session, forwards);
		// connect(username, sshHost, host, remotePort,localhost, localPort,
		// sshPort, keyfile,storeFolder);
	}

	private void addIdentity(String privateKey, File storeFolder)
			throws JSchException {
		if (privateKey != null) {
			File privateKeyFile = new File(privateKey);
			if (privateKeyFile.exists()) {
				jsch.addIdentity(privateKey);
				return;
			}
			if (!privateKeyFile.isAbsolute() && storeFolder != null && storeFolder.exists()) {
				File ps = new File(storeFolder, privateKey);
				File pub = new File(storeFolder,privateKey+".pub");
				if (ps.exists()) {
					jsch.addIdentity(ps.getAbsolutePath(),pub.getAbsolutePath());
					return;
				} else {
					logger.warn("Private key mentioned in configuration not found: "
							+ privateKey
							+ " not adding this key, but I'll still try to make a tunnel.");
				}
			}
		}
	}

	private File findConfiguration(String path, String fileInstallPath)
			throws IOException {

		if (path == null || "".equals(path)) {
			path = System.getProperty("storage.path");
		}
		File storeFolder = null;
		if (path == null) {
			logger.info("No storage.path found, now trying to retrieve from felix.fileinstall.filename");
			storeFolder = findByFileInstaller(fileInstallPath);
		} else {
			storeFolder = new File(path);
		}
		if (storeFolder == null || !storeFolder.exists()) {
			storeFolder = findByFileInstaller(fileInstallPath);
		}
		if (storeFolder == null || !storeFolder.exists()) {
			throw new IOException("No storage.path set in configuration!");
		}
		return storeFolder;
	}

	private File findByFileInstaller(final String fileNamePath) {
		try {
			URL url = new URL(fileNamePath);
			File f;
			try {
				f = new File(url.toURI());
			} catch (URISyntaxException e) {
				f = new File(url.getPath());
			}
			if (f != null) {
				File etc = f.getParentFile();
				return etc;
			}
		} catch (MalformedURLException e) {
			logger.warn("Fileinstall.filename based resolution also failed.", e);
		}
		return null;
	}

	public void deactivate() {
		if (session != null) {
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

//	private void connect(String username, String sshHost, String host,
//			int remotePort, String localhost, int localPort, int sshPort,
//			String privateKey, File storeFolder) throws JSchException {
//		int assigned = 0;
//		if (privateKey != null) {
//			File privateKeyFile = new File(privateKey);
//			if (privateKeyFile.exists()) {
//				jsch.addIdentity(privateKey);
//			} else {
//				logger.warn("Private key mentioned in configuration not found: "
//						+ privateKey
//						+ " not adding this key, but I'll still try to make a tunnel.");
//			}
//			if (!privateKeyFile.isAbsolute() && storeFolder != null
//					&& storeFolder.exists()) {
//				File bs = new File(storeFolder, privateKey);
//				if (bs.exists()) {
//					jsch.addIdentity(bs.getAbsolutePath());
//				} else {
//					logger.warn("Private key mentioned in configuration not found: "
//							+ privateKey
//							+ " not adding this key, but I'll still try to make a tunnel.");
//				}
//			}
//		}
//		session = jsch.getSession(username, sshHost, sshPort);
//
//		java.util.Properties config = new java.util.Properties();
//		config.put("StrictHostKeyChecking", "no");
//		config.put("Compression", "yes");
//		config.put("ConnectionAttempts", "2");
//
//		session.setConfig(config);
//		session.setTimeout(10000);
//		session.connect();
//
//		assigned = session.setPortForwardingL(localhost, localPort, host,
//				remotePort);
//		session.setDaemonThread(true);
//		System.err.println("assigned: " + assigned);
//
//		if (assigned == 0) {
//			logger.warn("SSH tunnel failed: ", host);
//			return;
//		}
//	}

	public static void main(String[] args) throws InterruptedException,
			JSchException {
		NavajoTunnelComponentImpl ntci = new NavajoTunnelComponentImpl();
		ntci.addIdentity("openshift_rsa", new File("/Users/frank/git/sportlink.restructure/config"));
		Session session = ntci.createSession("openshift@source.dexels.com:22");
		// ,0.0.0.0:27017>source.dexels.com:27017
		ntci.setupForwards(session,"127.0.0.1:21521>source.dexels.com:1521");
//		int result = session.setPortForwardingL("127.0.0.1", 21521, "source.dexels.com", 1521);
//		session.setDaemonThread(true);
		Thread.sleep(1000000);
	}

	private Session createSession(String sshString) throws JSchException {
		String[] parts = sshString.split("@");
		String username = parts[0];
		String host = parts[1];
		String sshPortString = null;
		if (host.indexOf(':') != -1) {
			String[] p = host.split(":");
			host = p[0];
			sshPortString = p[1];
		}
		int sshPort = sshPortString == null ? 22 : Integer
				.parseInt(sshPortString);
		session = jsch.getSession(username, host, sshPort);
		session = jsch.getSession(username, host, sshPort);

		java.util.Properties config = new java.util.Properties();
		config.put("StrictHostKeyChecking", "no");
		config.put("Compression", "yes");
		config.put("ConnectionAttempts", "2");

		session.setConfig(config);
		session.setTimeout(10000);
		session.connect();
		return session;
	}

	private void setupForwards(Session session, String forwardsString)
			throws JSchException {
		String[] forwards = forwardsString.split(",");
		for (String forward : forwards) {
			System.err.println("forward: " + forward);
			boolean remote = forward.indexOf('<') != -1;
			System.err.println("remote: " + remote);
			String[] parts = forward.split(remote ? "<" : ">");
			String from = parts[0];
			String to = parts[1];
			String[] fromParts = from.split(":");
			String fromLocalHost = fromParts[0];
			int fromP = Integer.parseInt(fromParts[1]);
			String[] toParts = to.split(":");
			String toRemoteHost = toParts[0];
			int toP = Integer.parseInt(toParts[1]);

			System.err.println("Adding " + (remote ? "remote" : "local")
					+ " forward from host: " + fromLocalHost + " port: "
					+ fromP + " to host: " + toRemoteHost + " port: " + toP);
			if (remote) {
				session.setPortForwardingR(toP, toRemoteHost, fromP);
			} else {
				int result = session.setPortForwardingL(fromLocalHost, fromP, toRemoteHost, toP);
				System.err.println("setPortForwardingL("+fromLocalHost+","+fromP+", "+toRemoteHost+", "+toP+") = "+result);
			}
		}
		session.setDaemonThread(true);
		try {
			session.sendKeepAliveMsg();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		System.err.println("assigned: " + assigned);
//
//		if (assigned == 0) {
//			logger.warn("SSH tunnel failed: ", host);
//			return;
//		}
	}

}

/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.resource.sftp.impl;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.types.Binary;
import com.dexels.navajo.resource.sftp.SFTPResource;
import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

public class SFTPResourceImpl implements SFTPResource {

	private final static Logger logger = LoggerFactory
			.getLogger(SFTPResourceImpl.class);
	
	private static final int DEFAULT_SFTP_PORT = 22;

	public String server;
	public String username;
	public String password;
	public int remotePort = DEFAULT_SFTP_PORT;

	public void activate(Map<String, Object> settings) {
		logger.debug("Activating SFTP connector with: " + settings);
		for (Entry<String, Object> e : settings.entrySet()) {
			logger.debug("key: " + e.getKey() + " value: " + e.getValue());
		}
		this.modified(settings);
		
	}

	private int parsePort(Object object) {
		if(object==null) {
			return DEFAULT_SFTP_PORT;
		}
		if(object instanceof String) {
			return Integer.parseInt((String) object);
		}
		if(object instanceof Integer) {
			return (Integer)object;
		}
		return DEFAULT_SFTP_PORT;
	}

	public void modified(Map<String,Object> settings) {
		this.server = (String) settings.get("server");
		this.username = extractUsername(settings);
		this.password = (String) settings.get("password");
		this.remotePort = parsePort(settings.get("remotePort"));
	}
	
	// Try both username and user, there is some magic in NavajoContextInstanceFactory +- 500
	private String extractUsername(Map<String, Object> settings) {
		String user = (String) settings.get("username");
		if(user==null) {
			user = (String) settings.get("user");
		}
		return user;
	}

	public void deactivate() {
		logger.debug("Deactivating HTTP connector");
	}

	@Override
	public void send(String path, String filename, Binary content) throws IOException {
      Session session = null;
      Channel channel = null;
      ChannelSftp channelSftp = null;
      logger.debug("preparing the host information for sftp.");
      InputStream data = null;
      try {
          JSch jsch = new JSch();
          session = jsch.getSession(this.username, this.server, this.remotePort);
          if(this.password!=null) {
              session.setPassword(this.password);
          }
          java.util.Properties config = new java.util.Properties();
          config.put("StrictHostKeyChecking", "no");
          session.setConfig(config);
          session.connect();
          logger.debug("Host connected.");
          channel = session.openChannel("sftp");
          channel.connect();
          logger.debug("sftp channel opened and connected.");
          channelSftp = (ChannelSftp) channel;
          if(path!=null) {
              channelSftp.cd(path);
          }
          File f = new File(filename);
          data = content.getDataAsStream();
          channelSftp.put(data, f.getName());
          logger.info("File transfered successfully to host.");
      } catch (Exception ex) {
      	throw new IOException("SFTP problem", ex);
      } 
      finally{
      	if(data!=null) {
      		try {
					data.close();
				} catch (IOException e) {
				}
      	}
      	if(channelSftp!=null) {
              channelSftp.exit();
      	}
          logger.info("sftp Channel exited.");
          if(channel!=null) {
              channel.disconnect();
          }
          logger.info("Channel disconnected.");
          if(session!=null) {
              session.disconnect();
          }
          logger.info("Host Session disconnected.");
      }
  }   

}

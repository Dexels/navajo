package com.dexels.navajo.resource.sftp;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.types.Binary;
import com.dexels.navajo.script.api.Access;
import com.dexels.navajo.script.api.Mappable;
import com.dexels.navajo.script.api.MappableException;
import com.dexels.navajo.script.api.UserException;
import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

public class SFTPMap implements Mappable {

	
	private final static Logger logger = LoggerFactory.getLogger(SFTPMap.class);
	private static final int DEFAULT_SFTP_PORT = 22;

	public String server;
	public String username;
	public String password;
	public int remotePort = DEFAULT_SFTP_PORT;

	
	public Binary content;
	public String path;
	public boolean useBinary = true;
	public String filename = "filename_not_specified";

	@Override
	public void load(Access access) throws MappableException, UserException {

	}

	@Override
	public void store() throws MappableException, UserException {
		send();
	}

	@Override
	public void kill() {

	}
	
	public void setFilename( String s ) {
		filename = s;
	}
	
	public void setContent( Binary b ) {
		content = b;
	}

	public void setServer( String s ) {
		server = s;
	}

	public void setPort( int port ) {
		remotePort = port;
	}

	public void setUsername( String s ) {
		username = s;
	}

	public void setPassword( String s ) {
		password = s;
	}

	public void setPath( String s ) {
		path = s;
	}
	
	private  void send() throws UserException {
//        String SFTPWORKINGDIR = "file/to/transfer";

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
            if(this.path!=null) {
                channelSftp.cd(this.path);
            }
            File f = new File(this.filename);
            data = content.getDataAsStream();
            channelSftp.put(data, f.getName());
            logger.info("File transfered successfully to host.");
        } catch (Exception ex) {
        	ex.printStackTrace();
        	throw new UserException("SFTP problem", ex);
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

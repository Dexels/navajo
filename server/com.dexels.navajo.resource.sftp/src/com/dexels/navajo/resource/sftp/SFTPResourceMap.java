package com.dexels.navajo.resource.sftp;

import java.io.IOException;

import com.dexels.navajo.document.types.Binary;
import com.dexels.navajo.script.api.Access;
import com.dexels.navajo.script.api.Mappable;
import com.dexels.navajo.script.api.MappableException;
import com.dexels.navajo.script.api.UserException;

public class SFTPResourceMap implements Mappable {

	public String resource = null;
	
	public Binary content;
	public String path;
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
	

	public void setResource(String resource) {
		this.resource = resource;
	}
	
	public void setFilename( String s ) {
		filename = s;
	}
	
	public void setContent( Binary b ) {
		content = b;
	}


	public void setPath( String s ) {
		path = s;
	}
	
	private  void send() throws UserException {
		if(resource == null) {
			throw new UserException(-1,"No resource set in SFTPMap");
		}
		SFTPResource sftpResource = SFTPResourceFactory.getInstance().getHttpResource(resource);
		if(sftpResource==null) {
			throw new UserException(-1,"Resource not found in SFTPMap");
		}
		try {
			sftpResource.send(this.path, this.filename, this.content);
		} catch (IOException e) {
			throw new UserException("Error sending data to SFTP",e);
		}
		
	}
}

package com.dexels.navajo.resource.sftp;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SFTPResourceFactory {

	private static SFTPResourceFactory instance = null;
	
	private final static Logger logger = LoggerFactory
			.getLogger(SFTPResourceFactory.class);
	
	private final Map<String,SFTPResource> sftpResource = new HashMap<>();
	public void activate() {
		instance = this;
	}

	public void deactivate() {
		instance = null;
	}
	
	public static SFTPResourceFactory getInstance() {
		return instance;
	}
	public void addSFTPResource(SFTPResource resource, Map<String,Object> settings) {
		String name = (String) settings.get("name");
		if(name==null) {
			logger.warn("Can not register http resource: It has no name.");
			return;
		}
		sftpResource.put(name, resource);
	}

	public void removeSFTPResource(SFTPResource resource, Map<String,Object> settings) {
		String name = (String) settings.get("name");
		if(name==null) {
			logger.warn("Can not deregister http resource: It has no name.");
			return;
		}
		sftpResource.remove(name);
	}
	
	public SFTPResource getHttpResource(String name) {
		return sftpResource.get(name);
	}
}

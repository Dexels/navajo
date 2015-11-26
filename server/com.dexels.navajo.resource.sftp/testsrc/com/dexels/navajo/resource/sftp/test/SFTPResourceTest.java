package com.dexels.navajo.resource.sftp.test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.dexels.navajo.document.types.Binary;
import com.dexels.navajo.resource.sftp.SFTPResource;
import com.dexels.navajo.resource.sftp.SFTPResourceFactory;
import com.dexels.navajo.resource.sftp.SFTPResourceMap;
import com.dexels.navajo.resource.sftp.impl.SFTPResourceImpl;
import com.dexels.navajo.script.api.Access;
import com.dexels.navajo.script.api.MappableException;
import com.dexels.navajo.script.api.UserException;

public class SFTPResourceTest {
	
	@Before
	public void setup() {
		SFTPResourceFactory factory = new SFTPResourceFactory();
		factory.activate();

		SFTPResourceImpl sftp = new SFTPResourceImpl();
		Map<String,Object> settings = new HashMap<>();
		settings.put("server",System.getenv().get("SFTPHOST"));
		settings.put("username",System.getenv().get("SFTPUSERNAME"));
		settings.put("password",System.getenv().get("SFTPPASSWORD"));
		settings.put("remotePort",System.getenv().get("SFTPPORT"));
		settings.put("name","test");
		sftp.activate(settings);
		factory.addSFTPResource(sftp, settings);
	}
	
	@Test @Ignore
	public void testSFTP() throws MappableException, UserException, IOException {
	
		SFTPResource res = SFTPResourceFactory.getInstance().getHttpResource("test");
		Binary b = new Binary(getClass().getResourceAsStream("logo.gif"));
		res.send("/share", "monkey2.gif", b);
	}
	
	@Test @Ignore
	public void testSFTPResourceMap() throws MappableException, UserException {
		SFTPResourceMap sf = new SFTPResourceMap();
		sf.load(new Access());
		sf.setResource("test");
		sf.setPath("/share");
		sf.setFilename("monkey.gif");
		Binary b = new Binary(getClass().getResourceAsStream("logo.gif"));
		sf.setContent(b);
		sf.store();
	}
}

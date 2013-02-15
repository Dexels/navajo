package com.dexels.navajo.tipi.jgit;

import org.eclipse.jgit.transport.JschConfigSessionFactory;
import org.eclipse.jgit.transport.OpenSshConfig.Host;

import com.jcraft.jsch.Session;

public class NavajoConfigSessionFactory extends JschConfigSessionFactory {

	@Override
	protected void configure(Host h, Session s) {
		

//		s.setIdentityRepository(js.getHostKeyRepository());
//		IdentityRepository i = new LocalIdentityRepository();
		
	
	}

}

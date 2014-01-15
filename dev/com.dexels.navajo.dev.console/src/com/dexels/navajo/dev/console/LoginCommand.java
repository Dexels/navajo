			package com.dexels.navajo.dev.console;
import org.apache.felix.service.command.CommandSession;
import org.apache.felix.service.command.Descriptor;

import com.dexels.navajo.client.nql.NqlContextApi;

public class LoginCommand {

//	private final BundleContext bundleContext;
	private NqlContextApi nqlContext;

	public LoginCommand() {
	}
	 
		@Descriptor(value = "sets username and password. Not that it _does not check anything_, it will only affect future calls") 
	 public void login(final CommandSession session, String username,String password) {
			nqlContext.getNavajoContext().setUsername(username);
			nqlContext.getNavajoContext().setPassword(password);
		session.getConsole().println("Credentials set.");
	 }

	public void setNqlContext(NqlContextApi nqlContext) {
		this.nqlContext = nqlContext;
	}
}

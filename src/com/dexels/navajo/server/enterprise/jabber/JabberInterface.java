package com.dexels.navajo.server.enterprise.jabber;

import org.jivesoftware.smack.*;

import com.dexels.navajo.document.*;
import com.dexels.navajo.server.*;

public interface JabberInterface {
	public void configJabber(Message jabberMessage) throws UserException;
		
}

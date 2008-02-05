package com.dexels.navajo.tipi.xmmp;

import org.jivesoftware.smack.*;
import org.jivesoftware.smack.packet.*;

import com.dexels.navajo.document.*;
import com.dexels.navajo.tipi.*;
import com.dexels.navajo.tipi.xmmp.*;

public class TipiChatClient extends TipiChatComponent {
	
	@Override
	public void appendMessage(String msg) {
	}
	


	public Object createContainer() {
		return null;
	}



	public void doTransaction(Navajo n, String service) throws TipiBreakException, TipiException {
		
	}



	public String getConnectorId() {
		return "jabber";
	}
}

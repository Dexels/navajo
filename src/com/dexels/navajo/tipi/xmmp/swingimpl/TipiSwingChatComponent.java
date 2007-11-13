package com.dexels.navajo.tipi.xmmp.swingimpl;

import org.jivesoftware.smack.*;
import org.jivesoftware.smack.packet.*;

import com.dexels.navajo.tipi.xmmp.*;

public class TipiSwingChatComponent extends TipiChatComponent {
	private SwingChatComponent chatComponent;

	@Override
	public void appendMessage(String msg) {
		// TODO Auto-generated method stub

	}
	
	public void initialize() throws XMPPException {
		super.initialize();
//		chat = connection.getChatManager().createChat("flyaruu@gmail.com",null);

		chatComponent.setMessageListener(new MessageSender(){
			public void messageSent(String msg) {
				try {
					broadcastMessage(msg);
				} catch (XMPPException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}});
	}
	



	public Object createContainer() {
		chatComponent = new SwingChatComponent();
	
		return chatComponent;
	}
}

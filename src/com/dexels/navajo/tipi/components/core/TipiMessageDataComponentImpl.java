package com.dexels.navajo.tipi.components.core;

import com.dexels.navajo.document.*;
import com.dexels.navajo.tipi.*;
import com.dexels.navajo.tipi.internal.*;

public abstract class TipiMessageDataComponentImpl extends TipiDataComponentImpl implements MessageComponent {

	private String myMessageName = null;
	private Message myMessage;

	public Message getMessage() {
		// TODO Auto-generated method stub
		return myMessage;
	}

	public String getMessageName() {
		return myMessageName;
	}

	@Override
	protected void setComponentValue(String name, Object object) {
		if(name.equals("messageName")) {
			myMessageName = (String)object;
			return;
		}
		super.setComponentValue(name, object);
	}

	public void setMessage(Message m) {
		myMessage = m;
		
	}


	
	/**
	 * @param n
	 */
	protected void loadProperties(Navajo n) {
		if(myMessage==null) {
			// No message has been set. Reverting to parent behavior.
			super.loadProperties(n);
			return;
		}
		for (int i = 0; i < properties.size(); i++) {
			PropertyComponent current = properties.get(i);
			Property p = myMessage.getProperty(current.getPropertyName());
			if (p != null) {
				try {
					getContext().debugLog("data    ",
							"delivering property: " + p.getFullPropertyName() + " to tipi: " + ((TipiComponent) current).getId());
				} catch (NavajoException ex) {
					ex.printStackTrace();
				}
			} else {
				getContext().debugLog("data    ", "delivering null property to tipi: " + ((TipiComponent) current).getId());
			}
			if (p != null) {
				current.setProperty(p);
			}
		}
	}

}

package com.dexels.navajo.tipi.components.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoException;
import com.dexels.navajo.document.Property;
import com.dexels.navajo.tipi.TipiComponent;
import com.dexels.navajo.tipi.internal.MessageComponent;
import com.dexels.navajo.tipi.internal.PropertyComponent;

public abstract class TipiMessageDataComponentImpl extends
		TipiDataComponentImpl implements MessageComponent {

	private static final long serialVersionUID = 3422365943982271264L;
	private String myMessageName = null;
	private Message myMessage;
	
	private final static Logger logger = LoggerFactory
			.getLogger(TipiMessageDataComponentImpl.class);
	
	@Override
	public Message getMessage() {
		return myMessage;
	}

	@Override
	public String getMessageName() {
		return myMessageName;
	}

	@Override
	protected void setComponentValue(String name, Object object) {
		if (name.equals("messageName")) {
			myMessageName = (String) object;
			return;
		}
		super.setComponentValue(name, object);
	}

	@Override
	public void setMessage(Message m) {
		myMessage = m;

	}

	/**
	 * @param n
	 */
	@Override
	protected void loadProperties(Navajo n) {
		if (myMessage == null) {
			// No message has been set. Reverting to parent behavior.
			super.loadProperties(n);
			return;
		}
		for (int i = 0; i < properties.size(); i++) {
			PropertyComponent current = properties.get(i);
			Property p = myMessage.getProperty(current.getPropertyName());
			if (p != null) {
				try {
					getContext().debugLog(
							"data    ",
							"delivering property: " + p.getFullPropertyName()
									+ " to tipi: "
									+ ((TipiComponent) current).getId());
				} catch (NavajoException ex) {
					logger.error("Error: ",ex);
				}
			} else {
				getContext().debugLog(
						"data    ",
						"delivering null property to tipi: "
								+ ((TipiComponent) current).getId());
			}
			if (p != null) {
				current.setProperty(p);
			}
		}
	}

}

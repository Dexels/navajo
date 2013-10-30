package com.dexels.navajo.tipi.components.swingimpl;

import java.util.HashMap;
import java.util.Map;

import javax.swing.JPanel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.tipi.TipiBreakException;
import com.dexels.navajo.tipi.TipiComponent;
import com.dexels.navajo.tipi.TipiException;
import com.dexels.navajo.tipi.TipiHelper;
import com.dexels.navajo.tipi.components.swingimpl.swing.TipiSwingHelper;
import com.dexels.navajo.tipi.internal.MessageComponent;

public class TipiMessagePanel extends TipiSwingDataComponentImpl implements
		MessageComponent {
	private static final long serialVersionUID = -3361711528499588037L;
	
	private final static Logger logger = LoggerFactory
			.getLogger(TipiMessagePanel.class);
	
	private String myMessageName = null;
	private Message myMessage;

	@Override
	public Message getMessage() {
		return myMessage;
	}

	@Override
	public String getMessageName() {
		return myMessageName;
	}

	@Override
	public void loadData(Navajo n, String method) throws TipiException,
			TipiBreakException {
		myMethod = method;
		if (n == null) {
			throw new TipiException("Loading with null Navajo! ");
		}
		myNavajo = n;
		{
			for (TipiComponent tc : propertyComponentSet) {
				tc.loadPropertiesFromMessage(myMessage);
			}
		}

	}

	@Override
	protected void setComponentValue(String name, Object object) {
		if (name.equals("messageName")) {
			myMessageName = (String) object;
			return;
		}
		if (name.equals("message")) {
			setMessage((Message) object);
			return;
		}
		super.setComponentValue(name, object);
	}

	@Override
	public void setMessage(Message m) {
		myMessage = m;
		loadProperties(m);
		loadPropertiesFromMessage(myMessage);
		Map<String, Object> eventParams = new HashMap<String, Object>();
		eventParams.put("message", m);
		eventParams.put("navajo", getNavajo());
		try {
			performTipiEvent("onLoad", eventParams, true);
		} catch (TipiBreakException e) {
			logger.error("Error detected",e);
		} catch (TipiException e) {
			logger.error("Error detected",e);
		}
	}

	@Override
	protected Object getComponentValue(String name) {
		if (name.equals("message")) {
			return getMessage();
		}
		return super.getComponentValue(name);
	}

	// /**
	// * @param n
	// *
	// * Overridden version! Uses message relative addressing
	// */
	// protected void loadProperties(Navajo n) {
	// if(myMessage==null) {
	// // No message has been set. Reverting to parent behavior.
	// // super.loadProperties(n);
	// return;
	// }
	// if(Message.MSG_TYPE_ARRAY.equals(myMessage.getType())) {
	// return;
	// }
	// getRecursiveProperties();
	// List<TipiComponent> props = getRecursiveProperties();
	// for (int i = 0; i < props.size(); i++) {
	// PropertyComponent current = (PropertyComponent) props.get(i);
	// Property p = myMessage.getProperty(current.getPropertyName());
	// if (p != null) {
	// try {
	// getContext().debugLog("data    ",
	// "delivering property: " + p.getFullPropertyName() + " to tipi: " +
	// ((TipiComponent) current).getId());
	// } catch (NavajoException ex) {
	// logger.error("Error detected",ex);
	// }
	// } else {
	// getContext().debugLog("data    ", "delivering null property to tipi: " +
	// ((TipiComponent) current).getId());
	// }
	// if (p != null) {
	// current.setProperty(p);
	// }
	// }
	// }

	@Override
	public Object createContainer() {
		JPanel myPanel = new JPanel();
		TipiHelper th = new TipiSwingHelper();
		th.initHelper(this);
		addHelper(th);
		return myPanel;
	}

	@Override
	public int getMessageIndex() {
		return Integer.parseInt(getId());
		// return 0;
	}
}

package com.dexels.navajo.tipi.components.swingimpl;

import java.util.*;

import javax.swing.*;

import com.dexels.navajo.document.*;
import com.dexels.navajo.tipi.*;
import com.dexels.navajo.tipi.components.swingimpl.swing.*;
import com.dexels.navajo.tipi.internal.*;

public class TipiMessagePanel extends TipiSwingDataComponentImpl implements MessageComponent {
	private String myMessageName = null;
	private Message myMessage;

	public Message getMessage() {
		// TODO Auto-generated method stub
		return myMessage;
	}

	public String getMessageName() {
		return myMessageName;
	}

	
	public void loadData(Navajo n, String method) throws TipiException, TipiBreakException {
		myMethod = method;
		if (n == null) {
			throw new TipiException("Loading with null Navajo! ");
		}
		myNavajo = n;
		// FIXME Beware, hacked
		if(true || "true".equals(myContext.getSystemProperty("isNewNavajoLoading"))) {
			for (TipiComponent tc : propertyComponentSet) {
				tc.loadPropertiesFromMessage(myMessage);
			}
		} else {
			loadProperties(n);
			loadPropertiesFromNavajo(n);
			loadMessages(n);
			cascadeLoad(n, method);
			doPerformOnLoad(method, n, true);
			doLayout();
		}
		
	}
	
	@Override
	protected void setComponentValue(String name, Object object) {
		if(name.equals("messageName")) {
			myMessageName = (String)object;
			return;
		}
		if(name.equals("message")) {
			setMessage((Message)object);
			return;
		}
		super.setComponentValue(name, object);
	}

	
	

	public void setMessage(Message m) {
		myMessage = m;
		loadProperties(getNavajo());
		loadPropertiesFromMessage(myMessage);
		Map<String,Object> eventParams = new HashMap<String, Object>();
		eventParams.put("message", m);
		eventParams.put("navajo", getNavajo());
		try {
			performTipiEvent("onLoad", eventParams, true);
		} catch (TipiBreakException e) {
			e.printStackTrace();
		} catch (TipiException e) {
			e.printStackTrace();
			}
	}


	
	@Override
	protected Object getComponentValue(String name) {
		if(name.equals("message")) {
			return getMessage();
		}
		return super.getComponentValue(name);
	}

//	/**
//	 * @param n
//	 *
//	 * Overridden version! Uses message relative addressing
//	 */
//	protected void loadProperties(Navajo n) {
//		if(myMessage==null) {
//			// No message has been set. Reverting to parent behavior.
////			super.loadProperties(n);
//			return;
//		}
//		if(Message.MSG_TYPE_ARRAY.equals(myMessage.getType())) {
//			return;
//		}
//		getRecursiveProperties();
//		List<TipiComponent> props  = getRecursiveProperties();
//			for (int i = 0; i < props.size(); i++) {
//			PropertyComponent current = (PropertyComponent) props.get(i);
//			Property p = myMessage.getProperty(current.getPropertyName());
//			if (p != null) {
//				try {
//					getContext().debugLog("data    ",
//							"delivering property: " + p.getFullPropertyName() + " to tipi: " + ((TipiComponent) current).getId());
//				} catch (NavajoException ex) {
//					ex.printStackTrace();
//				}
//			} else {
//				getContext().debugLog("data    ", "delivering null property to tipi: " + ((TipiComponent) current).getId());
//			}
//			if (p != null) {
//				current.setProperty(p);
//			}
//		}
//	}

	public Object createContainer() {
		JPanel myPanel = new JPanel();
		TipiHelper th = new TipiSwingHelper();
		th.initHelper(this);
		addHelper(th);
		return myPanel;
	}

	public int getMessageIndex() {
		 return Integer.parseInt(getId());
//		return 0;
	}
}

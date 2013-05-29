package com.dexels.navajo.tipi.vaadin.components;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.tipi.TipiBreakException;
import com.dexels.navajo.tipi.TipiComponent;
import com.dexels.navajo.tipi.TipiException;
import com.dexels.navajo.tipi.internal.MessageComponent;
import com.dexels.navajo.tipi.vaadin.components.base.TipiVaadinComponentImpl;
import com.vaadin.event.LayoutEvents.LayoutClickEvent;
import com.vaadin.event.LayoutEvents.LayoutClickListener;
import com.vaadin.terminal.Sizeable;
import com.vaadin.ui.VerticalLayout;

public class TipiMessagePanel extends TipiVaadinComponentImpl implements
		MessageComponent {
	private static final long serialVersionUID = -2676124688318507565L;
	private String myMessageName = null;
	private Message myMessage;
	
	private final static Logger logger = LoggerFactory
			.getLogger(TipiMessagePanel.class);
	
	public Message getMessage() {
		return myMessage;
	}

	public String getMessageName() {
		return myMessageName;
	}

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
		if(name.equals("height")){
			((Sizeable) getContainer()).setHeight("" + object);
		}
		super.setComponentValue(name, object);
	}

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
			logger.error("Error: ",e);
		} catch (TipiException e) {
			logger.error("Error: ",e);
		}
	}

	@Override
	protected Object getComponentValue(String name) {
		if (name.equals("message")) {
			return getMessage();
		}
		if(name.equals("height")){
			return ((Sizeable) getContainer()).getHeight();
		}
		return super.getComponentValue(name);
	}

	public Object createContainer() {
		VerticalLayout myPanel = new VerticalLayout();		
		myPanel.addListener(new LayoutClickListener() {
	            private static final long serialVersionUID = 5527999180793601282L;
	            @Override
	            public void layoutClick(LayoutClickEvent event) {
	                try {
	                	performTipiEvent("onActionPerformed", null, true);
					} catch (TipiBreakException e) {
						logger.error("Error: ",e);
					} catch (TipiException e) {
						logger.error("Error: ",e);
					}
	            }
	        });
		return myPanel;
	}

	public int getMessageIndex() {
		return Integer.parseInt(getId());
		// return 0;
	}
}

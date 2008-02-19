package com.dexels.navajo.tipi.components.swingimpl;

import java.lang.reflect.*;
import java.util.*;

import javax.swing.*;

import com.dexels.navajo.document.*;
import com.dexels.navajo.tipi.components.swingimpl.swing.*;

public class TipiSwingMessageView extends TipiSwingDataComponentImpl {

	@Override
	protected void setComponentValue(String name, final Object object) {
		super.setComponentValue(name, object);
		if(name.equals("message")) {
			try {
				SwingUtilities.invokeAndWait(new Runnable(){

					public void run() {
						setMessage((Message) object);
					}});
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	JPanel myPanel = null;
	private Message myMessage = null;
	@Override
	public Object createContainer() {
		myPanel = new JPanel();
		myPanel.setLayout(new BoxLayout(myPanel,BoxLayout.Y_AXIS));
		return myPanel;
	}
	
	public Message getMessage() {
		return myMessage;
	}
	
	public void setMessage(Message m) {
		myPanel.removeAll();
		List<Property> al = m.getAllProperties();
		for (Iterator<Property> iter = al.iterator(); iter.hasNext();) {
			Property element = iter.next();
			TipiSwingPropertyComponent tspc = new TipiSwingPropertyComponent(this);
			tspc.setLabelIndent(100);
			myPanel.add(tspc);
			tspc.setProperty(element);
		}
	}


	

}

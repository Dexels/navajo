/*
 * Created on Mar 21, 2006
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.dexels.navajo.tipi.components.swingimpl;

import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Method;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoException;
import com.dexels.navajo.document.Property;
import com.dexels.navajo.tipi.TipiBreakException;
import com.dexels.navajo.tipi.TipiComponent;
import com.dexels.navajo.tipi.TipiComponentMethod;
import com.dexels.navajo.tipi.TipiException;
import com.dexels.navajo.tipi.components.swingimpl.swing.TipiSwingGridBagConstraints;
import com.dexels.navajo.tipi.internal.TipiEvent;
import com.dexels.navajo.tipi.tipixml.CaseSensitiveXMLElement;
import com.dexels.navajo.tipi.tipixml.XMLElement;

public class TipiNavajoView extends TipiPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6895145174946001728L;
	public Navajo myNavajo = null;

	public Object createContainer() {
		Container container = (Container) super.createContainer();
		container.setLayout(new GridBagLayout());
		return container;
	}

	public void addToContainer(final Object c, Object constraints) {
		final int currentCount = getChildCount();
		runSyncInEventThread(new Runnable() {

			public void run() {
				TipiNavajoView.super.addToContainer(c,
						new TipiSwingGridBagConstraints(0, currentCount, 1, 1,
								1, 1, GridBagConstraints.NORTHWEST,
								GridBagConstraints.BOTH,
								new Insets(0, 0, 0, 0), 1, 0));

			}
		});
	}

	protected void setComponentValue(String name, Object object) {
		super.setComponentValue(name, object);
		if (name.equals("navajo")) {
			Navajo n = (Navajo) object;
			try {
				createNavajoXML(n);
				myNavajo = n;
			} catch (NavajoException e) {
				e.printStackTrace();
			} catch (TipiBreakException e) {
				e.printStackTrace();
			}
		}
	}

	private synchronized void createNavajoXML(final Navajo n)
			throws NavajoException, TipiBreakException {
		for (int i = 0; i < getChildCount(); i++) {
			TipiComponent c = getTipiComponent(i);
			myContext.disposeTipiComponent(c);
		}
		String navajoName = "Navajo";
		if (n.getHeader() != null) {
			if (n.getHeader().getRPCName() != null) {
				navajoName = n.getHeader().getRPCName();
			}
		}
		final XMLElement root = createTipiXML(n, navajoName);
		myContext.addTipiDefinition(root);

		System.err.println(root.toString());

		final XMLElement inst = createInstanceXML();

		// runSyncInEventThread(new Runnable(){
		//
		// public void run() {
		try {
			addComponentInstance(myContext, inst, "Center");
		} catch (TipiException e1) {
			e1.printStackTrace();
		}

		try {
			loadData(n, "NavajoView");
		} catch (TipiException e) {
			e.printStackTrace();
		}

	}

	private XMLElement createTipiXML(final Navajo n, String navajoName)
			throws NavajoException {
		XMLElement root = new CaseSensitiveXMLElement();
		root.setName("d.split");
		root.setAttribute("border", "{border:/titled-" + navajoName + "}");
		root.setAttribute("name", "NavajoView");
		root.setAttribute("dividerlocation", "200");
		root.setAttribute("inverted", "true");
		root.setAttribute("service", "NavajoView");

		// XMLElement rootlayout = new CaseSensitiveXMLElement();
		// rootlayout.setName("l.border");
		// root.addChild(rootlayout);
		XMLElement rootScroll = new CaseSensitiveXMLElement();
		rootScroll.setName("c.scroll");
		rootScroll.setAttribute("constraint", "top");
		root.addChild(rootScroll);

		XMLElement toolbar = new CaseSensitiveXMLElement();
		toolbar.setName("c.panel");
		toolbar.setAttribute("constraint", "bottom");
		root.addChild(toolbar);
		XMLElement tbFlow = new CaseSensitiveXMLElement();
		tbFlow.setName("l.flow");
		toolbar.addChild(tbFlow);
		appendmethods(tbFlow, n);

		XMLElement ay = new CaseSensitiveXMLElement();
		ay.setName("c.tipi");
		rootScroll.addChild(ay);

		XMLElement al = new CaseSensitiveXMLElement();
		al.setName("l.border");
		ay.addChild(al);

		XMLElement e = new CaseSensitiveXMLElement();
		e.setName("c.tipi");
		e.setAttribute("constraint", "North");
		al.addChild(e);

		XMLElement layout = new CaseSensitiveXMLElement();
		layout.setName("l.gridbag");
		e.addChild(layout);

		ArrayList<Message> aaa = n.getAllMessages();
		int index = 0;
		for (Message message : aaa) {
			appendMessage(layout, message, index++);
		}
		return root;
	}

	@Override
	protected void performComponentMethod(String name,
			TipiComponentMethod compMeth, TipiEvent event)
			throws TipiBreakException {
		super.performComponentMethod(name, compMeth, event);
		if (name.equals("fireMethod")) {
			System.err.println("Fire methof!!!");
			Map<String, Object> eventParam = new HashMap<String, Object>();
			eventParam.put("service",
					compMeth.getEvaluatedParameterValue("methodName", event));
			eventParam.put("input",
					compMeth.getEvaluatedParameterValue("input", event));

			try {
				performTipiEvent("onMethodFired", eventParam, false);
			} catch (TipiException e) {
				e.printStackTrace();
			}
		}
	}

	private XMLElement createInstanceXML() {
		final XMLElement inst = new CaseSensitiveXMLElement();
		inst.setName("component");
		inst.setAttribute("name", "NavajoView");
		inst.setAttribute("id", "GeneratedNavajo");
		return inst;
	}

	private void appendmethods(XMLElement toolbar, Navajo n) {
		String myName = n.getHeader().getRPCName();
		ArrayList<Method> mm = n.getAllMethods();
		for (Method method : mm) {

			XMLElement button = new CaseSensitiveXMLElement();
			button.setName("c.button");
			button.setAttribute("text", "'" + method.getName() + "'");
			toolbar.addChild(button);

			XMLElement onActionperformed = new CaseSensitiveXMLElement();
			onActionperformed.setName("onActionPerformed");
			button.addChild(onActionperformed);

			XMLElement firemethod = new CaseSensitiveXMLElement();
			firemethod.setName("navajoview.fireMethod");
			firemethod.setAttribute("methodName", "'" + method.getName() + "'");
			firemethod.setAttribute("input", "{navajo:/" + myName + "}");
			firemethod.setAttribute("path", "{component:/../../..}");

			onActionperformed.addChild(firemethod);

			// XMLElement callService = new CaseSensitiveXMLElement();
			// callService.setName("callService");
			// callService.setAttribute("input","{navajo:/"+myName+"}");
			// callService.setAttribute("service","'"+method.getName()+"'");
			// callService.setAttribute("connector","ToString({property:/Extension:Overview/DevelopConnector})");
			// onActionperformed.addChild(callService);
			//
			// XMLElement setNavajo = new CaseSensitiveXMLElement();
			// setNavajo.setName("performTipiMethod");
			// setNavajo.setAttribute("path","{component://init/tipi/navajoDiv/tipiPanel/navajoList}");
			// setNavajo.setAttribute("name","'selectByValue'");
			// setNavajo.setAttribute("propertyName","'Name'");
			// setNavajo.setAttribute("value","'"+method.getName()+"'");
			//
			// +method.getName()+"}");
			// setNavajo.setAttribute("to","{attributeref:/"+this.getStateMessage().getFullMessageName()+":navajo}");

			// onActionperformed.addChild(setNavajo);

		}
	}

	private void appendMessage(XMLElement parent, Message message, int index)
			throws NavajoException {
		if (isTableable(message)) {
			XMLElement e = new CaseSensitiveXMLElement();
			e.setName("c.tipitable");
			e.setAttribute("border", "{border:/titled-" + message.getName()
					+ "}");
			e.setAttribute("messagepath", "'" + message.getFullMessageName()
					+ "'");
			e.setAttribute("useScroll", "false");
			// return new TipiSwingGridBagConstraints(0, index, 1, 1, 0, 0,
			// GridBagConstraints.NORTHWEST, GridBagConstraints.HORIZONTAL, new
			// Insets(5, 10, 0, 0), 1, 0);
			e.setAttribute("constraint", "0," + index + ",1,1,1.0,1.0,"
					+ GridBagConstraints.CENTER + "," + GridBagConstraints.BOTH
					+ ",0,0,0,0,0,0");
			// e.setAttribute("id","NavajoView");

			parent.addChild(e);
		} else {
			XMLElement e = new CaseSensitiveXMLElement();
			e.setName("c.tipi");
			e.setAttribute("border", "{border:/titled-" + message.getName()
					+ "}");
			e.setAttribute("constraint", "0," + index + ",1,1,1.0,1.0,"
					+ GridBagConstraints.CENTER + "," + GridBagConstraints.BOTH
					+ ",0,0,0,0,0,0");
			// e.setAttribute("id","NavajoView");
			XMLElement layout = new CaseSensitiveXMLElement();
			layout.setName("l.gridbag");
			e.addChild(layout);
			ArrayList<Message> aaa = message.getAllMessages();
			int ind = 0;
			for (Message m : aaa) {
				appendMessage(layout, m, ind++);
			}
			ArrayList<Property> ppp = message.getAllProperties();
			// int pind = 0;
			for (Property p : ppp) {
				appendProperty(layout, p, ind++);
			}

			parent.addChild(e);
		}
	}

	private boolean isTableable(Message message) {
		if (!message.getType().equals(Message.MSG_TYPE_ARRAY)) {
			return false;
		}
		for (Message e : message.getAllMessages()) {
			if (e.getArraySize() > 0) {
				return false;
			}
			for (Property p : e.getAllProperties()) {
				if (p.getType().equals(Property.BINARY_PROPERTY)) {
					return false;
				}
			}
		}
		return true;
	}

	private void appendProperty(XMLElement layout, Property p, int index)
			throws NavajoException {
		XMLElement e = new CaseSensitiveXMLElement();
		e.setName("c.property");
		e.setAttribute("propertyname", "'" + p.getFullPropertyName() + "'");
		e.setAttribute("constraint", "0," + index + ",1,1,1.0,1.0,"
				+ GridBagConstraints.CENTER + "," + GridBagConstraints.BOTH
				+ ",0,0,0,0,0,0");
		layout.addChild(e);
	}
}

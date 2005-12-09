package com.dexels.navajo.tipi.components.echoimpl;

import java.util.ArrayList;
import java.util.Vector;

import nextapp.echo2.app.event.ActionEvent;
import nextapp.echo2.app.event.ActionListener;

import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.Property;
import com.dexels.navajo.tipi.TipiContext;
import com.dexels.navajo.tipi.TipiException;
import com.dexels.navajo.tipi.components.echoimpl.impl.MessageTable;
import com.dexels.navajo.tipi.tipixml.XMLElement;

import echopointng.ContainerEx;
import echopointng.table.PageableTableNavigation;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2004
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author Frank Lyaruu
 * @version 1.0
 */

public class TipiTable extends TipiEchoDataComponentImpl {

	private String messagePath = "";

	private boolean colDefs = false;

	private MessageTable myTable;

	public TipiTable() {
	}

	public Object createContainer() {
		ContainerEx myContainer = new ContainerEx();
		myTable = new MessageTable();
		myTable.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					performTipiEvent("onActionPerformed", null, true);
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		});
		PageableTableNavigation ptn = new PageableTableNavigation(myTable);
		// myTable.addPropertyChangeListener(new PropertyChangeListener(){
		//
		// public void propertyChange(PropertyChangeEvent evt) {
		// // TODO Auto-generated method stub
		// System.err.println("AAAAAAAAAAAAAP: "+evt.getPropertyName());
		// }});
		myContainer.add(myTable);
		myContainer.add(ptn);
		return myContainer;
	}

	public Object getActualComponent() {
		return myTable;
	}

	public void loadData(Navajo n, TipiContext context, String method)
			throws TipiException {
		super.loadData(n, context, method);
		MessageTable mm = (MessageTable) getActualComponent();
		// System.err.println("Navajo: ");
		// try {
		// n.write(System.err);
		// } catch (NavajoException e) {
		// e.printStackTrace();
		// }
		// System.err.println("------------------------------------------------------------------------------------>>
		// Path: " + messagePath);
		Message m = n.getMessage(messagePath);
		// System.err.println("------------------------------------------------------------------------------------>>
		// Got message: " + m);
		if (m != null) {
			if (!colDefs) {
				mm.removeAllColumns();
				ArrayList props = m.getMessage(0).getAllProperties();
				for (int i = 0; i < props.size(); i++) {
					Property p = (Property) props.get(i);
					mm.addColumn(p.getName(), p.getName(), false);
				}
			}
			System.err.println("Loading table. Message size: "
					+ m.getArraySize());
			mm.setMessage(m);
		}
	}

	public Object getComponentValue(String aap) {
		MessageTable mm = (MessageTable) getActualComponent();
		if ("selectedMessage".equals(aap)) {
			return mm.getSelectedMessage();
		}
		return super.getComponentValue(aap);
	}

	public void load(XMLElement elm, XMLElement instance, TipiContext context)
			throws com.dexels.navajo.tipi.TipiException {
		MessageTable mm = (MessageTable) getActualComponent();
		// TipiSwingColumnAttributeParser cap = new
		// TipiSwingColumnAttributeParser();
		messagePath = (String) elm.getAttribute("messagepath");
		if (messagePath != null) {
			if (messagePath.startsWith("'") && messagePath.endsWith("'")) {
				messagePath = messagePath
						.substring(1, messagePath.length() - 1);
			}
		}
		super.load(elm, instance, context);
		Vector children = elm.getChildren();
		for (int i = 0; i < children.size(); i++) {
			XMLElement child = (XMLElement) children.elementAt(i);
			if (child.getName().equals("column")) {
				String label = (String) child.getAttribute("label");
				String name = (String) child.getAttribute("name");
				String editableString = (String) child.getAttribute("editable");
				boolean editable = "true".equals(editableString);
				colDefs = true;
				// System.err.println("Adding column " + name + ", editable: " +
				// editable);
				mm.addColumn(name, label, editable);
				// mm.messageChanged();
			}
			if (child.getName().equals("column-attribute")) {
				String name = (String) child.getAttribute("name");
				String type = (String) child.getAttribute("type");
				// if (name != null && type != null && !name.equals("") &&
				// !type.equals("")) {
				// columnAttributes.put(name, cap.parseAttribute(child));
				// }
			}
		}
		// mm.setColumnAttributes(columnAttributes);
	}

	public void setComponentValue(String id, Object value) {
		super.setComponentValue(id, value);
	}

}

package com.dexels.navajo.tipi.vaadin.components;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.Operand;
import com.dexels.navajo.tipi.TipiBreakException;
import com.dexels.navajo.tipi.TipiContext;
import com.dexels.navajo.tipi.TipiException;
import com.dexels.navajo.tipi.tipixml.XMLElement;
import com.dexels.navajo.tipi.vaadin.components.base.TipiVaadinComponentImpl;
import com.dexels.navajo.tipi.vaadin.components.impl.MessageTable;
import com.dexels.navajo.tipi.vaadin.document.ArrayMessageBridge;
import com.dexels.navajo.tipi.vaadin.document.MessageBridge;
import com.vaadin.data.Container;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.ui.Table;

public class TipiTable extends TipiVaadinComponentImpl {

	private Table table;
	private String messagepath;
	private final List<String> visibleColumns = new ArrayList<String>();
//	private final Map<Object, Message> messageMap = new HashMap<Object, Message>();
	private Object selectedId = null;
	private Container messageBridge;
	
	private int selectedIndex = -1;

	private final static Logger logger = LoggerFactory.getLogger(TipiTable.class);
	@Override
	public Object createContainer() {
		table = new MessageTable();
//		table.setRowHeaderMode(Table.ROW_HEADER_MODE_ID);
		table.setSelectable(true);
		table.setImmediate(true);
//		table.setPageLength(5);
//		table.setHeight("100px");
//		table.setSizeFull();
		table.addListener(new Table.ValueChangeListener() {

			private static final long serialVersionUID = 1L;

			@Override
			public void valueChange(ValueChangeEvent event) {

				System.err.println("Selected message!" + table.getValue());
				selectedId = table.getValue();
				Message selectedM = getSelectedMessage();
				Map<String, Object> tempMap = new HashMap<String, Object>();
				// TODO fix
				tempMap.put("selectedIndex", selectedIndex);
				tempMap.put("selectedMessage", selectedM);
				try {
					performTipiEvent("onSelectionChanged", tempMap, false);
				} catch (TipiBreakException e) {
					e.printStackTrace();
				} catch (TipiException e) {
					e.printStackTrace();
				}
			}


		});
		return table;
	}

	
	private Message getSelectedMessage() {
		if(selectedId==null) {
			selectedIndex = -1;
			return null;
		}
		selectedIndex = (Integer)selectedId;
		MessageBridge mb = (MessageBridge) messageBridge.getItem(selectedId);
		if(mb==null) {
			return null;
		}
		
		return mb.getSource();
	}
	
	@Override
	public void loadData(Navajo n, String method) throws TipiException, TipiBreakException {
		super.loadData(n, method);
		Message m = n.getMessage(messagepath);

		if (m != null) {
			messageBridge = createMessageContainer(m);
			table.setContainerDataSource(messageBridge);
		} else {
			logger.error("Can not load null message: "+messagepath);
		}
//		table.setVisibleColumns(visibleColumns.toArray());
	}

	private Message getModelMessage(Message m) {
		Message def = m.getDefinitionMessage();
		if (def == null) {
			if (m.getArraySize() == 0) {
				return null;
			} else {
				def = m.getAllMessages().get(0);
			}
		}
		return def;
	}

	public void setComponentValue(final String name, final Object object) {
		super.setComponentValue(name, object);
		if (name.equals("messagepath")) {
			this.messagepath = (String) object;
		}
		if (name.equals("rowsPerPage")) {
			table.setPageLength((Integer)object);
		}
		
	}

	public Object getComponentValue(String name) {
		if ("selectedMessage".equals(name)) {
			return getSelectedMessage();
		}
		if (name.equals("selectedIndex")) {
			if(selectedId==null) {
				return -1;
			}
			return selectedId;
		}
		return super.getComponentValue(name);
	}

	
	private Container createMessageContainer(Message m) {
		ArrayMessageBridge amb = new ArrayMessageBridge(m,visibleColumns);

		return amb;
	}
//	private IndexedContainer createOldMessageContainer(Message m) {
//		IndexedContainer icc = new IndexedContainer();
////		visibleColumns = new ArrayList<Object>();
//		Message model = getModelMessage(m);
//		if (model == null) {
//			return null;
//		}
//		List<Property> allProps = model.getAllProperties();
//		for (Property property : allProps) {
//			// String label = property.getDescription();
//			// if(label==null || "".equals(label)) {
//			// }
//			String label = property.getName();
//			Class<?> cls = NavajoFactory.getInstance().getJavaType(property.getType());
//			if (cls == null) {
//				cls = String.class;
//			}
//			icc.addContainerProperty(label, cls, "");
//			visibleColumns.add(label);
//		}
//
//		for (Message message : m.getAllMessages()) {
//			Object id = icc.addItem();
//			// Object[] row = createRow(message);
////			messageMap.put(id, message);
//			// table.addItem(row,message);
//			for (Property property : message.getAllProperties()) {
//				// String label = property.getDescription();
//				// if(label==null || "".equals(label)) {
//				// }
//				String label = property.getName();
//				Class<?> cls = NavajoFactory.getInstance().getJavaType(property.getType());
//				if (cls == null) {
//					cls = String.class;
//				}
//				icc.getContainerProperty(id, label).setValue(property.getTypedValue());
//			}
//
//		}
//		return icc;
//	}

	public void load(final XMLElement elm, final XMLElement instance, final TipiContext context)
			throws com.dexels.navajo.tipi.TipiException {
		super.load(elm, instance, context);
		runSyncInEventThread(new Runnable() {

			public void run() {
				try {
					TipiTable.super.load(elm, instance, context);
				} catch (TipiException e) {
					e.printStackTrace();
				}
				Table mm = table;

				boolean editableColumnsFound = false;

				List<XMLElement> children = elm.getChildren();
				for (int i = 0; i < children.size(); i++) {
					XMLElement child = children.get(i);
					if (child.getName().equals("column")) {
						Operand o = evaluate(child.getStringAttribute("label"), TipiTable.this, null);
						String label = null;
						if (o == null) {
							label = "";
						} else {
							label = (String) o.value;
							if (label == null) {
								label = "";
							}
						}
						String name = (String) child.getAttribute("name");
						String editableString = (String) child.getAttribute("editable");
						int size = child.getIntAttribute("size", -1);

						boolean editable = "true".equals(editableString);
//						colDefs = true;
						visibleColumns.add(name);
//						mm.addColumn(name, label, editable, size);
						editableColumnsFound = editableColumnsFound || editable;
					}
					if (child.getName().equals("column-attribute")) {
						String name = (String) child.getAttribute("name");
						String type = (String) child.getAttribute("type");
					
					}
				}
			}
		});

	}

}

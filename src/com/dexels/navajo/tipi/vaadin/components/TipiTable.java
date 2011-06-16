package com.dexels.navajo.tipi.vaadin.components;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoException;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.document.Property;
import com.dexels.navajo.tipi.TipiBreakException;
import com.dexels.navajo.tipi.TipiException;
import com.dexels.navajo.tipi.vaadin.components.base.TipiVaadinComponentImpl;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.ui.Component;
import com.vaadin.ui.Table;

public class TipiTable extends TipiVaadinComponentImpl {

	private Table table;
	private String messagepath;
	private List<String> visibleColumns;
	private final Map<Object, Message> messageMap = new HashMap<Object, Message>();
	private Object selectedId = null;
	
	@Override
	public Object createContainer() {
		table = new Table();
		table.setSelectable(true);
		table.setImmediate(true);
		table.addListener(new Table.ValueChangeListener() {
			
			@Override
			public void valueChange(ValueChangeEvent event) {
				
				System.err.println("Selected message!"+table.getValue());
				selectedId = table.getValue();
				Map<String, Object> tempMap = new HashMap<String, Object>();
				// TODO fix
				tempMap.put("selectedIndex", new Integer(0));
				tempMap.put("selectedMessage", messageMap.get(selectedId));
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

	@Override
	public void loadData(Navajo n, String method) throws TipiException,
			TipiBreakException {
		super.loadData(n, method);
		Message m = n.getMessage(messagepath);
		
		System.err.println("Loading message: "+m.getArraySize());
//		table.removeAllItems();
//		List<Message> res = m.getAllMessages();
//		setupHeaders(m);
//		for (Message message : res) {
//			Object[] row = createRow(message);
//			table.addItem(row,message);
//		}
//		table.commit();
		 table.setContainerDataSource(createMessageContainer(m));
		 table.setVisibleColumns(visibleColumns.toArray());
	}

	

	private Message getModelMessage(Message m) {
		Message def = m.getDefinitionMessage();
		if(def==null) {
			if(m.getArraySize()==0) {
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
		        	this.messagepath = (String)object;
		        }
	  }

	public Object getComponentValue(String name) {
		if ("selectedMessage".equals(name)) {
			return messageMap.get(selectedId);
		}
		if (name.equals("selectedIndex")) {
			// TODO fix
			return new Integer(0);
		}
		return super.getComponentValue(name);
	}

	private IndexedContainer createMessageContainer(Message m) {
		IndexedContainer icc = new IndexedContainer();
		visibleColumns = new ArrayList<String>();
		Message model = getModelMessage(m);
		if(model==null) {
			return null;
		}
		List<Property> allProps = model.getAllProperties();
		for (Property property : allProps) {
//			String label = property.getDescription();
//			if(label==null || "".equals(label)) {
//			}
			String label = property.getName();
			Class<?> cls = NavajoFactory.getInstance().getJavaType(property.getType());
			if(cls==null) {
				cls = String.class;
			}
			icc.addContainerProperty(label, cls, "");
			visibleColumns.add(label);
		}
		

		for (Message message : m.getAllMessages()) {
			Object id = icc.addItem();
//			Object[] row = createRow(message);
			messageMap.put(id, message);
//			table.addItem(row,message);
			for (Property property : message.getAllProperties()) {
//				String label = property.getDescription();
//				if(label==null || "".equals(label)) {
//				}
				String label = property.getName();
				Class<?> cls = NavajoFactory.getInstance().getJavaType(property.getType());
				if(cls==null) {
					cls = String.class;
				}
				icc.getContainerProperty(id, label).setValue(property.getTypedValue());
			}
			
		}
		return icc;
	}
}

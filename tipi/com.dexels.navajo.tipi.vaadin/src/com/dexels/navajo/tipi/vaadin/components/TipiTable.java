package com.dexels.navajo.tipi.vaadin.components;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.client.NavajoClientFactory;
import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoException;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.document.Operand;
import com.dexels.navajo.document.types.Binary;
import com.dexels.navajo.tipi.TipiBreakException;
import com.dexels.navajo.tipi.TipiComponentMethod;
import com.dexels.navajo.tipi.TipiContext;
import com.dexels.navajo.tipi.TipiException;
import com.dexels.navajo.tipi.internal.TipiEvent;
import com.dexels.navajo.tipi.tipixml.XMLElement;
import com.dexels.navajo.tipi.vaadin.components.base.TipiVaadinComponentImpl;
import com.dexels.navajo.tipi.vaadin.components.impl.MessageTable;
import com.dexels.navajo.tipi.vaadin.components.impl.TmlTableFieldFactory;
import com.dexels.navajo.tipi.vaadin.document.CompositeArrayContainer;
import com.dexels.navajo.tipi.vaadin.document.CompositeMessageBridge;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.ui.Table;

public class TipiTable extends TipiVaadinComponentImpl {

	private static final long serialVersionUID = 1440706945170166712L;
	private Table table;
	private String messagepath;
	private final List<String> visibleColumns = new ArrayList<String>();
	private final List<String> editableColumns = new ArrayList<String>();
	private final Map<String,Integer> columnSizes = new HashMap<String,Integer>();
	private final Map<String,String> columnLabels = new HashMap<String,String>();
//	private final Map<Object, Message> messageMap = new HashMap<Object, Message>();
	private Object selectedId = null;
	private CompositeArrayContainer messageBridge;
	
	private int selectedIndex = -1;
	private Message currentMessage;

	private final static Logger logger = LoggerFactory.getLogger(TipiTable.class);
	@Override
	public Object createContainer() {
		table = new MessageTable();
		table.setStyleName("striped");
		table.setSelectable(true);
		table.setImmediate(true);
		
		table.setSortDisabled(false);
		table.setColumnReorderingAllowed(true);
		table.setEditable(false);
//		table.setColumnCollapsingAllowed(true);
		table.setTableFieldFactory(new TmlTableFieldFactory());
//		table.set
		table.addListener(new Table.ValueChangeListener() {

			private static final long serialVersionUID = 1L;

			@Override
			public void valueChange(ValueChangeEvent event) {

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
		
		table.addListener(new ItemClickEvent.ItemClickListener() {
			private static final long serialVersionUID = 2068314108919135281L;

			public void itemClick(ItemClickEvent event) {
				if (event.isDoubleClick()) {
					selectedId = table.getValue();
					Message selectedM = getSelectedMessage();
					Map<String, Object> tempMap = new HashMap<String, Object>();
					// TODO fix
					tempMap.put("selectedIndex", selectedIndex);
					tempMap.put("selectedMessage", selectedM);
					try {
						performTipiEvent("onActionPerformed", tempMap, false);
					} catch (TipiBreakException e) {
						e.printStackTrace();
					} catch (TipiException e) {
						e.printStackTrace();
					}

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
		CompositeMessageBridge mb = (CompositeMessageBridge) messageBridge.getItem(selectedId);
		if(mb==null) {
			return null;
		}
		
		return mb.getSource();
	}
	
	@Override
	public void loadData(Navajo n, String method) throws TipiException, TipiBreakException {
		if(messagepath==null) {
			throw new NullPointerException("message path in table is null, set it before loading!");
		}
		super.loadData(n, method);
		Message m = n.getMessage(messagepath);
		currentMessage = m;
		if (m != null) {
			messageBridge = createMessageContainer(m);
			table.setContainerDataSource(messageBridge);
		} else {
			logger.error("Can not load null message: "+messagepath);
		}
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

	
	private CompositeArrayContainer createMessageContainer(Message m) {
		if(m==null) {
			return null;
		}
		CompositeArrayContainer amb = new CompositeArrayContainer(m,visibleColumns,editableColumns,columnSizes);
		Message exa = amb.getExampleMessage();
		if(exa==null) {
			return null;
		}
		for (String col : visibleColumns) {
			String label = columnLabels.get(col);
			if(label==null) {
				label = amb.getPropertyAspect(col+"@description");
				if(label==null) {
					label = amb.getPropertyAspect(col+"@name");
				}
			}
			Class<?> type = NavajoFactory.getInstance().getJavaType(amb.getPropertyAspect(col+"@type"));
			String alignment = Table.ALIGN_LEFT;
			
			
			if(type!=null && type.isAssignableFrom(Number.class)) {
				alignment = Table.ALIGN_RIGHT;
			}
			table.setColumnAlignment(col+"@value", alignment);
			int size = columnSizes.get(col);
			if(size>=0) {
				table.setColumnWidth(col+"@value", size);
			}
			table.setColumnHeader(col+"@value", label);
		}
		return amb;
	}

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
//				Table mm = table;

				boolean columnsFound = false;

				List<XMLElement> children = elm.getChildren();
				for (int i = 0; i < children.size(); i++) {
					XMLElement child = children.get(i);
					if (child.getName().equals("column")) {
						Operand o = evaluate(child.getStringAttribute("label"), TipiTable.this, null);
						String label = null;
						String name = (String) child.getAttribute("name");
						if (o != null) {
							label = (String) o.value;
						}
						if(label!=null) {
							columnLabels.put(name, label);
						}
						int size = child.getIntAttribute("size", -1);
						columnSizes.put(name,size);
						String editableString = (String) child.getAttribute("editable");
						boolean editable = "true".equals(editableString);
						visibleColumns.add(name);
						if(editable) {
							editableColumns.add(name);
						}
						columnsFound = true;
					}
					if (child.getName().equals("column-attribute")) {
					}
				}
				if(!columnsFound) {
					editableColumns.addAll(visibleColumns);
				}
			}
		});
	}

	protected void performComponentMethod(String name, TipiComponentMethod compMeth, TipiEvent event) {
		int count = this.table.getContainerDataSource().size();
		if (count != 0) {
	
			if ("printReport".equals(name)) {
				try {
					getTableReport("pdf", "horizontal", new int[] { 10, 10, 10, 10 });
				} catch (NavajoException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	private void printTable(Binary b) {
		try {
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("report", b);
			performTipiEvent("onReport", param, true);
		} catch (TipiException e) {
			e.printStackTrace();
		}
	}


	public void getTableReport(String format, String orientation,
			int[] margins) throws NavajoException {

		Collection<?> cids = table.getContainerDataSource().getContainerPropertyIds();
		Message m = currentMessage.copy(NavajoFactory.getInstance().createNavajo()); // getMessageAsPresentedOnTheScreen(false);
		if (m == null) {
			throw NavajoFactory.getInstance().createNavajoException(
					"No message loaded, can not get message!");
		}
		
		int count = cids.size(); // getColumnModel().getColumnCount() - 1;

		int[] widths = new int[count];
		String[] namesarray = new String[count];
		String[] titles = new String[count];
		// ECHO SPECIFIC: SKIP THE FIRST COLUMN!!!
		//
		for (Object id : cids) {
			logger.debug("ID: "+id);
		}
		int i = 0;
		for (Object propertyId : cids) {
			// TableColumn tt = getColumnModel().getColumn(j);
			int width = table.getColumnWidth(propertyId);
			String name = ((String)propertyId).split("@")[0]; // ids.get(j); // getColumnId(j);
			String title = table.getColumnHeader(propertyId); // ""+tt.getHeaderValue();
			widths[i] = width;
			namesarray[i] = name.trim();
			titles[i] = title; 
			logger.debug("Adding width: " + width);
			logger.debug("Adding name: " + name.trim());
			i++;
		}

		Binary result = NavajoClientFactory.getClient().getArrayMessageReport(
				m, namesarray, titles, widths, format, orientation, margins);
		printTable(result);
	}
}

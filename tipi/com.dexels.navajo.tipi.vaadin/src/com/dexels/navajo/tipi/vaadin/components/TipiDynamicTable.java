package com.dexels.navajo.tipi.vaadin.components;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.tipi.TipiBreakException;
import com.dexels.navajo.tipi.TipiException;
import com.dexels.navajo.tipi.actions.TipiInstantiateTipi;
import com.dexels.navajo.tipi.vaadin.document.CompositeArrayContainer;
import com.dexels.navajo.tipi.vaadin.document.CompositeMessageBridge;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.ui.Component;
import com.vaadin.ui.Table;

public class TipiDynamicTable extends TipiMessagePanel  {

	private static final long serialVersionUID = 1440706945170166712L;
	private Table table;
	private String messagepath;
//	private final Map<String,String> columnLabels = new HashMap<String,String>();
	private Object selectedId = null;
	private CompositeArrayContainer messageBridge;
	
	private int selectedIndex = -1;
	private String definitionName;
	
	private final static Logger logger = LoggerFactory.getLogger(TipiDynamicTable.class);

	@Override
	public Object createContainer() {
		table = new Table();
		table.setSelectable(true);
		table.setImmediate(true);
		table.setSortDisabled(true);
		table.setColumnReorderingAllowed(false);
		table.setEditable(false);
		table.setColumnCollapsingAllowed(false);
		table.setColumnHeaderMode(Table.COLUMN_HEADER_MODE_HIDDEN);
		//		table.setWidth("100%");
		table.setSizeFull();
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
					logger.error("Error: ",e);
				} catch (TipiException e) {
					logger.error("Error: ",e);
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

		if (m != null) {
			messageBridge = createMessageContainer(m);
			table.setContainerDataSource(messageBridge);
//			Container c = messageBridge;
		} else {
			logger.error("Can not load null message: "+messagepath);
		}
		table.removeGeneratedColumn("message");
        table.addGeneratedColumn("message", new Table.ColumnGenerator() {
            private static final long serialVersionUID = 1L;

            @Override
			public Component generateCell(Table source, final Object itemId, Object columnId) {
            	
            		CompositeMessageBridge item = (CompositeMessageBridge) source.getContainerDataSource().getItem(itemId);
            		Map<String, Object> eventParams = new HashMap<String, Object>();
            		eventParams.put("message", item.getSource());
            		eventParams.put("navajo", getNavajo());
            		int index = Integer.parseInt(""+itemId);
					eventParams.put("messageIndex", index);

            		TipiDynamicCell tc;
					try {
						tc = (TipiDynamicCell)TipiInstantiateTipi.instantiateByDefinition(TipiDynamicTable.this, false, ""+itemId, definitionName, null, null);
						tc.setTablePartner(TipiDynamicTable.this);
						try {
	            			tc.performTipiEvent("onRow", eventParams, true);
	            		} catch (TipiBreakException e) {
	            			logger.error("Error: ",e);
	            		} catch (TipiException e) {
	            			logger.error("Error: ",e);
	            		}
					} catch (TipiException e) {
						logger.error("Error: ",e);
						return null;
					}
            		return (Component) tc.getContainer();
            }
            
            
        });
	}
	
	


	@Override
	public void addToContainer(Object c, Object constraints) {
		// ignore
	}


	public void layoutClick(int index) {
        table.select(index);
    }

	@Override
	protected void setComponentValue(String name, Object object) {
		if (name.equals("definitionName")) {
			definitionName = (String) object;
			return;
		}
		if (name.toLowerCase().equals("messagepath")) {
			messagepath = (String) object;
			return;
		}
		if (name.equals("selectedIndex")) {
			 selectedId = object;
			 table.select(selectedId);
		}
		super.setComponentValue(name, object);
	}
	
	@Override
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
		CompositeArrayContainer amb = new CompositeArrayContainer(m) {

			private static final long serialVersionUID = 1243862718076377004L;

			@Override
			public Collection<?> getContainerPropertyIds() {
				ArrayList<String> a = new ArrayList<String>();
				a.add("message");
				return a;
			}
			
		};
		Message exa = amb.getExampleMessage();

		if(exa==null) {
			return null;
		}
		return amb;
	}


}

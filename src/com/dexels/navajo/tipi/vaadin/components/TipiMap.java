package com.dexels.navajo.tipi.vaadin.components;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vaadin.vol.Bounds;
import org.vaadin.vol.GoogleStreetMapLayer;
import org.vaadin.vol.OpenLayersMap;
import org.vaadin.vol.OpenStreetMapLayer;
import org.vaadin.vol.Point;
import org.vaadin.vol.Popup;

import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.tipi.TipiBreakException;
import com.dexels.navajo.tipi.TipiException;
import com.dexels.navajo.tipi.actions.TipiInstantiateTipi;
import com.vaadin.addon.calendar.event.BasicEvent;
import com.vaadin.addon.calendar.event.BasicEventProvider;
import com.vaadin.addon.calendar.event.CalendarEvent;
import com.vaadin.addon.calendar.event.CalendarEventProvider;
import com.vaadin.addon.calendar.ui.Calendar;
import com.vaadin.addon.calendar.ui.CalendarComponentEvents;
import com.vaadin.addon.calendar.ui.CalendarComponentEvents.BackwardEvent;
import com.vaadin.addon.calendar.ui.CalendarComponentEvents.EventClick;
import com.vaadin.addon.calendar.ui.CalendarComponentEvents.EventClickHandler;
import com.vaadin.addon.calendar.ui.CalendarComponentEvents.ForwardEvent;
import com.vaadin.addon.calendar.ui.handler.BasicBackwardHandler;
import com.vaadin.addon.calendar.ui.handler.BasicForwardHandler;

public class TipiMap extends TipiMessagePanel  {

	private static final long serialVersionUID = 1440706945170166712L;
	private OpenLayersMap map;
	private String messagepath;
	private Map<CalendarEvent,TipiCalendarEvent> componentMap = new HashMap<CalendarEvent, TipiCalendarEvent>();
	
	private String definitionName;
	
	private final static Logger logger = LoggerFactory.getLogger(TipiMap.class);

	@Override
	public Object createContainer() {
		map = new OpenLayersMap();
		OpenStreetMapLayer layer = new OpenStreetMapLayer();

		map.addLayer(layer);
//		map.setImmediate(true);
		map.setSizeFull();
		 map.setCenter(22.30083, 60.452541);
		Popup p = new Popup(4.1, 51.3, "Shake it!");
//		map.zoomToExtent(new Bounds(Point.valueOf("4 51"),Point.valueOf("5 52")));
		map.setZoom(8);
		map.addPopup(p);
		return map;
	}

	public void addToContainer(Object c, Object constraints) {
	}
	
	@Override
	public void loadData(Navajo n, String method) throws TipiException, TipiBreakException {
		if(messagepath==null) {
			throw new NullPointerException("message path in table is null, set it before loading!");
		}
		super.loadData(n, method);
//		componentMap.clear();
//		Message m = n.getMessage(messagepath);
//		BasicEventProvider cep = new BasicEventProvider();
//		map.setEventProvider(cep);
//		TipiCalendarEvent tc;
//		int i = 0;
//		for (Message current : m.getAllMessages()) {
//    		Map<String, Object> eventParams = new HashMap<String, Object>();
//    		eventParams.put("message", current);
//    		eventParams.put("navajo", getNavajo());
//			eventParams.put("messageIndex", i);
//			
//			try {
//				tc = (TipiCalendarEvent) TipiInstantiateTipi
//						.instantiateByDefinition(TipiMap.this, false, ""
//								+ i, definitionName, null, null);
//				tc.performTipiEvent("onCalendarEvent", eventParams, true);
//				cep.addEvent(tc.getCalendarEvent());
//				componentMap.put(tc.getCalendarEvent(),tc);
//			} catch (TipiException e) {
//				e.printStackTrace();
//			} catch (TipiBreakException e) {
//				e.printStackTrace();
//			}
//			i++;
//		}
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
		super.setComponentValue(name, object);
	}
	
	public Object getComponentValue(String name) {
		return super.getComponentValue(name);
	}




}

package com.dexels.navajo.tipi.vaadin.components;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vaadin.vol.Bounds;
import org.vaadin.vol.OpenLayersMap;
import org.vaadin.vol.OpenStreetMapLayer;
import org.vaadin.vol.Point;
import org.vaadin.vol.Popup;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.tipi.TipiBreakException;
import com.dexels.navajo.tipi.TipiException;
import com.vaadin.addon.calendar.event.CalendarEvent;
import com.vaadin.ui.ComponentContainer.ComponentAttachEvent;
import com.vaadin.ui.ComponentContainer.ComponentAttachListener;

public class TipiMap extends TipiMessagePanel  {

	private static final long serialVersionUID = 1440706945170166712L;
	private OpenLayersMap map;
	private String messagepath;
	private Map<CalendarEvent,TipiCalendarEvent> componentMap = new HashMap<CalendarEvent, TipiCalendarEvent>();
	
	private String definitionName;
	
	private final static Logger logger = LoggerFactory.getLogger(TipiMap.class);
	private double centerLat = 0;
	private double centerLon = 0;
	
	@Override
	public Object createContainer() {
		map = new OpenLayersMap();
		OpenStreetMapLayer layer = new OpenStreetMapLayer();

		map.addLayer(layer);
//		map.setImmediate(true);
		map.setSizeFull();
		map.addListener(new ComponentAttachListener() {
			
			@Override
			public void componentAttachedToContainer(ComponentAttachEvent event) {
				centerLat = 52.1;
				centerLon = 99.1;
				 map.setCenter(centerLat,centerLon);
					Popup p = new Popup(4.1, 51.3, "Shake it!");
//					map.zoomToExtent(new Bounds(Point.valueOf("4 51"),Point.valueOf("5 52")));
//					 Point observer = new Point(90,53);
//				        Bounds extent = new Bounds(observer, observer);
				      
//					map.zoomToExtent(extent);
				        map.setZoom(5);
				        map.addPopup(p);
				        
			}
		}); 
		return map;
	}

	public void addToContainer(Object c, Object constraints) {
	}
	
	@Override
	public void loadData(Navajo n, String method) throws TipiException, TipiBreakException {
//		if(messagepath==null) {
//			throw new NullPointerException("message path in table is null, set it before loading!");
//		}
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
		System.err.println("Set "+name+" object: "+object);
		if (name.equals("definitionName")) {
			definitionName = (String) object;
			return;
		}
		if (name.toLowerCase().equals("messagepath")) {
			messagepath = (String) object;
			return;
		}
		if (name.toLowerCase().equals("lat")) {
			double lat = (Double)object;
			centerLat = lat;
			map.setCenter(centerLat,centerLon);
			return;
		}
		if (name.toLowerCase().equals("lon")) {
			double lon = (Double)object;
			centerLon = lon;
			map.setCenter(centerLat,centerLon);
			return;
		}
		if (name.toLowerCase().equals("zoom")) {
			int zoom = (Integer)object;
			map.setZoom(zoom);
			return;
		}
		
		super.setComponentValue(name, object);
	}
	
	public Object getComponentValue(String name) {
		return super.getComponentValue(name);
	}




}

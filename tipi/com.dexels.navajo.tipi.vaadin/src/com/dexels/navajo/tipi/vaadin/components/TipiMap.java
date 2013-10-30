package com.dexels.navajo.tipi.vaadin.components;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vaadin.vol.GoogleStreetMapLayer;
import org.vaadin.vol.Marker;
import org.vaadin.vol.MarkerLayer;
import org.vaadin.vol.OpenLayersMap;
import org.vaadin.vol.Popup;
import org.vaadin.vol.Popup.CloseEvent;
import org.vaadin.vol.Popup.CloseListener;
import org.vaadin.vol.Popup.PopupStyle;

import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.tipi.TipiBreakException;
import com.dexels.navajo.tipi.TipiComponent;
import com.dexels.navajo.tipi.TipiComponentMethod;
import com.dexels.navajo.tipi.TipiException;
import com.dexels.navajo.tipi.internal.TipiEvent;
import com.vaadin.event.MouseEvents.ClickEvent;
import com.vaadin.event.MouseEvents.ClickListener;

public class TipiMap extends TipiMessagePanel  {

	private static final long serialVersionUID = 1440706945170166712L;
	private OpenLayersMap map;
	private String messagepath;
//	private Map<CalendarEvent,TipiCalendarEvent> componentMap = new HashMap<CalendarEvent, TipiCalendarEvent>();
	
//	private String definitionName;
	
	private final static Logger logger = LoggerFactory.getLogger(TipiMap.class);
	private double centerLat = 0;
	private double centerLon = 0;
	private MarkerLayer markers;
	private Marker marker;
	private String currentCaption;

	
	
	@Override
	public void addedToParentContainer(TipiComponent parentTipiComponent,
			Object parentContainer, Object container, Object constriants) {
		super.addedToParentContainer(parentTipiComponent, parentContainer, container,
				constriants);
//		centerLat = 52.1;
//		centerLon = 4.1;
//
//		Point e = new Point(centerLon,centerLat);
//		map.zoomToExtent(new Bounds(e));
//		 map.setCenter(centerLat,centerLon);
//		 map.setZoom(10);
	}

	@Override
	public Object createContainer() {
		logger.info("Creating map container");
        map = new OpenLayersMap();
//        OpenStreetMapLayer osm = new OpenStreetMapLayer();
        GoogleStreetMapLayer googleStreets = new GoogleStreetMapLayer();
        markers = new MarkerLayer();
        
//        map.addLayer(osm);
        map.addLayer(googleStreets);
        map.addLayer(markers);
        map.setCenter(22.30083, 60.452541);

		
		
		//		p.setPopupStyle(PopupStyle.DEFAULT);

//		map.add
//		markers.addMarker(m);
//		p.setAnchor(m);
		
//		map.addPopup(p );
		
//		map = new OpenLayersMap();
//		OpenStreetMapLayer layer = new OpenStreetMapLayer();
//		GoogleStreetMapLayer gsml = new GoogleStreetMapLayer();
//		map.addLayer(gsml);
		map.setSizeFull();
        map.setHeight("200px");
//		map.set
		logger.info("Zoom: "+map.getZoom()+" api: "+map.getApiProjection());
		return map;
		
//		 &lt;script src="http://maps.google.com/maps/api/js?v=3.2&amp;sensor=false"&gt;&lt;/script&gt;
	}

	@Override
	public void addToContainer(Object c, Object constraints) {
	}
	
	@Override
	public void loadData(Navajo n, String method) throws TipiException, TipiBreakException {
		if(messagepath==null) {
			throw new NullPointerException("message path in table is null, set it before loading!");
		}
		doPerformOnLoad(method, n, true);
		super.loadData(n, method);
		Message m = n.getMessage(messagepath);
		if(m==null) {
			throw new NullPointerException("message with name: "+ messagepath +" not found in Navajo object");
		}
//		if(true) return;
//		Popup p = new Popup(4.1,51.3,"Aaaap aap aaap");
//		map.addPopup(p);

		
		
		//		map.addComponent(c)
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
//				logger.error("Error: ",e);
//			} catch (TipiBreakException e) {
//				logger.error("Error: ",e);
//			}
//			i++;
//		}
	}

	
	
	@Override
	protected synchronized void performComponentMethod(String name,
			TipiComponentMethod compMeth, TipiEvent event)
			throws TipiBreakException {
		if(name.equals("mark")) {
			if(compMeth.getParameter("lat")==null) {
				// ignore
				return;
			}
			Double lat = (Double) compMeth.getEvaluatedParameterValue("lat", event);
			Double lon = (Double) compMeth.getEvaluatedParameterValue("lon", event);
			Integer zoom = (Integer) compMeth.getEvaluatedParameterValue("zoom", event);
			String caption = (String) compMeth.getEvaluatedParameterValue("description", event);
			if(lat==null || lon==null) {
				return;
			}

			markLocation(lon,lat,zoom,caption);
		}
		if(name.equals("openMarker")) {
			showPopup(currentCaption, marker);
		}
			super.performComponentMethod(name, compMeth, event);
	}

	private void markLocation(Double lon, Double lat, Integer zoom,
			final String caption) {
		centerLon = lon;
		centerLat = lat;
		map.setCenter(centerLon,centerLat);
		map.setZoom(zoom);
		marker = new Marker(lon,lat);
		this.currentCaption = caption;
		markers.addMarker(marker);
		marker.addClickListener(new ClickListener() {
			private static final long serialVersionUID = -6806907718826334805L;

			@Override
			public void click(ClickEvent event) {
				showPopup(caption, marker);
			}
		});

		
	}

	@Override
	protected void setComponentValue(String name, Object object) {
		if (name.toLowerCase().equals("messagepath")) {
			messagepath = (String) object;
			return;
		}
		if (name.toLowerCase().equals("lat")) {
			double lat = (Double)object;
			centerLat = lat;
			map.setCenter(centerLon,centerLat);
			return;
		}
		if (name.toLowerCase().equals("lon")) {
			double lon = (Double)object;
			centerLon = lon;
			map.setCenter(centerLon,centerLat);
			return;
		}
		if (name.toLowerCase().equals("zoom")) {
			int zoom = (Integer)object;
			map.setZoom(zoom);
			return;
		}
		
		super.setComponentValue(name, object);
	}
	
	@Override
	public Object getComponentValue(String name) {
		return super.getComponentValue(name);
	}

	private void showPopup(final String caption, final Marker m) {
		final Popup popup = new Popup(m.getLon(), m.getLat(),
				caption);
		popup.setAnchor(m);
		popup.setPopupStyle(PopupStyle.FRAMED_CLOUD);
		popup.addListener(new CloseListener() {
			@Override
			public void onClose(CloseEvent event) {
				map.removeComponent(popup);
			}
		});
		map.addPopup(popup);
	}




}

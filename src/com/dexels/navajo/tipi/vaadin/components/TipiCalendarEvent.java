package com.dexels.navajo.tipi.vaadin.components;

import java.util.Date;

import com.dexels.navajo.tipi.TipiBreakException;
import com.dexels.navajo.tipi.TipiException;
import com.dexels.navajo.tipi.components.core.TipiComponentImpl;
import com.vaadin.addon.calendar.event.BasicEvent;

public class TipiCalendarEvent extends TipiComponentImpl {

	private static final long serialVersionUID = 1561760767992331660L;
	
	private BasicEvent event;
	private TipiCalendar myCalendar = null;

	private Integer index;
	
	@Override
	public Object createContainer() {
		event = new BasicEvent();
		return event;
	}

	public Object getComponentValue(final String name) {
//		super.setComponentValue(name, object);
		if (name.equals("startTime")) {
			return event.getStart();
		}
		if (name.equals("endTime")) {
			return event.getEnd();
		}
		if (name.equals("description")) {
			return event.getDescription();
		}
		if (name.equals("caption")) {
			return event.getCaption();
		}
		if(name.equals("style")) {
			return event.getStyleName();
		}
		if (name.equals("allDay")) {
			return event.isAllDay();
		}
		if (name.equals("index")) {
			return index;
		}
		return super.getComponentValue(name);
		
	}
	public void setComponentValue(final String name, final Object object) {
		super.setComponentValue(name, object);
		if (name.equals("startTime")) {
			event.setStart((Date) object);
		}
		if (name.equals("endTime")) {
			event.setEnd((Date) object);
		}
		if (name.equals("description")) {
			event.setDescription((String)object);
		}
		if (name.equals("caption")) {
			event.setDescription((String)object);
		}
		if(name.equals("style")) {
			event.setStyleName(""+object);
		}
		if (name.equals("allDay")) {
			event.setAllDay((Boolean)object);
		}
		if (name.equals("index")) {
			this.index = ((Integer)object);
		}
		
	}

	

	public BasicEvent getCalendarEvent() {
		return event;
	}


	public void setCalendar(TipiCalendar tipiCalendar) {
		myCalendar = tipiCalendar;
	}


	public void onClick() {
		try {
			performTipiEvent("onActionPerformed", null, true);
		} catch (TipiBreakException e) {
			e.printStackTrace();
		} catch (TipiException e) {
			e.printStackTrace();
		}		
	}

}

package com.dexels.navajo.tipi.vaadin.components;

import java.util.Date;

import com.dexels.navajo.tipi.components.core.TipiComponentImpl;
import com.vaadin.addon.calendar.event.BasicEvent;

public class TipiCalendarEvent extends TipiComponentImpl {

	private static final long serialVersionUID = 1561760767992331660L;
	
	private BasicEvent event;
	private TipiCalendar myCalendar = null;
	@Override
	public Object createContainer() {
		event = new BasicEvent();
		return event;
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
		
	}


	public BasicEvent getCalendarEvent() {
		return event;
	}


	public void setCalendar(TipiCalendar tipiCalendar) {
		myCalendar = tipiCalendar;
	}

}

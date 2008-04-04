package com.dexels.navajo.adapter;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.StringTokenizer;

import net.fortuna.ical4j.data.CalendarOutputter;
import net.fortuna.ical4j.model.*;
import net.fortuna.ical4j.model.component.VAlarm;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.model.property.*;

import com.dexels.navajo.mapping.Mappable;
import com.dexels.navajo.server.Parameters;
import com.dexels.navajo.adapter.icalmap.ICalEvent;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.types.Binary;
import com.dexels.navajo.adapter.icalmap.RandomString;
import com.dexels.navajo.server.Access;
import com.dexels.navajo.server.NavajoConfig;
import com.dexels.navajo.mapping.MappableException;
import com.dexels.navajo.server.UserException;

/**
 * <p>
 * Title: ICalMap
 * </p>
 * <p>
 * Description: Map to create iCal objects
 * </p>
 * <p>
 * Copyright: Copyright (c) 2006
 * </p>
 * <p>
 * Company: Dexels BV
 * </p>
 * 
 * @author Matthijs Philip, Arjen Schoneveld
 * @version $Id$
 */

public class ICalMap implements Mappable {

	public ICalEvent [] events;
	public ICalEvent event;
	
	public Binary iCal;
	
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd'T'HHmmss");

	public ICalMap() {
	}

	public void kill() {
	}

	public void setEvent(ICalEvent e) {
		events = new ICalEvent[1];
		events[0] = e;
	}
	
	public void setEvents(ICalEvent [] e) {
		this.events = e;
	}
	
	private String getTokenValue(String token, String sep) {
		
		StringTokenizer st = new StringTokenizer(token, sep);
		if ( st.hasMoreTokens() ) {
			st.nextToken();
			if ( st.hasMoreTokens() ) {
				String value = st.nextToken();
				return value;
			}
		}
		return "";
	}
	
	public com.dexels.navajo.adapter.icalmap.Attendee parseAttendee(String line) {
		com.dexels.navajo.adapter.icalmap.Attendee a = new com.dexels.navajo.adapter.icalmap.Attendee();
		
		// CN=aap:mailto:noot
		String cn = null;
		String email = null;
		int indx;
		
		if ( line.indexOf("CN=") != -1) {
			StringTokenizer tokens = new StringTokenizer(line, "=");
			tokens.nextToken();
			cn = tokens.nextToken();
			if ( ( indx = cn.indexOf("mailto:")) != -1) {
				cn = cn.substring(0, indx-1);
			}
		}
		
		if ( ( indx = line.indexOf("mailto:") ) != -1 ) {
			email = line.substring(indx+7);
		}
		
		a.setName(cn);
		a.setEmail(email);
		
		return a;
	}
	
	public ICalEvent parseEvent(BufferedReader br) {

		ICalEvent event = new ICalEvent();
		HashSet<com.dexels.navajo.adapter.icalmap.Attendee> attendees = new HashSet<com.dexels.navajo.adapter.icalmap.Attendee>();
		String line = null;
		try {
			while ( ( line = br.readLine() ) != null ) {
				if ( line.indexOf("END:VEVENT") != -1 ) {
					//return event;
				} else if ( line.indexOf("SUMMARY:") != -1) {
					event.setSummary(getTokenValue(line, ":"));
				} else if ( line.indexOf("DESCRIPTION:") != -1) {
					event.setDescription(getTokenValue(line, ":"));
				} else if ( line.indexOf("LOCATION:") != -1) {
					event.setLocation(getTokenValue(line, ":"));
				} else if ( line.indexOf("DTEND:") != -1 ) {
					String value = getTokenValue(line, ":");
					event.setEndDate(sdf.parse(value));
				} else if ( line.indexOf("DTSTART") != -1 ) {
					String value = getTokenValue( getTokenValue(line, ";"), ":");
					event.setStartDate(sdf.parse(value));
				} else if ( line.indexOf("UID:") != -1) {
					event.setUid(getTokenValue(line, ":"));
				} else if ( line.indexOf("ATTENDEE") != -1) {
					String value = getTokenValue(line, ";");
					attendees.add(parseAttendee(value));
				}
			}
		} catch (Exception ioe) {
			ioe.printStackTrace(System.err);
		}

		com.dexels.navajo.adapter.icalmap.Attendee [] aas = new com.dexels.navajo.adapter.icalmap.Attendee[attendees.size()];
		aas = (com.dexels.navajo.adapter.icalmap.Attendee []) attendees.toArray(aas);
		event.setAttendees(aas);
		
		return event;
	}
	
	/**
	 * Parse an Ical document.
	 * 
	 * BEGIN:VCALENDAR
PRODID:-//Dexels//Navajo Integrator Adapters iCal 1.0//EN
VERSION:2.0
CALSCALE:GREGORIAN
METHOD:PUBLISH
BEGIN:VEVENT
DTSTAMP:20071211T142053Z
DTSTART;TZID=Europe/Amsterdam:20071208T083000
DTEND:20071208T101500
SUMMARY:NAC Breda [NAC Breda] - Ajax [Ajax]
UID:72A34197-A43F-BF20-59EE-23B7DFB2BFBC
SEQUENCE:0
TRANSP:OPAQUE
CLASS:PUBLIC
DESCRIPTION:
LOCATION:Rat Verlegh Stadion BREDA
END:VEVENT
BEGIN:VEVENT

	 * @param b
	 */
	public void setICal(Binary b) {
		
		BufferedReader br = new BufferedReader( new StringReader(new String(b.getData())));
		HashSet<ICalEvent> eventSet = new HashSet<ICalEvent>();
		
		String line = null;
		
		try {
		while ( ( line = br.readLine() ) != null ) {
			if ( line.indexOf("BEGIN:VEVENT") != -1 ) {
				eventSet.add(parseEvent(br));
			}
		}
		} catch (Exception ioe) {
			ioe.printStackTrace(System.err);
		}
		events = new ICalEvent[eventSet.size()];
		events = (ICalEvent []) eventSet.toArray(events);
		
	}
	
	/**
	 * Generate an Ical document.
	 * 
	 * @return
	 */
	public Binary getICal() {

		Calendar calendar = new Calendar();
		calendar.getProperties().add(new ProdId("-//Dexels//Navajo Integrator Adapters iCal 1.0//EN"));
		calendar.getProperties().add(Version.VERSION_2_0);
		calendar.getProperties().add(CalScale.GREGORIAN);
		calendar.getProperties().add(Method.PUBLISH);
		
		java.util.Calendar c = java.util.Calendar.getInstance();
		java.util.TimeZone tz = c.getTimeZone();
		TimeZoneRegistry registry = TimeZoneRegistryFactory.getInstance().createRegistry();
		
		for (int i = 0; i < events.length; i++) {
			
			ICalEvent icalEvent = events[i];
			
			net.fortuna.ical4j.model.DateTime eventStartDate = 
				new net.fortuna.ical4j.model.DateTime(icalEvent.getStartDate());
			
			eventStartDate.setTimeZone(registry.getTimeZone(tz.getID()));
			
			VEvent myEvent = new VEvent(eventStartDate, new net.fortuna.ical4j.model.DateTime(icalEvent.getEndDate()), icalEvent.getSummary());
			
			// Add some default properties
			myEvent.getProperties().add(new Uid(getUid()));
			myEvent.getProperties().add(new Sequence(0));
			myEvent.getProperties().add(new Transp("OPAQUE"));
			myEvent.getProperties().add(new Clazz("PUBLIC"));
			myEvent.getProperties().add(new Description(icalEvent.getDescription()));
			
			if (icalEvent.getFromEmail() != null) {
				URI uri = null;
				try {
					uri = new URI("mailto:"+icalEvent.getFromEmail());
					if (icalEvent.getFromName() != null) {
						ParameterList pl = new ParameterList();
						Parameter p = ParameterFactoryImpl.getInstance().createParameter("CN", icalEvent.getFromName());
						pl.add(p);
						myEvent.getProperties().add(new Organizer(pl, uri));
					} else {
						myEvent.getProperties().add(new Organizer(uri));
					}
				} catch (Exception e) {
					
				}
			}
			
			if (icalEvent.getLocation() != null) {
				myEvent.getProperties().add(new Location(icalEvent.getLocation()));
			}
		
			if ( icalEvent.getAlarmDate() != null || icalEvent.getAlarmMinutesObject() != null ) {
				SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy HH:mm");
				VAlarm va = null;
				if ( icalEvent.getAlarmDate() != null ) {
					net.fortuna.ical4j.model.DateTime eventAlarm = new DateTime(icalEvent.getAlarmDate());
					eventAlarm.setTimeZone(registry.getTimeZone(tz.getID()));
					va = new VAlarm(eventAlarm);
				} else {
					int duration = icalEvent.getAlarmMinutesObject().intValue();
					Dur d = new Dur(0,0,Math.abs(duration),0);
					if ( duration < 0 ) {
						d = new Dur("-PT"+Math.abs(duration)+"M");
					}	
					va = new VAlarm(d);
				}
				va.getProperties().add(new Action(icalEvent.getAlarmAction()));
				String description = (icalEvent.getAlarmDescription() != null ? icalEvent.getAlarmDescription() :
					icalEvent.getSummary() + ": " + sdf.format(icalEvent.getStartDate()));
				
				va.getProperties().add(new Description(description));
				myEvent.getAlarms().add(va);
			}
			
			// Add attendees
			if ( icalEvent.getAttendees() != null ) {
				for ( int a = 0; a < icalEvent.getAttendees().length; a++ ) {
					com.dexels.navajo.adapter.icalmap.Attendee atnd = icalEvent.getAttendees()[a];

					try {
						URI uri = null;
						String name =  null;
						if ( atnd.getEmail() != null ) {
							uri = new URI("mailto:" + atnd.getEmail());
						}
						if ( atnd.getName() != null ) {
							name = atnd.getName();
						}
						if ( name != null && uri != null) {
							ParameterList pl = new ParameterList();
							Parameter p = ParameterFactoryImpl.getInstance().createParameter("CN", name);
							pl.add(p);
							myEvent.getProperties().add(new Attendee(pl, uri));
						} else if ( uri != null ) {
							myEvent.getProperties().add(new Attendee(uri));
						} else if ( name != null ) {
							myEvent.getProperties().add(new Attendee(name));
						}
					} catch (Exception e) {

					}
				}
			}
			calendar.getComponents().add(myEvent);
		}
		
		StringWriter bout = new StringWriter();
		CalendarOutputter outputter = new CalendarOutputter();
		try {
			outputter.output(calendar, bout);
			bout.close();
			//iCal = new Binary( new StringReader( bout.toString() ) );
			iCal = new Binary(bout.toString().getBytes());
			return iCal;
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ValidationException e) {
			e.printStackTrace();
		}

		return null;
	}

	public void load(Parameters parms, Navajo inMessage, Access access,
			NavajoConfig config) throws MappableException, UserException {

	}

	public void store() throws MappableException, UserException {

	}

	private final static String getUid() {
		// 8-4-4-4-12
		String charset = "ABCDEF0123456789";
		String uid = "";
		RandomString rs = new RandomString();
		
		try {
			rs.reset();
			rs.insertOperand(new Integer(8));
			rs.insertOperand(charset);
			uid += rs.evaluate() + "-";
			
			rs.reset();
			rs.insertOperand(new Integer(4));
			rs.insertOperand(charset);
			uid += rs.evaluate() + "-";
			
			rs.reset();
			rs.insertOperand(new Integer(4));
			rs.insertOperand(charset);
			uid += rs.evaluate() + "-";
			
			rs.reset();
			rs.insertOperand(new Integer(4));
			rs.insertOperand(charset);
			uid += rs.evaluate() + "-";
			
			rs.reset();
			rs.insertOperand(new Integer(12));
			rs.insertOperand(charset);
			uid += rs.evaluate();
			
		} catch (Exception e) {
			
		}
		return uid;
	}
	
	public static void main(String[] args) throws Exception {
		ICalMap icm = new ICalMap();
		ICalEvent ie = new ICalEvent();
		
		com.dexels.navajo.adapter.icalmap.Attendee at1 = new com.dexels.navajo.adapter.icalmap.Attendee();
		at1.setEmail("mphilip@dexels.com");
		at1.setName("Matthijs Philip");
		com.dexels.navajo.adapter.icalmap.Attendee at2 = new com.dexels.navajo.adapter.icalmap.Attendee();
		at2.setEmail("arjen@dexels.com");
		at2.setName("Arjen Schoneveld");
		
		
		ie.setAttendees(new com.dexels.navajo.adapter.icalmap.Attendee[]{at1,at2});
		ie.setDescription("Description\nDescription");
		ie.setLocation("Location");
		ie.setSummary("Summary");
		ie.setStartDate(new Date());
		ie.setEndDate(new Date());
		
		icm.setEvents(new ICalEvent[]{ie});
		
		System.err.println(new String( icm.getICal().getData() ));
		
		// Parse ics file.
		Binary b = new Binary(new File("/home/arjen/aap.ics"));
		icm.setICal(b);
		ICalEvent [] events = icm.getEvents();
		for (int i = 0; i < events.length; i++) {
			System.err.println(events[i]);
			// Get attendees
			com.dexels.navajo.adapter.icalmap.Attendee [] aas = events[i].getAttendees();
			for (int j  = 0; j < aas.length; j++) {
				System.err.println(aas[j]);
			}
		}
		
		
	}

	public ICalEvent[] getEvents() {
		return events;
	}
}

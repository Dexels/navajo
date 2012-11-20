package com.dexels.navajo.tipi.vaadin.components;

import java.text.DateFormatSymbols;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.tipi.TipiBreakException;
import com.dexels.navajo.tipi.TipiException;
import com.dexels.navajo.tipi.actions.TipiInstantiateTipi;
import com.vaadin.addon.calendar.event.BasicEventProvider;
import com.vaadin.addon.calendar.event.CalendarEvent;
import com.vaadin.addon.calendar.ui.Calendar;
import com.vaadin.addon.calendar.ui.Calendar.TimeFormat;
import com.vaadin.addon.calendar.ui.CalendarComponentEvents.EventClick;
import com.vaadin.addon.calendar.ui.CalendarComponentEvents.EventClickHandler;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

public class TipiCalendar extends TipiMessagePanel  {

	private static final long serialVersionUID = 1440706945170166712L;
	private Calendar calendarComponent;
	private String messagepath;
	private Map<CalendarEvent,TipiCalendarEvent> componentMap = new HashMap<CalendarEvent, TipiCalendarEvent>();
	

    GregorianCalendar calendar = new GregorianCalendar();
    private String definitionName;
	
	private final static Logger logger = LoggerFactory.getLogger(TipiCalendar.class);
	private Date currentMonthsFirstDate;

	
	
	@Override
	public Object getActualComponent() {
		return calendarComponent;
	}

	@Override
	public Object createContainer() {
		
		
		final Label label = new Label();

        calendarComponent = new Calendar();
        calendar = new GregorianCalendar(getContext().getApplicationInstance().getLocale());
        
        calendarComponent.setTimeFormat(TimeFormat.Format24H);
//        calendar.setFirstDayOfWeek(GregorianCalendar.SATURDAY);
        Date today = new Date();
        calendar.setTime(today);
        calendar.get(GregorianCalendar.MONTH);

        DateFormatSymbols s = new DateFormatSymbols(getContext().getApplicationInstance().getLocale());
        String month = s.getShortMonths()[calendar.get(GregorianCalendar.MONTH)];
        label.setValue(month + " " + calendar.get(GregorianCalendar.YEAR));
        int rollAmount = calendar.get(GregorianCalendar.DAY_OF_MONTH) - 1;
        calendar.add(GregorianCalendar.DAY_OF_MONTH, -rollAmount);
        currentMonthsFirstDate = calendar.getTime();
        calendarComponent.setStartDate(currentMonthsFirstDate);
        calendar.add(GregorianCalendar.MONTH, 1);
        calendar.add(GregorianCalendar.DATE, -1);
        calendarComponent.setEndDate(calendar.getTime());
        VerticalLayout vl = new VerticalLayout();
        vl.setSizeFull();
        vl.setMargin(false);
        HorizontalLayout hl = new HorizontalLayout();
        hl.setMargin(true);
        vl.addComponent(hl);
        Button next = new Button("next", new Button.ClickListener() {
            private static final long serialVersionUID = 1L;

            public void buttonClick(Button.ClickEvent event) {
                calendar.setTime(currentMonthsFirstDate);
                calendar.add(GregorianCalendar.MONTH, 1);
                currentMonthsFirstDate = calendar.getTime();
                calendarComponent.setStartDate(currentMonthsFirstDate);
                DateFormatSymbols s = new DateFormatSymbols(getContext().getApplicationInstance().getLocale());
                String month = s.getShortMonths()[calendar
                        .get(GregorianCalendar.MONTH)];
                label.setValue(month + " "
                        + calendar.get(GregorianCalendar.YEAR));
                calendar.add(GregorianCalendar.MONTH, 1);
                calendar.add(GregorianCalendar.DATE, -1);
                calendarComponent.setEndDate(calendar.getTime());
            }

        });
        Button prev = new Button("prev", new Button.ClickListener() {
            private static final long serialVersionUID = 1L;

			@Override
           public void buttonClick(com.vaadin.ui.Button.ClickEvent event) {
                calendar.setTime(currentMonthsFirstDate);
                calendar.add(GregorianCalendar.MONTH, -1);
                currentMonthsFirstDate = calendar.getTime();
                calendarComponent.setStartDate(currentMonthsFirstDate);
                DateFormatSymbols s = new DateFormatSymbols(getContext().getApplicationInstance().getLocale());
                String month = s.getShortMonths()[calendar
                        .get(GregorianCalendar.MONTH)];
                label.setValue(month + " "
                        + calendar.get(GregorianCalendar.YEAR));
                calendar.add(GregorianCalendar.MONTH, 1);
                calendar.add(GregorianCalendar.DATE, -1);
                calendarComponent.setEndDate(calendar.getTime());
            }


        });
        hl.addComponent(prev);
        hl.addComponent(label);
        hl.addComponent(next);


        vl.addComponent(calendarComponent);
        vl.setExpandRatio(calendarComponent, 1);
		calendarComponent.setHandler(new EventClickHandler() {
			
			private static final long serialVersionUID = -5074871951736420122L;

			@Override
			public void eventClick(EventClick event) {
				CalendarEvent ce = event.getCalendarEvent();
				TipiCalendarEvent te = componentMap.get(ce);
				if(te==null) {
					logger.error("Unbound event found? ");
				} else {
					te.onClick();

				}
			}
		});
        
		return vl;
	}

	public void addToContainer(Object c, Object constraints) {
	}
	
	@Override
	public void loadData(Navajo n, String method) throws TipiException, TipiBreakException {
		if(messagepath==null) {
			throw new NullPointerException("message path in table is null, set it before loading!");
		}
		super.loadData(n, method);
		componentMap.clear();
		Message m = n.getMessage(messagepath);
		BasicEventProvider cep = new BasicEventProvider();
		calendarComponent.setEventProvider(cep);
		TipiCalendarEvent tc;
		int i = 0;
		for (Message current : m.getAllMessages()) {
    		Map<String, Object> eventParams = new HashMap<String, Object>();
    		eventParams.put("message", current);
    		eventParams.put("navajo", getNavajo());
			eventParams.put("messageIndex", i);
			
			try {
				tc = (TipiCalendarEvent) TipiInstantiateTipi
						.instantiateByDefinition(TipiCalendar.this, false, ""
								+ i, definitionName, null, null);
				try {
				tc.performTipiEvent("onCalendarEvent", eventParams, true);
				} catch (TipiBreakException e) {
				}
				cep.addEvent(tc.getCalendarEvent());
//				tc.setCalendar(this);
				componentMap.put(tc.getCalendarEvent(),tc);
			} catch (TipiException e) {
				logger.error("Error: ",e);
			}
			i++;
		}
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

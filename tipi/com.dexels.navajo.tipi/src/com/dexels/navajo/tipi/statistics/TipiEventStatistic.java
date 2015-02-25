package com.dexels.navajo.tipi.statistics;

import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.document.Property;

public class TipiEventStatistic implements TipiStatistic {

    private String component;
    private String parentComponent;
    private String eventname;
    private Long duration;

    public TipiEventStatistic(String component, String parentComponent, String eventname, Long duration) {
        super();
        this.component = component;
        this.parentComponent = parentComponent;
        this.eventname = eventname;
        this.duration = duration;
    }

    @Override
    public void addStatisticsTo(Navajo n) {
        NavajoFactory navajoFactory = NavajoFactory.getInstance();
        Message eventarray = null;
        if (n.getMessage("event") == null) {
            eventarray = navajoFactory.createMessage(n, "event", Message.MSG_TYPE_ARRAY);
            n.addMessage(eventarray);
        } else {
            eventarray = n.getMessage("event");
        }

        Message event = navajoFactory.createMessage(n, "event", Message.MSG_TYPE_ARRAY_ELEMENT);

        event.addProperty(navajoFactory.createProperty(n, "component", Property.STRING_PROPERTY, component, 0, "", ""));
        event.addProperty(navajoFactory.createProperty(n, "parentcomponent", Property.STRING_PROPERTY, parentComponent, 0, "", ""));

        event.addProperty(navajoFactory.createProperty(n, "event", Property.STRING_PROPERTY, eventname, 0, "", ""));
        event.addProperty(navajoFactory.createProperty(n, "duration", Property.INTEGER_PROPERTY, duration.toString(),
                0, "", ""));
        eventarray.addMessage(event);

    }

}

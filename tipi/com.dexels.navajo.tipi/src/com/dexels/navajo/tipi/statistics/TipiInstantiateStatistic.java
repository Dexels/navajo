package com.dexels.navajo.tipi.statistics;

import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.document.Property;

public class TipiInstantiateStatistic  implements TipiStatistic{
    private String component;
    private Long duration;
    private Boolean unhide;
    
    
    
    public TipiInstantiateStatistic(String component, Long duration, Boolean unhide) {
        super();
        this.component = component;
        this.duration = duration;
        this.unhide = unhide;
    }



    @Override
    public void addStatisticsTo(Navajo n) {
        NavajoFactory navajoFactory = NavajoFactory.getInstance();
        Message instantiateArray = null;
        if (n.getMessage("instantiate") == null) {
            instantiateArray = navajoFactory.createMessage(n, "instantiate", Message.MSG_TYPE_ARRAY);
            n.addMessage(instantiateArray);
        } else {
            instantiateArray = n.getMessage("instantiate");
        }

        Message instantiate = navajoFactory.createMessage(n, "instantiate", Message.MSG_TYPE_ARRAY_ELEMENT);

        instantiate.addProperty(navajoFactory.createProperty(n, "component", Property.STRING_PROPERTY, component, 0, "", ""));
        instantiate.addProperty(navajoFactory.createProperty(n, "duration", Property.INTEGER_PROPERTY, duration.toString(),0, "", ""));
        instantiate.addProperty(navajoFactory.createProperty(n, "unhide", Property.BOOLEAN_PROPERTY, unhide.toString(),0, "", ""));

        instantiateArray.addMessage(instantiate);

    }

}

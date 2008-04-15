package com.dexels.navajo.tipi.components.echoimpl;

import nextapp.echo2.app.Component;
import nextapp.echo2.extras.app.*;
import nextapp.echo2.extras.app.layout.*;

public class TipiAccordionPane extends TipiEchoDataComponentImpl {
    private AccordionPane myAccordionPane;

    public Object createContainer() {
        myAccordionPane = new AccordionPane();
        return myAccordionPane;
    }

    public void addToContainer(Object c, Object constraints) {
        Component comp = (Component) c;
        myAccordionPane.add(comp);
        String s = (String)constraints;
        AccordionPaneLayoutData accordionPaneLayoutData = new AccordionPaneLayoutData();
        accordionPaneLayoutData.setTitle(s);
        comp.setLayoutData(accordionPaneLayoutData);
    }

}

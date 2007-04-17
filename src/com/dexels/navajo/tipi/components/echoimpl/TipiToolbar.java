package com.dexels.navajo.tipi.components.echoimpl;

import com.dexels.navajo.tipi.components.echoimpl.impl.ToolbarImpl;
import com.dexels.navajo.tipi.components.echoimpl.impl.layout.EchoLayoutImpl;

import nextapp.echo2.app.Color;
import nextapp.echo2.app.Component;
import nextapp.echo2.app.Row;
import echopointng.ContainerEx;

public class TipiToolbar extends TipiEchoDataComponentImpl {
    private ToolbarImpl myRow;

    public Object createContainer() {
        // ContainerEx myContainer = new ContainerEx();
        myRow = new ToolbarImpl();
        myRow.setBackground(new Color(230,230,230));
        return myRow;
    }

    public void addToContainer(Object c, Object constraints) {
        Component comp = (Component) c;
        myRow.add(comp);
        // System.err.println(">>>>>>>>>|||||||||||||||||| Added to
        // container...");
    }

    // public Object getActualComponent() {
    // return myColumn;
    // }
    public void setContainerLayout(Object layout) {
    	// ignore
    }

}

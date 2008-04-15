package com.dexels.navajo.tipi.components.echoimpl;

import com.dexels.navajo.tipi.components.echoimpl.impl.ToolbarImpl;
import com.dexels.navajo.tipi.components.echoimpl.impl.layout.EchoLayoutImpl;

import nextapp.echo2.app.*;
import nextapp.echo2.app.layout.*;
import echopointng.ContainerEx;

public class TipiToolbar extends TipiEchoDataComponentImpl {
    private ToolbarImpl myRow;

    public Object createContainer() {
        // ContainerEx myContainer = new ContainerEx();
        myRow = new ToolbarImpl();
        return myRow;
    }

    public void addToContainer(Object c, Object constraints) {
        Component comp = (Component) c;
        myRow.add(comp);
        RowLayoutData rld = new RowLayoutData();
        rld.setInsets(new Insets(3));
        comp.setLayoutData(rld);
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

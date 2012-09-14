package com.dexels.navajo.tipi.components.echoimpl;

import nextapp.echo2.app.Component;
import nextapp.echo2.app.Insets;
import nextapp.echo2.app.layout.RowLayoutData;

import com.dexels.navajo.tipi.components.echoimpl.impl.ToolbarImpl;

public class TipiToolbar extends TipiEchoDataComponentImpl {
	private static final long serialVersionUID = 3927529949578898047L;
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
        // logger.info(">>>>>>>>>|||||||||||||||||| Added to
        // container...");
    }

    // public Object getActualComponent() {
    // return myColumn;
    // }
    public void setContainerLayout(Object layout) {
    	// ignore
    }

}

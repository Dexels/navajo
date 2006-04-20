package com.dexels.navajo.tipi.components.echoimpl;

import echopointng.ContainerEx;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2004
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author Frank Lyaruu
 * @version 1.0
 */

public class TipiPanel extends TipiEchoDataComponentImpl {

    public TipiPanel() {
    }

    public Object createContainer() {
        ContainerEx p = new ContainerEx();
        return p;
    }

    // public void addToContainer(Object o, Object contraints){
    //
    // }
    //
    // public void setContainerLayout(Object l){
    //
    // }

    public void setComponentValue(final String name, final Object object) {

        // if ("w".equals(name)) {
        // ContentPane cont = (ContentPane) getContainer();
        // cont.setWidth( ( (Integer) object).intValue());
        // }
        // if ("h".equals(name)) {
        // TipiEchoPanel cont = (TipiEchoPanel) getContainer();
        // cont.setHeight( ( (Integer) object).intValue());
        // }
        super.setComponentValue(name, object);
    }

}

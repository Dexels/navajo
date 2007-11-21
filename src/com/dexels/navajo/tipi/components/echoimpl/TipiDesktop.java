package com.dexels.navajo.tipi.components.echoimpl;

import com.dexels.navajo.echoclient.components.*;

import nextapp.echo2.app.*;
import echopointng.ContainerEx;
import echopointng.able.Positionable;

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

public class TipiDesktop extends TipiEchoDataComponentImpl {

    private ContentPane myContainer;

	public TipiDesktop() {
    }

    public Object createContainer() {
        myContainer = new ContentPane();
        Style ss = Styles.DEFAULT_STYLE_SHEET.getStyle(ContentPane.class, "Desktop");
        myContainer.setStyle(ss);
        myContainer.setBackground(new Color(180,180,240));
//		myContainer.setPosition(Positionable.STATIC);
        return myContainer;
    }

    // public void addToContainer(Object o, Object contraints){
    //
    // }
    //
    // public void setContainerLayout(Object l){
    //
    // }

    public void setComponentValue(final String name, final Object object) {
        if ("background".equals(name)) {
            if (object instanceof Color) {
                myContainer.setBackground((Color) object);
                            }
        }

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

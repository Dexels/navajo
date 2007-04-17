package com.dexels.navajo.tipi.components.echoimpl;

import nextapp.echo2.app.Color;
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

public class TipiPanel extends TipiEchoDataComponentImpl {

    private ContainerEx myContainer;

	public TipiPanel() {
    }

    public Object createContainer() {
        myContainer = new ContainerEx();
		myContainer.setPosition(Positionable.STATIC);
        return myContainer;
    }

    // public void addToContainer(Object o, Object contraints){
    //
    // }
    //
//     public void setContainerLayout(Object l){
//    
//     }

    public void setComponentValue(final String name, final Object object) {
        if ("background".equals(name)) {
            if (object instanceof Color) {
                myContainer.setBackground((Color) object);
                            }
        }

        super.setComponentValue(name, object);
    }

}

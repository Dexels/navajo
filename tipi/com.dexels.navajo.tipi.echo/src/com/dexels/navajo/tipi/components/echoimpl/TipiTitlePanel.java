package com.dexels.navajo.tipi.components.echoimpl;

import echopointng.GroupBox;

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

public class TipiTitlePanel extends TipiPanel {

	private static final long serialVersionUID = 7825287651695595489L;
	private GroupBox myContainer;

	public TipiTitlePanel() {
    }

    public Object createContainer() {
        myContainer = new GroupBox();
        return myContainer;
    }

 
    public void setComponentValue(final String name, final Object object) {
        if ("title".equals(name)) {
            myContainer.setTitle(""+object);
            return;
        }
        super.setComponentValue(name, object);
    }

}

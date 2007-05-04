package com.dexels.navajo.tipi.components.echoimpl;

import nextapp.echo2.app.Border;
import nextapp.echo2.app.Color;
import nextapp.echo2.app.Component;
import nextapp.echo2.app.ContentPane;
import nextapp.echo2.app.Extent;
import nextapp.echo2.app.Insets;
import nextapp.echo2.app.Label;
import echopointng.ContainerEx;
import echopointng.GroupBox;
import echopointng.LabelEx;
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

public class TipiTitlePanel extends TipiPanel {

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

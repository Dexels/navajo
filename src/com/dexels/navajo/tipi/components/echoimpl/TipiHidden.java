package com.dexels.navajo.tipi.components.echoimpl;

import java.net.*;

import echopointng.*;
import echopointng.image.*;
import nextapp.echo2.app.Label;
import nextapp.echo2.app.ResourceImageReference;

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

public class TipiHidden extends TipiEchoComponentImpl {
    public TipiHidden() {
    }

    public Object createContainer() {
        Label b = new LabelEx();
        b.setVisible(false);
        return b;
    }

    protected void setComponentValue(String name, Object object) {
        Label b = (Label) getContainer();
      
        super.setComponentValue(name, object);
    }

}

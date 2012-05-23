package com.dexels.navajo.tipi.components.echoimpl;

import nextapp.echo2.app.Label;
import echopointng.LabelEx;

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

	private static final long serialVersionUID = 5400920173404016751L;

	public TipiHidden() {
    }

    public Object createContainer() {
        Label b = new LabelEx();
//        b.setStyleName("Default");
        
        b.setVisible(false);
        return b;
    }

    protected void setComponentValue(String name, Object object) {
        super.setComponentValue(name, object);
    }

}

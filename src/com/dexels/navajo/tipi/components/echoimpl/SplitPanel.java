package com.dexels.navajo.tipi.components.echoimpl;

import nextapp.echo2.app.Extent;
import nextapp.echo2.app.SplitPane;

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

public class SplitPanel extends TipiEchoDataComponentImpl {

    public SplitPanel() {
    }

    public Object createContainer() {
        SplitPane splitPane = new SplitPane();
        return splitPane;
    }

    public void addToContainer(Object o, Object constraints) {
        super.addToContainer(o, null);
    }

    public void setComponentValue(final String name, final Object object) {
        SplitPane p = (SplitPane) getContainer();
        if ("orientation".equals(name)) {
            if ("horizontal".equals(object)) {
                p.setOrientation(SplitPane.ORIENTATION_HORIZONTAL);
            }
            if ("vertical".equals(object)) {
                p.setOrientation(SplitPane.ORIENTATION_VERTICAL);
            }
        }

        if ("separator".equals(name)) {
            p.setSeparatorPosition((Extent) object);
        }
        if ("resizable".equals(name)) {
            p.setResizable(((Boolean) object).booleanValue());
        }
        if ("separatorsize".equals(name)) {
            p.setSeparatorWidth((Extent) object);
        }
        super.setComponentValue(name, object);
    }

}

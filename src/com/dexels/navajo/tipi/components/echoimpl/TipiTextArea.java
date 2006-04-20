package com.dexels.navajo.tipi.components.echoimpl;

import nextapp.echo2.app.TextArea;

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
 * @author not attributable
 * @version 1.0
 */

public class TipiTextArea extends TipiEchoComponentImpl {
    public TipiTextArea() {
    }

    public Object createContainer() {
        TextArea rta = new TextArea();
        // rta.setWidth(new Extent(100,100) TextArea.PERCENT_UNITS);

        // rta.setColumns(100);
        // rta.setRows(10);

        return rta;
    }

    public Object getComponentValue(String id) {
        if ("text".equals(id)) {
            TextArea t = (TextArea) getContainer();
            return t.getText().trim();
        }
        return super.getComponentValue(id);
    }

    public void setComponentValue(String id, Object value) {
        if ("text".equals(id)) {
            TextArea t = (TextArea) getContainer();
            t.setText(value.toString());
            return;
        }
        if ("w".equals(id)) {
            TextArea t = (TextArea) getContainer();
            int w = ((Integer) value).intValue();
            // t.setColumns(w);
            return;
        }
        if ("h".equals(id)) {
            TextArea t = (TextArea) getContainer();
            int h = ((Integer) value).intValue();
            // t.setRows(h);
            return;
        }
        super.setComponentValue(id, value);
    }

}

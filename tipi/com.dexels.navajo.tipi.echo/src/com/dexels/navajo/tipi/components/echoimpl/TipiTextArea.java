/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
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
	private static final long serialVersionUID = 568076805655127962L;

	public TipiTextArea() {
    }

    public Object createContainer() {
        TextArea rta = new TextArea();
//        rta.set
//        rta.setStyleName("Default");
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
//            TextArea t = (TextArea) getContainer();
//            int w = ((Integer) value).intValue();
            // t.setColumns(w);
            return;
        }
        if ("h".equals(id)) {
//            TextArea t = (TextArea) getContainer();
//            int h = ((Integer) value).intValue();
            // t.setRows(h);
            return;
        }
        super.setComponentValue(id, value);
    }

}

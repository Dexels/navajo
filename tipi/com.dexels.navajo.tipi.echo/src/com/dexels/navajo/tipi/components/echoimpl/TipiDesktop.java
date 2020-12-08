/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.tipi.components.echoimpl;

import nextapp.echo2.app.Color;
import nextapp.echo2.app.ContentPane;
import nextapp.echo2.app.Style;

import com.dexels.navajo.echoclient.components.Styles;

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

	private static final long serialVersionUID = 7088091255359938129L;
	private ContentPane myContainer;

	public TipiDesktop() {
    }

    public Object createContainer() {
        myContainer = new ContentPane();
        Style ss = Styles.DEFAULT_STYLE_SHEET.getStyle(ContentPane.class, "Desktop");
        myContainer.setStyle(ss);
//        myContainer.setBackground(new Color(180,180,240));
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

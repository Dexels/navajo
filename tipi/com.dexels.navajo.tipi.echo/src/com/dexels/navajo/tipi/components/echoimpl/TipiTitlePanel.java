/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
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

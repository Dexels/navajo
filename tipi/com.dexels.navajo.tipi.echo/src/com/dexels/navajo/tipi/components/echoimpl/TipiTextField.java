/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.tipi.components.echoimpl;

import nextapp.echo2.app.TextField;

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

public class TipiTextField extends TipiEchoComponentImpl {
	private static final long serialVersionUID = 5579720008151004727L;

	public TipiTextField() {
    }

    public Object createContainer() {
        TextField b = new TextField();
//        b.setStyleName("Default");
      
                return b;
    }

    protected void setComponentValue(String name, Object object) {
        TextField b = (TextField) getContainer();
        if ("text".equals(name)) {
            b.setText("" + object);
        }
        if ("enabled".equals(name)) {
            b.setEnabled("true".equals(object));
        }
        super.setComponentValue(name, object);
    }

}

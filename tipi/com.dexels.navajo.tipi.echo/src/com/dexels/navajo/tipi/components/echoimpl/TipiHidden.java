/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
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


}

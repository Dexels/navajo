/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.tipi.swingclient.components;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * Title: Seperate project for Navajo Swing client
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2003
 * </p>
 * <p>
 * Company: Dexels
 * </p>
 *  
 * @author not attributable
 * @version 1.0
 */

public class RowAttribute {
    private Map<Integer, Object> myAttributes;

    public final static int ROW_BACKGROUND_COLOR = 1;
    public final static int ROW_FOREGROUND_COLOR = 2;

    public RowAttribute() {
        myAttributes = new HashMap<Integer, Object>();
    }

    public Object getAttribute(int i) {
        return myAttributes.get(i);

    }

    public void setAttribute(int type, Object value) {
        myAttributes.put(type, value);
    }

}

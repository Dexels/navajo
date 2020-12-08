/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.tipi.components.echoimpl.impl;

import java.util.HashMap;
import java.util.Map;

import nextapp.echo2.app.Component;
import nextapp.echo2.app.Extent;

public abstract class DefaultTipiLayoutManager implements TipiLayoutManager {

    private final Map layoutComponentMap = new HashMap();

    public void addLayoutComponent(String name, Component comp) {
        layoutComponentMap.put(name, comp);
    }

    public void removeLayoutComponent(Component comp) {
        // layoutComponentMap.values();
    }

    public Extent[] preferredLayoutSize(Component parent) {
        return new Extent[] { new Extent(100, Extent.PERCENT), new Extent(100, Extent.PERCENT) };
    }

    public Extent[] minimumLayoutSize(Component parent) {
        return new Extent[] { new Extent(10, Extent.PX), new Extent(10, Extent.PX) };
    }

}

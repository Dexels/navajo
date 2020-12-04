/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.tipi.components.echoimpl.impl;

import nextapp.echo2.app.Component;
import nextapp.echo2.app.LayoutData;

public class TipiBorderLayout extends DefaultTipiLayoutManager {

    public void layoutContainer(Component parent) {
        for (int i = 0; i < parent.getComponentCount(); i++) {
            Component currentChild = parent.getComponent(i);
            LayoutData l = currentChild.getLayoutData();
            layoutChild(parent, currentChild, l);
        }
    }

    private void layoutChild(Component parent, Component currentChild, LayoutData l) {
    }

}

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

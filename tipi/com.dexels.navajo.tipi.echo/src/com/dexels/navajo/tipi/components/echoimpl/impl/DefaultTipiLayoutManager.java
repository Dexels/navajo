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

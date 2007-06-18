package com.dexels.navajo.echoclient.components;

import nextapp.echo2.app.LayoutData;

public class BorderLayoutConstraint implements LayoutData {
    private final String myDirection;

    public BorderLayoutConstraint(String dir) {
        myDirection = dir;
    }
}

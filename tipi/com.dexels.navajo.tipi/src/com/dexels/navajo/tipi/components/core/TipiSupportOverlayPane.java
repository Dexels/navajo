package com.dexels.navajo.tipi.components.core;

import java.awt.Container;

public interface TipiSupportOverlayPane {
    public Container getOverlayContainer();
    
    public void addOverlayProgressPanel(String type);

    public void removeOverlayProgressPanel();
    
    public boolean isHidden();
}

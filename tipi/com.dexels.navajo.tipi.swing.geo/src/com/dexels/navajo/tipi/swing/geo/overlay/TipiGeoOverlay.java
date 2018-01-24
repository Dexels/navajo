package com.dexels.navajo.tipi.swing.geo.overlay;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

public class TipiGeoOverlay extends JPanel {

    
    /**
     * 
     */
    private static final long serialVersionUID = -4586858157589730168L;

    public TipiGeoOverlay() {
        this.setOpaque(true); // Set to true to see it
        this.setBackground(Color.GREEN);
        this.setSize(300, 150);
        this.setLocation(10, 10);
        this.setBorder(BorderFactory.createLineBorder(Color.white));
       
    }
}

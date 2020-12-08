/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
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

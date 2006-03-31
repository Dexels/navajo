/*
 * Created on Mar 29, 2006
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.dexels.navajo.tipi.components.swingimpl.swing;

import java.awt.*;

import javax.swing.*;

public class TipiSwingScrollPane extends JScrollPane {
    private Dimension checkMax(Dimension preferredSize) {
        Dimension maximumSize = getMaximumSize();
        if (maximumSize==null) {
            return preferredSize;
        }
        return new Dimension(Math.min(preferredSize.width, maximumSize.width),Math.min(preferredSize.height, maximumSize.height));
    }
    private Dimension checkMin(Dimension preferredSize) {
        Dimension minimumSize = getMinimumSize();
        if (minimumSize==null) {
            return preferredSize;
        }
        return new Dimension(Math.max(preferredSize.width, minimumSize.width),Math.max(preferredSize.height, minimumSize.height));
    }

    public Dimension checkMaxMin(Dimension d) {
        return checkMin(checkMax(d));
    }
    public Dimension getPreferredSize() {
        return checkMaxMin(super.getPreferredSize());
    }

}

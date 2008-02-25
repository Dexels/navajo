/*
 * Created on Mar 29, 2006
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.dexels.navajo.tipi.swingclient.components;

import java.awt.*;

import javax.swing.*;

public class SizeLimitedScrollPane extends JScrollPane {

    private JComponent scrollingComponent = null;
    
    private Dimension checkMax(Dimension preferredSize, Dimension maximumSize) {
//        Dimension maximumSize = getMaximumSize();
        if (maximumSize==null) {
            return preferredSize;
        }
        return new Dimension(Math.min(preferredSize.width, maximumSize.width),Math.min(preferredSize.height, maximumSize.height));
    }
    private Dimension checkMin(Dimension preferredSize, Dimension minimumSize) {
//        Dimension minimumSize = getMinimumSize();
        if (minimumSize==null) {
            return preferredSize;
        }
        return new Dimension(Math.max(preferredSize.width, minimumSize.width),Math.max(preferredSize.height, minimumSize.height));
    }

    public Dimension checkMaxMin(Dimension preferredSize, Dimension maximumSize, Dimension minimumSize) {
        return checkMin(checkMax(preferredSize,maximumSize),minimumSize);
    }
//    public Dimension getPreferredSize() {
//        return checkMaxMin(getViewport().getPreferredSize(), getViewport().getMaximumSize(), getViewport().getMaximumSize());
//    }
//    public void setPreferredSize(Dimension d) {
//        System.err.println("SETPREF. in scroller called.: "+d);
//        Thread.dumpStack();
//        }

//    public void setScrollingComponent(JComponent c) {
//        scrollingComponent = c;
//        getViewport().add(c);
//        
//    }
}

/*
 * Created on Mar 21, 2006
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.dexels.navajo.tipi.components.swingimpl;

import java.awt.*;

import javax.swing.*;

public class TipiRow extends TipiPanel {

    public Object createContainer() {
        Container container = (Container)super.createContainer();
        container.setLayout(new BoxLayout(container,BoxLayout.Y_AXIS));
        return container;
    }

}

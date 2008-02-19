/*
 * Created on Mar 21, 2006
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.dexels.navajo.tipi.components.swingimpl;

import java.awt.*;

import com.dexels.navajo.tipi.components.swingimpl.swing.*;

public class TipiColumn extends TipiPanel {

//    <value direction="in" name="enabled" type="boolean" />
//    <value direction="in" name="visible" type="boolean" />
//    <value direction="inout" name="title" type="string" />
//    <value direction="in" name="background" type="color" />
//    <value direction="in" name="foreground" type="color" />
//    <value direction="in" name="border" type="border" />
    
    public Object createContainer() {
        Container container = (Container)super.createContainer();
        container.setLayout(new GridBagLayout());
        return container;
    }

    public void addToContainer(Object c, Object constraints) {
        int currentCount = getChildCount();
        super.addToContainer(c,  new TipiSwingGridBagConstraints(0, currentCount, 1, 1,1, 1, GridBagConstraints.NORTHWEST, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
    }
    

}

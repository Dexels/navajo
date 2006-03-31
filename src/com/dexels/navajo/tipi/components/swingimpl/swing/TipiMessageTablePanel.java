/*
 * Created on Feb 10, 2006
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.dexels.navajo.tipi.components.swingimpl.swing;

import java.awt.*;

import com.dexels.navajo.swingclient.components.*;
import com.dexels.navajo.tipi.*;

public class TipiMessageTablePanel extends MessageTablePanel {
    public TipiMessageTablePanel(TipiContext tc) {
        super(new TipiMessageTable(tc));
    }
    
    public void setPreferredSize(Dimension d) {
        // ignore
    }
}

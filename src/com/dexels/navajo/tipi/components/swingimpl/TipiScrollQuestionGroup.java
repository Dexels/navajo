/*
 * Created on Feb 17, 2006
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.dexels.navajo.tipi.components.swingimpl;

import java.awt.*;

import javax.swing.*;

import com.dexels.navajo.tipi.*;
import com.dexels.navajo.tipi.components.*;
import com.dexels.navajo.tipi.components.question.*;
import com.dexels.navajo.tipi.components.swingimpl.swing.*;
import com.dexels.navajo.tipi.internal.*;

public class TipiScrollQuestionGroup extends TipiBaseQuestionGroup {
    private JScrollPane jp;
    private JPanel jpanel;

    public Object createContainer() {
      jp = new JScrollPane();
      jp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
      jp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
      jpanel = new TipiSwingPanel();
     
      jpanel.setLayout(new BorderLayout());
      jp.getViewport().add(jpanel);
      TipiHelper th = new TipiSwingHelper();
      th.initHelper(this);
      addHelper(th);
      return jp;
    }

    public void addToContainer(Object c, Object constraints) {
        jpanel.add((Component)c, constraints);
    }
    public void removeFromContainer(Object c) {
        jpanel.remove((Component)c);
    }

    public void setContainerLayout(Object layout) {
         jpanel.setLayout((LayoutManager)layout);
    }

    
}

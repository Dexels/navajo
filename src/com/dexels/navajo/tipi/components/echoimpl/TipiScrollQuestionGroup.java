/*
 * Created on Feb 17, 2006
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.dexels.navajo.tipi.components.echoimpl;

import com.dexels.navajo.tipi.*;
import com.dexels.navajo.tipi.components.question.*;
import com.dexels.navajo.tipi.internal.*;

public class TipiScrollQuestionGroup extends TipiBaseQuestionGroup {

    public Object createContainer() {
        // jp = new JScrollPane();
        // jp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        // jp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        // jpanel = new JPanel();
        // jpanel.setLayout(new BorderLayout());
        // jp.getViewport().add(jpanel);
        // return jp;
        return null;
    }

    public void addToContainer(Object c, Object constraints) {
        // jpanel.add((Component)c, constraints);
    }

    public void removeFromContainer(Object c) {
        // jpanel.remove((Component)c);
    }
//
//    public void setContainerLayout(Object layout) {
//        // jpanel.setLayout((LayoutManager)layout);
//    }

}

/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
/*
 * Created on Feb 17, 2006
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.dexels.navajo.tipi.components.echoimpl;

import com.dexels.navajo.tipi.components.question.TipiBaseQuestionGroup;

/**
 * 
 * @author frank
 *@deprecated
 */
@Deprecated
public class TipiScrollQuestionGroup extends TipiBaseQuestionGroup {

    /**
	 * 
	 */
	private static final long serialVersionUID = -4333687907745383806L;

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

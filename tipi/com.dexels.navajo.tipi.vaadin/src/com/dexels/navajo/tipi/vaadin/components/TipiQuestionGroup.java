/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.tipi.vaadin.components;


import com.dexels.navajo.tipi.components.question.TipiBaseQuestionGroup;
import com.vaadin.ui.Component;
import com.vaadin.ui.Panel;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2004
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author not attributable
 * @version 1.0
 */

public class TipiQuestionGroup extends TipiBaseQuestionGroup {

	private static final long serialVersionUID = -2453065163545438139L;
	private Panel myColumn;

    @Override
	public Object createContainer() {
        myColumn = new Panel();
//        com.vaadin.ui.
        myColumn.setScrollable(true);
      
        return myColumn;
    }

    @Override
	public void setComponentValue(String name, Object object) {

        super.setComponentValue(name, object);
    }

    @Override
	public void addToContainer(Object c, Object constraints) {
        Component comp = (Component) c;
        myColumn.addComponent(comp);
    }
   
}

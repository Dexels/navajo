package com.dexels.navajo.tipi.components.echoimpl;

import nextapp.echo2.app.*;

import com.dexels.navajo.tipi.components.core.*;
import com.dexels.navajo.tipi.components.question.*;
import com.dexels.navajo.tipi.*;
import com.dexels.navajo.document.*;

import com.dexels.navajo.tipi.actions.*;

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
    private Column myColumn;

    public Object createContainer() {
        myColumn = new Column();
//        myColumn.setStyleName("Default");
        return myColumn;
    }

    public void setComponentValue(String name, Object object) {
        if (name.equals("background")) {
            Color background = (Color) object;
            myColumn.setBackground(background);
            return;
        }

        super.setComponentValue(name, object);
    }

    public void addToContainer(Object c, Object constraints) {
        Component comp = (Component) c;
        myColumn.add(comp);
    }

}

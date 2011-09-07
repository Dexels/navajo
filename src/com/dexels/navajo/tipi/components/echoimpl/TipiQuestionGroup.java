package com.dexels.navajo.tipi.components.echoimpl;

import nextapp.echo2.app.Color;
import nextapp.echo2.app.Column;
import nextapp.echo2.app.Component;

import com.dexels.navajo.tipi.components.question.TipiBaseQuestionGroup;

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
 * @deprecated
 */

@Deprecated
public class TipiQuestionGroup extends TipiBaseQuestionGroup {
	private static final long serialVersionUID = -1163029880650425912L;
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

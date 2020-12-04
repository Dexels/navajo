/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.tipi.components.echoimpl;

import nextapp.echo2.app.Color;
import nextapp.echo2.app.Column;
import nextapp.echo2.app.Component;

import com.dexels.navajo.tipi.components.question.TipiBaseQuestion;

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
public class TipiQuestion extends TipiBaseQuestion {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1103473421332245951L;


    // private TipiQuestionGroup questionGroupPath = null;
    private Column myColumn;


    public TipiQuestion() {
    }

    public Object createContainer() {
        myColumn = new Column();
        return myColumn;
    }

    public void addToContainer(Object c, Object constraints) {
        Component comp = (Component) c;
        myColumn.add(comp);
    }

    protected void setQuestionBorder(String val) {

    }

    public void setQuestionVisible(boolean b) {
        myColumn.setVisible(b);

    }

    public void setComponentValue(String name, Object object) {
        if (name.equals("background")) {
            Color background = (Color) object;
            myColumn.setBackground(background);
//            myGroupBox.setBackground(background);
            return;
        }

        super.setComponentValue(name, object);
    }

    public void setValid(boolean b, String msg) {
    }
   

}

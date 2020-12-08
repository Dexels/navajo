/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.tipi.components.echoimpl;

import nextapp.echo2.app.Column;
import nextapp.echo2.app.Component;

public class TipiColumn extends TipiEchoDataComponentImpl {

	private static final long serialVersionUID = -7956286824574976318L;
	private Column myColumn;

    public Object createContainer() {
        myColumn = new Column();
//        myColumn.setStyleName("Default");
                
        return myColumn;
    }

    public void addToContainer(Object c, Object constraints) {
        Component comp = (Component) c;
        myColumn.add(comp);
    }

}

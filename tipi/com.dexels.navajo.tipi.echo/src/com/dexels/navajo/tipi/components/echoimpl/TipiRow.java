/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.tipi.components.echoimpl;

import nextapp.echo2.app.Component;
import nextapp.echo2.app.Row;

public class TipiRow extends TipiEchoDataComponentImpl {
	private static final long serialVersionUID = -408069195341249869L;
	private Row myRow;

    public Object createContainer() {
        myRow = new Row();
        // myColumn.setLayoutData(new ColumnLayoutData());
        // myContainer.add(myRow);
//        myRow.setStyleName("Default");
        return myRow;
    }

    public void addToContainer(Object c, Object constraints) {
        Component comp = (Component) c;
        myRow.add(comp);
    }

    // public Object getActualComponent() {
    // return myColumn;
    // }

}

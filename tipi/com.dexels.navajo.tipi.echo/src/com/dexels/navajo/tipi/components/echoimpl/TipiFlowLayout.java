/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.tipi.components.echoimpl;

import nextapp.echo2.app.Row;

import com.dexels.navajo.tipi.TipiException;
import com.dexels.navajo.tipi.TipiValue;
import com.dexels.navajo.tipi.components.core.TipiLayoutImpl;

public class TipiFlowLayout extends TipiLayoutImpl {

	private static final long serialVersionUID = 5911721063631611697L;

	public void createLayout() throws TipiException {
        // myComponent.setContainerLayout(new Row());
        setLayout(new Row());
    }

    protected void setValue(String name, TipiValue tv) {
        // TODO Auto-generated method stub

    }

    public Object parseConstraint(String text, int index) {
        return null;
    }

}

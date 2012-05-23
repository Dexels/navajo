package com.dexels.navajo.tipi.components.echoimpl;

import nextapp.echo2.app.Grid;

import com.dexels.navajo.tipi.TipiValue;
import com.dexels.navajo.tipi.components.core.TipiLayoutImpl;
import com.dexels.navajo.tipi.components.echoimpl.impl.layout.TipiEchoGridBagConstraints;

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
 * @deprecated
 * @author Frank Lyaruu
 * @version 1.0
 */

@Deprecated
public class TipiEchoGridBagLayout extends TipiLayoutImpl {

	private static final long serialVersionUID = -543288866058083873L;

	public TipiEchoGridBagLayout() {
    }

    protected void setValue(String name, TipiValue tv) {
        /**
         * @todo Implement this com.dexels.navajo.tipi.internal.TipiLayout
         *       abstract method
         */
    }

    public void createLayout() throws com.dexels.navajo.tipi.TipiException {
        myLayout = new Grid(3);
        // EchoGridBagLayout p = (EchoGridBagLayout) myLayout;
        // System.err.println("EchoGridBagLayout created!!");
        // setLayout(p);
    }

    public Object parseConstraint(String text, int index) {
        // return new EchoGridBagConstraints(text);
        TipiEchoGridBagConstraints g = new TipiEchoGridBagConstraints();
        g.parse(text,index);
        return g;
    }

}

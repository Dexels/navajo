package com.dexels.navajo.tipi.components.echoimpl;

import nextapp.echo2.app.*;

import com.dexels.navajo.tipi.TipiHelper;
import com.dexels.navajo.tipi.components.core.TipiComponentImpl;
import com.dexels.navajo.tipi.components.echoimpl.helpers.EchoTipiHelper;
import com.dexels.navajo.tipi.components.echoimpl.parsers.*;

import echopointng.able.*;

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
 * @author Frank Lyaruu
 * @version 1.0
 */

public abstract class TipiEchoComponentImpl extends TipiComponentImpl {

    public TipiEchoComponentImpl() {
        TipiHelper th = new EchoTipiHelper();
        th.initHelper(this);
        addHelper(th);
    }
    public void processStyles() {
        super.processStyles();
        if (getContainer()!=null && getContainer() instanceof Positionable) {
            Positionable pos = (Positionable)getContainer();
            String s = getStyle("x");
            if (s!=null) {
//                 pos.setLeft(ExtentParser.parseExtent(s));
//                 pos.setPosition(Positionable.ABSOLUTE);
            }
            s = getStyle("y");
            if (s!=null) {
//                 pos.setTop(ExtentParser.parseExtent(s));
//                 pos.setPosition(Positionable.ABSOLUTE);
            }
         }
        if (getContainer()!=null && getContainer() instanceof Sizeable) {
            Sizeable pos = (Sizeable)getContainer();
            String s = getStyle("w");
            if (s!=null) {
                 pos.setWidth(ExtentParser.parseExtent(s));
             }
            s = getStyle("h");
            if (s!=null) {
                 pos.setHeight(ExtentParser.parseExtent(s));
            }
         }
    }

    protected void setComponentValue(String name, Object object) {
        if ("border".equals(name)) {
            if (getContainer()!=null && getContainer() instanceof Borderable) {
                Borderable comp = (Borderable)getContainer();
                comp.setBorder((Border)object);
//                comp.set
            }
        }

        super.setComponentValue(name, object);

    }

}


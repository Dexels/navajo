package com.dexels.navajo.tipi.components.echoimpl;

import nextapp.echo2.app.*;
import nextapp.echo2.app.Component;
import nextapp.echo2.app.Extent;
import nextapp.echo2.app.LayoutData;
import nextapp.echo2.app.Window;
import nextapp.echo2.app.WindowPane;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.tipi.TipiContext;
import com.dexels.navajo.tipi.TipiException;
import com.dexels.navajo.tipi.TipiHelper;
import com.dexels.navajo.tipi.components.core.TipiDataComponentImpl;
import com.dexels.navajo.tipi.components.core.TipiLayoutImpl;
import com.dexels.navajo.tipi.components.echoimpl.helpers.EchoTipiHelper;
import com.dexels.navajo.tipi.components.echoimpl.impl.TipiLayoutManager;
import com.dexels.navajo.tipi.components.echoimpl.parsers.*;
import com.dexels.navajo.tipi.internal.TipiLayout;

import echopointng.ExtentEx;
import echopointng.able.*;
import echopointng.able.Sizeable;

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

public abstract class TipiEchoDataComponentImpl extends TipiDataComponentImpl {

    // protected TipiLayout layout = null;
    protected Component layoutComponent;

    public TipiEchoDataComponentImpl() {
        TipiHelper th = new EchoTipiHelper();
        th.initHelper(this);
        addHelper(th);
    }

    public Component getLayoutComponent() {
        return layoutComponent;
    }

    // public TipiLayout getLayout() {
    // return layout;
    // }

    public void removeFromContainer(Object c) {
        Component cc = (Component) getContainer();
        Component child = (Component) c;
        cc.remove(child);
    }

    public void addToContainer(Object c, Object constraints) {
        Component cc;
        cc = (Component) getContainer();
        Component child = (Component) c;
        if (layoutComponent != null) {
            cc = layoutComponent;
        }
        if (child instanceof WindowPane) {
            TipiScreen s = (TipiScreen) getContext().getDefaultTopLevel();
            // Watch this.
            final Window w = (Window) s.getTopLevel();
            w.getContent().add(child);
        } else {
            cc.add(child);
            if (constraints != null && constraints instanceof LayoutData) {
                child.setLayoutData((LayoutData) constraints);
            }
            if (getLayout() != null) {
                getLayout().childAdded(c);
            }

        }
    }

    public void setContainerLayout(Object layout) {
        if (layout instanceof TipiLayoutManager) {
            /* 'Real layout' */
            // layoutComponent = (TipiLayoutManager)layout;
        } else {
            if (layout instanceof Component) {
            	System.err.println("Deprecated layout usage IN TipiEchoDataComponent");
                layoutComponent = (Component) layout;
                if (layoutComponent instanceof Sizeable) {
                    ((Sizeable) layoutComponent).setWidth(new Extent(100, Extent.PERCENT));
                    ((Sizeable) layoutComponent).setHeight(new Extent(100, Extent.PERCENT));
                }
                ((Component) getContainer()).add(layoutComponent);

            } else {
                System.err.println("*********************\nStrange layout found!\n*********************");
            }
        }
    }

    /**
     * loadData
     * 
     * @param n
     *            Navajo
     * @param context
     *            TipiContext
     * @throws TipiException
     * @todo Implement this com.dexels.navajo.tipi.TipiDataComponent method
     */
    public void loadData(Navajo n, TipiContext context, String method) throws TipiException {
        super.loadData(n, context, method);
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
    

}

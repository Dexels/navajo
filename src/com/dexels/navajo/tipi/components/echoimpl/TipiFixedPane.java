package com.dexels.navajo.tipi.components.echoimpl;

import nextapp.echo2.app.Color;
import nextapp.echo2.app.Component;
import nextapp.echo2.app.Extent;
import nextapp.echo2.app.LayoutData;
import nextapp.echo2.app.event.WindowPaneEvent;
import nextapp.echo2.app.event.WindowPaneListener;

import com.dexels.navajo.tipi.TipiComponentMethod;
import com.dexels.navajo.tipi.TipiHelper;
import com.dexels.navajo.tipi.components.echoimpl.helpers.EchoTipiHelper;
import com.dexels.navajo.tipi.internal.TipiEvent;

import echopointng.ContainerEx;
import echopointng.able.Positionable;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

/**
 * @todo Need to refactor menus in internalframes. Now still uses the old mode
 *       Frank
 */
public final class TipiFixedPane
// extends DefaultTipi {
        extends TipiEchoDataComponentImpl {
    // private WindowPane myWindow;
    private String myMenuBar = "";

    private String myTitle;

    private ContainerEx innerContainer;

    public Object createContainer() {
        // myWindow = new WindowPane();
        innerContainer = new ContainerEx();
        innerContainer.setPosition(Positionable.FIXED);
        innerContainer.setLeft(new Extent(0, Extent.PX));
        innerContainer.setTop(new Extent(0, Extent.PX));

        TipiHelper th = new EchoTipiHelper();
        th.initHelper(this);
        addHelper(th);
        return innerContainer;
    }

    public Object getComponentValue(String name) {
        if ("visible".equals(name)) {
            return new Boolean(innerContainer.isVisible());
        }
        return super.getComponentValue(name);
    }

    private final void myWindow_internalFrameClosed(WindowPaneEvent arg0) {
        // myContext.disposeTipi(this);
    }

    public void addToContainer(final Object c, final Object constraints) {
        innerContainer.add((Component) c);
        if (constraints != null && constraints instanceof LayoutData) {
            ((ContainerEx) getContainer()).setLayoutData((LayoutData) constraints);

        }
        // ((SwingTipiContext)myContext).addTopLevel(c);
        // });
    }

    public void removeFromContainer(final Object c) {
        // runSyncInEventThread(new Runnable() {
        // public void run() {
        innerContainer.remove((Component) c);
        // ( (JInternalFrame) getContainer()).getContentPane().remove(
        // (Component) c);
        // ((SwingTipiContext)myContext).removeTopLevel(c);
        // }
        // });
    }

    public void setContainerLayout(final Object layout) {
        // runSyncInEventThread(new Runnable() {
        // public void run() {

        // eueueuh
        // ( (JInternalFrame) getContainer()).getContentPane().setLayout(
        // (LayoutManager) layout);
        // }
        // });
    }

    public final void setComponentValue(final String name, final Object object) {
        super.setComponentValue(name, object);
        if (object == null) {
            System.err.println("Null object. Name = " + name);
        }
        if (name.equals("background")) {
            innerContainer.setBackground((Color) object);
        }

    }

    private final void doPerformMethod(String name, TipiComponentMethod compMeth) {
    }

    protected void performComponentMethod(final String name, final TipiComponentMethod compMeth, TipiEvent event) {
        doPerformMethod(name, compMeth);
    }

    public boolean isReusable() {
        return true;
    }

    public void reUse() {
    }
}

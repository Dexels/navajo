package com.dexels.navajo.tipi.components.echoimpl;

import nextapp.echo2.app.Color;
import nextapp.echo2.app.Component;
import nextapp.echo2.app.Extent;
import nextapp.echo2.app.LayoutData;
import nextapp.echo2.app.WindowPane;
import nextapp.echo2.app.event.WindowPaneEvent;
import nextapp.echo2.app.event.WindowPaneListener;

import com.dexels.navajo.tipi.TipiComponentMethod;
import com.dexels.navajo.tipi.TipiHelper;
import com.dexels.navajo.tipi.components.echoimpl.helpers.EchoTipiHelper;
import com.dexels.navajo.tipi.components.echoimpl.parsers.ColorParser;
import com.dexels.navajo.tipi.internal.TipiEvent;

import echopointng.ContainerEx;
import echopointng.LabelEx;

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
public final class TipiWindow
// extends DefaultTipi {
        extends TipiEchoDataComponentImpl {
    private WindowPane myWindow;

    private String myMenuBar = "";

    private String myTitle;

    private ContainerEx innerContainer;

    public void disposeComponent() {
        myWindow.setVisible(false);
        myWindow.dispose();
        super.disposeComponent();
    }

    public Object createContainer() {
        myWindow = new WindowPane();
        myWindow.setStyleName("window");
        TipiHelper th = new EchoTipiHelper();
        th.initHelper(this);
        addHelper(th);
        innerContainer = new ContainerEx();
        myWindow.add(innerContainer);
        myWindow.addWindowPaneListener(new WindowPaneListener() {
            public void windowPaneClosing(WindowPaneEvent arg0) {
                myWindow_internalFrameClosed(arg0);
            }
        });
        myWindow.setDefaultCloseOperation(WindowPane.DO_NOTHING_ON_CLOSE);
        myWindow.setClosable(false);
        return myWindow;
    }

    public Object getComponentValue(String name) {
        if ("visible".equals(name)) {
            return new Boolean(myWindow.isVisible());
        }
        // if("title".equals(name)){
        // return myWindow.getTitle();
        // }
        // Rectangle r = myWindow.getBounds();
        // if (name.equals("x")) {
        // return new Integer(r.x);
        // }
        // if (name.equals("y")) {
        // return new Integer(r.y);
        // }
        // if (name.equals("w")) {
        // return new Integer(r.width);
        // }
        // if (name.equals("h")) {
        // return new Integer(r.height);
        // }
        // if (name.equals("iconifiable")) {
        // return new Boolean(myWindow.isIconifiable());
        // }
        // if (name.equals("maximizable")) {
        // return new Boolean(myWindow.isMaximizable());
        // }
        if (name.equals("closable")) {
            return new Boolean(myWindow.isClosable());
        }
        if (name.equals("resizable")) {
            return new Boolean(myWindow.isResizable());
        }
        if (name.equals("title")) {
            return myWindow.getTitle();
        }

        return super.getComponentValue(name);
    }

    private final void myWindow_internalFrameClosed(WindowPaneEvent arg0) {
    		myWindow.setVisible(false);
    	// myContext.disposeTipi(this);
    }

    public void addToContainer(final Object c, final Object constraints) {
        innerContainer.add((Component) c);
        if (constraints != null && constraints instanceof LayoutData) {
            ((WindowPane) getContainer()).setLayoutData((LayoutData) constraints);

        }
        // ((SwingTipiContext)myContext).addTopLevel(c);
        // });
    }

    public void removeFromContainer(final Object c) {
        // runSyncInEventThread(new Runnable() {
        // public void run() {
        ((WindowPane) getContainer()).remove((Component) c);
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

    public void processStyles() {
//      System.err.println("Processing styles.... "+styleHintMap);
      super.processStyles();
      final WindowPane jj = (WindowPane) getContainer();
      Color c = ColorParser.parseColor(getStyle("titlebackground"));
      if (c!=null) {
          jj.setTitleBackground(c);
      }
     
       }
    
    public final void setComponentValue(final String name, final Object object) {
        super.setComponentValue(name, object);

        final WindowPane jj = (WindowPane) getContainer();
        if (name.equals("background")) {
            jj.setBackground((Color) object);
        }
        if (name.equals("closable")) {
            boolean b = ((Boolean) object).booleanValue();
            jj.setClosable(b);
        }
        if (name.equals("resizable")) {
            boolean b = ((Boolean) object).booleanValue();
            jj.setResizable(b);
        }
        if (name.equals("x")) {
            jj.setPositionX(new Extent(((Integer) object).intValue(), Extent.PX));
        }
        if (name.equals("y")) {
            jj.setPositionY(new Extent(((Integer) object).intValue(), Extent.PX));
        }
        if (name.equals("w")) {
            jj.setWidth(new Extent(((Integer) object).intValue(), Extent.PX));
        }
        if (name.equals("h")) {
            jj.setHeight(new Extent(((Integer) object).intValue(), Extent.PX));
        }
        if (name.equals("title")) {
            myTitle = object.toString();
            setTitle(myTitle);
        }
    }

    protected void setTitle(final String s) {
        myWindow.setTitle(s);
    }

    private final void doPerformMethod(String name, TipiComponentMethod compMeth) {

        // TODO THis one may be fixable
        // if (name.equals("toFront")) {
        // JInternalFrame jj = (JInternalFrame) getContainer();
        // TipiSwingDesktop tt = (TipiSwingDesktop) jj.getParent();
        // if (jj==null || tt==null) {
        // return;
        // }
        // jj.toFront();
        // if (tt.getDesktopManager()==null) {
        // return;
        // }
        // tt.getDesktopManager().activateFrame(jj);
        // }
    }

    protected void performComponentMethod(final String name, final TipiComponentMethod compMeth, TipiEvent event) {
        doPerformMethod(name, compMeth);
    }

    public boolean isReusable() {
        return true;
    }

    public void reUse() {
        // if (myParent!=null) {
        // myParent.addToContainer();
        // }
        // myWindow.setVisible(true);
    }
}

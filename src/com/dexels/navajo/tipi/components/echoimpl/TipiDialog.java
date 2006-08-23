package com.dexels.navajo.tipi.components.echoimpl;

import nextapp.echo2.app.*;
import nextapp.echo2.app.Font.Typeface;
import nextapp.echo2.app.event.WindowPaneEvent;
import nextapp.echo2.app.event.WindowPaneListener;

import com.dexels.navajo.tipi.TipiBreakException;
import com.dexels.navajo.tipi.TipiComponent;
import com.dexels.navajo.tipi.TipiComponentMethod;
import com.dexels.navajo.tipi.TipiException;
import com.dexels.navajo.tipi.TipiHelper;
import com.dexels.navajo.tipi.components.echoimpl.helpers.EchoTipiHelper;
import com.dexels.navajo.tipi.components.echoimpl.parsers.*;
import com.dexels.navajo.tipi.internal.TipiEvent;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2003
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author not attributable
 * @version 1.0
 */
public class TipiDialog extends TipiEchoDataComponentImpl {
    private boolean disposed = false;

    private WindowPane myDialog = null;

    private boolean modal = false;

    private boolean decorated = true;

    private boolean showing = false;

    private String title = "";

    int x = 0, y = 0, w = 0, h = 0;

    // private Rectangle myBounds = new Rectangle(0, 0, 0, 0);
    private boolean studioMode = false;

    private boolean closable = false;

    private boolean resizable = true;

    private int headerheight = 25;
    private int leftheaderinset = 0;
    private int topheaderinset = 0;
    private int rightheaderinset = 0;
    private int bottomheaderinset = 0;
    
    private Color headerforeground = null;
    private Color headerbackground = null;
    private Font headerfont = null;

    // headerheight leftheaderinset topheaderinset rightheaderinset bottomheaderinset
    
    // headerfont headerforeground headerbackground

    
    
    
    public TipiDialog() {
    }

    public Object createContainer() {
        Component tp = new Row();
        TipiHelper th = new EchoTipiHelper();
        th.initHelper(this);
        // tp.setBackground(new Color(200, 100, 100));
        addHelper(th);
        return tp;
    }

    // }
    private final void dialog_windowClosing(WindowPaneEvent e) {
        WindowPane d = (WindowPane) e.getSource();
        try {
            performTipiEvent("onWindowClosed", null, true);
        } catch (TipiException ex) {
            ex.printStackTrace();
        }
        myContext.disposeTipiComponent(this);
        disposed = true;
    }

    protected void createWindowListener(WindowPane d) {
        d.setDefaultCloseOperation(WindowPane.DO_NOTHING_ON_CLOSE);
        d.addWindowPaneListener(new WindowPaneListener() {
            public void windowPaneClosing(WindowPaneEvent arg0) {
                dialog_windowClosing(arg0);
            }
        });
    }

    // public void removeFromContainer(Object c) {
    // getSwingContainer().remove( (Component) c);
    // }
    public void setComponentValue(final String name, final Object object) {
        // runSyncInEventThread(new Runnable() {
        // public void run() {

        if (name.equals("modal")) {
            modal = ((Boolean) object).booleanValue();
            return;
        }
        if (name.equals("decorated")) {
            decorated = ((Boolean) object).booleanValue();
            return;
        }
        if (name.equals("title")) {
            title = object.toString();
            return;
        }
        if (name.equals("x")) {
            x = ((Integer) object).intValue();
            return;
        }
        if (name.equals("y")) {
            y = ((Integer) object).intValue();
            return;
        }
        if (name.equals("w")) {
            w = ((Integer) object).intValue();
            return;
        }
        if (name.equals("h")) {
            h = ((Integer) object).intValue();
            return;
        }

        final Component jj = (Component) getContainer();
        // runSyncInEventThread(new Runnable() {
        // public void run() {
        // if (name.equals("iconifiable")) {
        // boolean b = ( (Boolean) object).booleanValue();
        // jj.setIconifiable(b);
        // }
        if (name.equals("background")) {
            jj.setBackground((Color) object);
        }
        // if (name.equals("maximizable")) {
        // boolean b = ( (Boolean) object).booleanValue();
        // jj.setMaximizable(b);
        // }
        if (name.equals("closable")) {
            closable = ((Boolean) object).booleanValue();
        }
        if (name.equals("resizable")) {
            resizable = ((Boolean) object).booleanValue();
        }

        super.setComponentValue(name, object);
    }

    public Object getComponentValue(String name) {
        /** @todo Override this com.dexels.navajo.tipi.impl.DefaultTipi method */
        if ("isShowing".equals(name)) {
            // return new Boolean( ( (JDialog) getContainer()).isVisible());
            return new Boolean(showing);
        }
        if ("title".equals(name)) {
            // return ( (JDialog) getContainer()).getTitle();
            return title;
        }
        // if (name.equals("x")) {
        // return new Integer(myBounds.x);
        // }
        // if (name.equals("y")) {
        // return new Integer(myBounds.y);
        // }
        // if (name.equals("w")) {
        // return new Integer(myBounds.width);
        // }
        // if (name.equals("h")) {
        // return new Integer(myBounds.height);
        // }
        return super.getComponentValue(name);
    }

    public void disposeComponent() {
        if (myDialog != null) {
            myDialog.setVisible(false);
        }
        super.disposeComponent();
    }

    private final void constructDialog() {
        // System.err.println("Constructing: studio? "+isStudioElement());
        if (myContext.isStudioMode() && !isStudioElement()) {
            //
            studioMode = true;
        } else {
            constructStandardDialog();
            studioMode = false;
        }
    }

    private final void constructStandardDialog() {
        TipiScreen s = (TipiScreen) getContext().getDefaultTopLevel();
        final Window win = (Window) s.getTopLevel();
        if (w == 0) {
            w = 400;
        }
        if (h == 0) {
            h = 400;
        }
        if (x == 0) {
            x = 100;
        }
        if (y == 0) {
            y = 100;
        }
        myDialog = new WindowPane(title, new Extent(w, Extent.PX), new Extent(h, Extent.PX));
        myDialog.setDefaultCloseOperation(WindowPane.DISPOSE_ON_CLOSE);
        // myDialog.setUndecorated(!decorated);
        FillImageBorder fib = new FillImageBorder(new Color(100,100,100),new Insets(new Extent(1,Extent.PX)),new Insets(new Extent(2,Extent.PX)));
        myDialog.setBorder(fib);
        createWindowListener(myDialog);
        myDialog.setTitle(title);
//        myDialog.
        // myDialog.toFront();
        // if (myBar != null) {
        // myDialog.setJMenuBar(myBar);
        // }
        win.getContent().add(myDialog);
        // myDialog.setModal(true);
        myDialog.add((Component) getContainer());

        myDialog.setPositionX(new Extent(x, Extent.PX));
        myDialog.setPositionY(new Extent(y, Extent.PX));
        myDialog.setModal(modal);
        myDialog.setResizable(resizable);
        // ARRRRRRRGGGGHHHHH
        myDialog.setClosable(closable);

        if (headerbackground!=null) {
            myDialog.setTitleBackground(headerbackground);
        }
        if (headerforeground!=null) {
            myDialog.setTitleForeground(headerforeground);
        }
        
//        myDialog.setTitleBackground(new Color(232, 232, 232));
        myDialog.setTitleInsets(new Insets(leftheaderinset, topheaderinset, rightheaderinset, bottomheaderinset));
//        myDialog.setTitleForeground(new Color(0, 0, 0));
//        myDialog.setTitleFont(new Font(Font.ARIAL, Font.PLAIN, new Extent(10, Extent.PT)));
        if (headerheight!=0) {
            myDialog.setTitleHeight(new Extent(headerheight, Extent.PX));
        }
        myDialog.setVisible(true);
    }
    public void processStyles() {
//        System.err.println("Processing styles.... "+styleHintMap);
        super.processStyles();
        
        String s = getStyle("headerheight");
        if (s!=null) {
            headerheight = Integer.parseInt(s);
        }
        s = getStyle("leftheaderinset");
        if (s!=null) {
            leftheaderinset = Integer.parseInt(s);
        }
        s = getStyle("topheaderinset");
        if (s!=null) {
            topheaderinset = Integer.parseInt(s);
        }
        s = getStyle("rightheaderinset");
        if (s!=null) {
            rightheaderinset = Integer.parseInt(s);
        }
        s = getStyle("bottomheaderinset");
        if (s!=null) {
            bottomheaderinset = Integer.parseInt(s);
        }
        
        // headerheight leftheaderinset topheaderinset rightheaderinset bottomheaderinset
        
        // headerfont headerforeground headerbackground

        
        Color c = ColorParser.parseColor(getStyle("foreground"));
        if (c!=null) {
            headerforeground = c;
        }
        c = ColorParser.parseColor(getStyle("background"));
        if (c!=null) {
            headerbackground = c;
        }
        s = getStyle("headerfont");
        Font f= FontParser.parseFont(s);
        if (s!=null) {
            headerfont = f;
        }
    }
    
    protected void helperRegisterEvent(TipiEvent te) {
        if (te != null && te.getEventName().equals("onWindowClosed")) {
            // overridden.. should be ok.
        } else {
            super.helperRegisterEvent(te);
        }
    }

    public void addToContainer(Object c, Object constraints) {
        super.addToContainer(c, constraints);
    }

    protected synchronized void performComponentMethod(String name, TipiComponentMethod compMeth, TipiEvent event) throws TipiBreakException {
        final TipiComponent me = this;
        // final Thread currentThread = Thread.currentThread();
        // final boolean amIEventThread =
        // SwingUtilities.isEventDispatchThread();
        super.performComponentMethod(name, compMeth, event);
        if (name.equals("show")) {
            constructDialog();
        }
        // }
        // });
        if (name.equals("hide")) {
            if (myDialog != null) {
                myDialog.setVisible(false);
            }
        }
        if (name.equals("dispose")) {
            if (myDialog != null) {
                myDialog.setVisible(false);
                myDialog = null;
            }
            myContext.disposeTipiComponent(me);
            disposed = true;
        }
    }

    public void reUse() {
    }
}

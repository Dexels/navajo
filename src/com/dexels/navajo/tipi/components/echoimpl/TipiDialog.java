package com.dexels.navajo.tipi.components.echoimpl;

import nextapp.echo2.app.*;
import nextapp.echo2.app.Font.Typeface;
import nextapp.echo2.app.event.WindowPaneEvent;
import nextapp.echo2.app.event.WindowPaneListener;

import com.dexels.navajo.echoclient.components.Styles;
import com.dexels.navajo.tipi.TipiBreakException;
import com.dexels.navajo.tipi.TipiComponent;
import com.dexels.navajo.tipi.TipiComponentMethod;
import com.dexels.navajo.tipi.TipiException;
import com.dexels.navajo.tipi.TipiHelper;
import com.dexels.navajo.tipi.components.echoimpl.helpers.EchoTipiHelper;
import com.dexels.navajo.tipi.components.echoimpl.parsers.*;
import com.dexels.navajo.tipi.internal.TipiEvent;

import echopointng.ContainerEx;

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

    private WindowPane myWindow = null;

    private boolean modal = false;

    private boolean decorated = true;

    private boolean showing = false;

    private String title = "";
 
    int x = 0, y = 0, w = 0, h = 0;

    // private Rectangle myBounds = new Rectangle(0, 0, 0, 0);
    private boolean studioMode = false;

    private boolean closable = false;

    private boolean resizable = true;
    private ContentPane innerContainer;

    private int headerheight = 25;
    private int leftheaderinset = 0;
    private int topheaderinset = 0;
    private int rightheaderinset = 0;
    private int bottomheaderinset = 0;
    
    private Color headerforeground = null;
    private Color headerbackground = null;
    private Font headerfont = null;

	private String myTitle;

	private boolean movable;

    // headerheight leftheaderinset topheaderinset rightheaderinset bottomheaderinset
    
    // headerfont headerforeground headerbackground

    
    
    
    public TipiDialog() {
    }

    public Object createContainer() {

            myWindow = new WindowPane();
			Style ss = Styles.DEFAULT_STYLE_SHEET.getStyle(WindowPane.class, "Default");
            myWindow.setStyle(ss);
            TipiHelper th = new EchoTipiHelper();
            th.initHelper(this);
            addHelper(th);
            innerContainer = new ContentPane();
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

    public Component getInnerComponent() {
    	return innerContainer;
    }
  
    private final void myWindow_internalFrameClosed(WindowPaneEvent arg0) {
		myWindow.setVisible(false);
	// myContext.disposeTipi(this);
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
            myWindow.setWidth(new Extent(w,Extent.PX));
            return;
        }
        if (name.equals("h")) {
            h = ((Integer) object).intValue();
            myWindow.setHeight(new Extent(h,Extent.PX));
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
        if (name.equals("movable")) {
             movable = ((Boolean) object).booleanValue();
              }        
        if (name.equals("title")) {
            myTitle = object.toString();
            myWindow.setTitle(myTitle);
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
        if (myWindow != null) {
        	myWindow.setVisible(false);
        }
        super.disposeComponent();
        TipiScreen s = (TipiScreen) getContext().getDefaultTopLevel();
        final Window win = (Window) s.getTopLevel();
        ContentPane content = win.getContent();

        content.remove(myWindow);
        myWindow = null;
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
//        myWindow = new WindowPane(title, new Extent(w, Extent.PX), new Extent(h, Extent.PX));
//        myWindow.setStyleName("dialog");
//        myWindow.setDefaultCloseOperation(WindowPane.DISPOSE_ON_CLOSE);
//        FillImageBorder fib = new FillImageBorder(new Color(100,100,100),new Insets(new Extent(1,Extent.PX)),new Insets(new Extent(2,Extent.PX)));
//        myWindow.setBorder(fib);
//        createWindowListener(myWindow);
        myWindow.setTitle(title);
        win.getContent().add(myWindow);
//        myWindow.add((Component) getContainer());

        myWindow.setPositionX(new Extent(x, Extent.PX));
        myWindow.setPositionY(new Extent(y, Extent.PX));
        myWindow.setModal(modal);
        myWindow.setResizable(resizable);
        // ARRRRRRRGGGGHHHHH
        myWindow.setClosable(closable);
        myWindow.setMovable(movable);
        myWindow.setZIndex(((EchoTipiContext)myContext).acquireHighestZIndex());
        

//        if (headerbackground!=null) {
//        	myWindow.setTitleBackground(headerbackground);
//        }
//        if (headerforeground!=null) {
//        	myWindow.setTitleForeground(headerforeground);
//        }
//        
////        myWindow.setTitleInsets(new Insets(leftheaderinset, topheaderinset, rightheaderinset, bottomheaderinset));
//        if (headerheight!=0) {
//        	myWindow.setTitleHeight(new Extent(headerheight, Extent.PX));
//        }
        myWindow.setVisible(true);
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

    public void addToContainer(final Object c, final Object constraints) {
		Component cc = innerContainer;
		if (layoutComponent != null) {
			// do layoutstuff
			layoutComponent.setParentComponent(this);
			layoutComponent.addChildComponent((Component)c, constraints);
		} else {
			cc.add((Component) c);
			if (constraints != null && constraints instanceof LayoutData) {
				((WindowPane) getContainer()).setLayoutData((LayoutData) constraints);

			}
		}

	}


    protected synchronized void performComponentMethod(String name, TipiComponentMethod compMeth, TipiEvent event) throws TipiBreakException {
        final TipiComponent me = this;
        // final Thread currentThread = Thread.currentThread();
        // final boolean amIEventThread =
        // SwingUtilities.isEventDispatchThread();
        super.performComponentMethod(name, compMeth, event);
        if (name.equals("show")) {
            constructStandardDialog();
        }
        // }
        // });
        if (name.equals("hide")) {
            if (myWindow != null) {
            	myWindow.setVisible(false);
            	System.err.println("HIDING DIALOG!!!!!!!!!!!!!!!!!");
            }
        }
        if (name.equals("dispose")) {
        	System.err.println("DISPOSING DIALOG!!!!!!!!!!!!!!!!!");
              if (myWindow != null) {
            	myWindow.setVisible(false);
            	myWindow = null;
            }
            myContext.disposeTipiComponent(me);
            disposed = true;
        }
    }

    public void reUse() {
    }
}

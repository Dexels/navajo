package com.dexels.navajo.tipi.components.echoimpl;

import nextapp.echo2.app.Color;
import nextapp.echo2.app.Component;
import nextapp.echo2.app.ContentPane;
import nextapp.echo2.app.Extent;
import nextapp.echo2.app.LayoutData;
import nextapp.echo2.app.Style;
import nextapp.echo2.app.Window;
import nextapp.echo2.app.WindowPane;
import nextapp.echo2.app.event.WindowPaneEvent;
import nextapp.echo2.app.event.WindowPaneListener;

import com.dexels.navajo.echoclient.components.Styles;
import com.dexels.navajo.tipi.TipiBreakException;
import com.dexels.navajo.tipi.TipiComponent;
import com.dexels.navajo.tipi.TipiComponentMethod;
import com.dexels.navajo.tipi.TipiException;
import com.dexels.navajo.tipi.TipiHelper;
import com.dexels.navajo.tipi.components.echoimpl.helpers.EchoTipiHelper;
import com.dexels.navajo.tipi.internal.TipiEvent;

import echopointng.ContentPaneEx;
import echopointng.LightBox;

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
	private static final long serialVersionUID = 686157003404928933L;


    private WindowPane myWindow = null;

    private boolean modal = false;


    private boolean showing = false;

    private String title = "";
 
    int x = 0, y = 0, w = 0, h = 0;

    // private Rectangle myBounds = new Rectangle(0, 0, 0, 0);
    private boolean closable = false;

    private boolean resizable = true;
    private ContentPane innerContainer;


	private String myTitle;

	private boolean movable;

	private ContentPaneEx paneEx;

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

            paneEx = new ContentPaneEx();
    		final LightBox lightBox = new LightBox();
            lightBox.setHidden(true);
            paneEx.add(lightBox);

            myWindow.addWindowPaneListener(new WindowPaneListener() {
				private static final long serialVersionUID = 4646592879205026448L;

				public void windowPaneClosing(WindowPaneEvent arg0) {
                    myWindow_internalFrameClosed(arg0);
                    lightBox.hide();
                }
            });
            
            
            

            
            return paneEx;
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
//        WindowPane d = (WindowPane) e.getSource();
        try {
            performTipiEvent("onWindowClosed", null, true);
        } catch (TipiException ex) {
            ex.printStackTrace();
        }
        myContext.disposeTipiComponent(this);
    }

    protected void createWindowListener(WindowPane d) {
        d.setDefaultCloseOperation(WindowPane.DO_NOTHING_ON_CLOSE);
        d.addWindowPaneListener(new WindowPaneListener() {
			private static final long serialVersionUID = -6015877461943700892L;

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
//        if (name.equals("decorated")) {
//            decorated = ((Boolean) object).booleanValue();
//            return;
//        }
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
        System.err.println("DISPOSED DIALOG!");
        myWindow = null;
   }

    private final void constructStandardDialog() {
        TipiScreen s = (TipiScreen) getContext().getDefaultTopLevel();
//        ModalDimmer m = new ModalDimmer();
        final Window win = (Window) s.getTopLevel();
        
  //        win.getContent().add(lb);
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
        

        myWindow.setVisible(true);
    }
//    public void processStyles() {
//        super.processStyles();
//        
//        String s = getStyle("headerheight");
//        if (s!=null) {
//            headerheight = Integer.parseInt(s);
//        }
//        s = getStyle("leftheaderinset");
//        if (s!=null) {
//            leftheaderinset = Integer.parseInt(s);
//        }
//        s = getStyle("topheaderinset");
//        if (s!=null) {
//            topheaderinset = Integer.parseInt(s);
//        }
//        s = getStyle("rightheaderinset");
//        if (s!=null) {
//            rightheaderinset = Integer.parseInt(s);
//        }
//        s = getStyle("bottomheaderinset");
//        if (s!=null) {
//            bottomheaderinset = Integer.parseInt(s);
//        }
//        
//        // headerheight leftheaderinset topheaderinset rightheaderinset bottomheaderinset
//        
//        // headerfont headerforeground headerbackground
//
//        
//        Color c = ColorParser.parseColor(getStyle("foreground"));
//        if (c!=null) {
//            headerforeground = c;
//        }
//        c = ColorParser.parseColor(getStyle("background"));
//        if (c!=null) {
//            headerbackground = c;
//        }
//        s = getStyle("headerfont");
//        Font f= FontParser.parseFont(s);
//        if (s!=null) {
//            headerfont = f;
//        }
//    }
    
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
        }
    }

    public void reUse() {
    }
}

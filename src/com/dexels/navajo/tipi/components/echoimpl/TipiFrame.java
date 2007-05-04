package com.dexels.navajo.tipi.components.echoimpl;

import tucana.echo2.app.ModalDimmer;
import nextapp.echo2.app.*;
import nextapp.echo2.app.layout.GridLayoutData;
import nextapp.echo2.extras.app.MenuBarPane;
import nextapp.echo2.extras.app.menu.DefaultMenuModel;
import nextapp.echo2.extras.app.menu.DefaultOptionModel;

import com.dexels.navajo.tipi.components.echoimpl.impl.TipiLayoutManager;
import com.dexels.navajo.tipi.components.echoimpl.impl.layout.EchoLayoutImpl;
import com.dexels.navajo.tipi.components.echoimpl.parsers.*;

import echopointng.ContainerEx;
import echopointng.MenuBar;
import echopointng.able.Positionable;
import echopointng.able.Sizeable;

/**
 * <p>
 * Title:
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


public class TipiFrame extends TipiEchoDataComponentImpl {

//    private ContentPane myContentPane = new ContentPane();

    private Window myWindow;

    // private ContainerEx innerContainer;

    private ContentPane contentPane;

	private SplitPane mySplit;

    private ContentPane realContent;

	private ContentPane menuPane;
//
//	private ContainerEx topPlaceHolder;
//
//	private ContainerEx leftPlaceHolder;

//	private Grid myWindowGrid;
	private ModalDimmer dimmer;

    public TipiFrame() {
		dimmer = new ModalDimmer();
		dimmer.setDimOpacity((float)0.5);
		dimmer.setDimAnimated(false);
		dimmer.setVisible(false);
   }

    public Window getWindow() {

    	return myWindow;
    }
//    public void setDimmer(Component w) {
//    	// Watch this.
//	     w.add(dimmer);
//	}
	
//	public void stopDimmer() {
//		if(dimmer==null) {
//			return;
//		}
//    	// Watch this.
//		final Window w = (Window) getTopLevel();
//        w.getContent().remove(dimmer);
//        dimmer=null;
//	}
	
    public Object createContainer() {
        myWindow = new Window();
        contentPane = new ContentPane();
        contentPane.setInsets(new Insets(10,10,10,10));
        myWindow.setContent(contentPane);
        mySplit = new SplitPane(SplitPane.ORIENTATION_VERTICAL_TOP_BOTTOM);
        contentPane.add(mySplit);
        menuPane = new ContentPane();
        realContent = new ContentPane();
        realContent.add(dimmer);
//        realContent.setBackground(new Color(255,255,0));
//        mySplit.setBackground(new Color(200,100,0));
        mySplit.add(menuPane); 
        mySplit.add(realContent);
        mySplit.setSeparatorHeight(new Extent(0,Extent.PX));
        mySplit.setSeparatorPosition(new Extent(0,Extent.PX));

        return myWindow;

    }

    public void processStyles() {
        String x =  getStyle("xindent");
        if (x!=null) {
            int xoff = Integer.parseInt(x);
//            myWindowGrid.setColumnWidth(0,new Extent(xoff, Extent.PX));
//            leftPlaceHolder.setWidth(new Extent(xoff, Extent.PX));
//            realContent.setLeft(new Extent(xoff,Extent.PX));
            return;
        }
        String y =  getStyle("yindent");
        if (y!=null) {
            int yoff = Integer.parseInt(y);
//            myWindowGrid.setRowHeight(0,new Extent(yoff, Extent.PX));
//            topPlaceHolder.setHeight(new Extent(yoff, Extent.PX));
//           realContent.setTop(new Extent(yoff,Extent.PX));
            return;
        }

        String widthS =  getStyle("width");
        if (widthS!=null) {
            int width = Integer.parseInt(widthS);
//            myWindowGrid.setColumnWidth(1,new Extent(width, Extent.PX));
//            realContent.setWidth(new Extent(width, Extent.PX));
            //       realContent.setLeft(new Extent(xoff,Extent.PX));
            return;
        }
        String heightS =  getStyle("height");
        if (heightS!=null) {
            int height = Integer.parseInt(heightS);
//            myWindowGrid.setRowHeight(1,new Extent(height, Extent.PX));
//            realContent.setHeight(new Extent(height, Extent.PX));
                 //       realContent.setLeft(new Extent(xoff,Extent.PX));
            return;
        }
        
        super.processStyles();

    }    
    
    
//    public void setContainerLayout(Object layout) {
//        if (layout instanceof Component) {
//        	System.err.println("Deprecated layout usage IN TipiEchoDataComponent");
//            layoutComponent = (Component) layout;
//            if (layoutComponent instanceof Sizeable) {
//                ((Sizeable) layoutComponent).setWidth(new Extent(100, Extent.PERCENT));
//                ((Sizeable) layoutComponent).setHeight(new Extent(100, Extent.PERCENT));
//            }
//            realContent.add(layoutComponent);
//
//        } else {
//            System.err.println("*********************\nStrange layout found!\n*********************");
//        }
////    	realContent.add(layout)
//    }

    
    
    /**
     * setComponentValue
     * 
     * @param name
     *            String
     * @param object
     *            Object
     * @todo Implement this
     *       com.dexels.navajo.tipi.components.core.TipiComponentImpl method
     */
    protected void setComponentValue(String name, Object object) {
        if ("title".equals(name)) {
            myWindow.setTitle("" + object);
        }
        if ("background".equals(name)) {
            if (object instanceof Color) {
                contentPane.setBackground((Color) object);
//                if (realContent!=null) {
//                    realContent.setBackground((Color) object);
//				}
                myWindow.setBackground((Color) object);
//                topPlaceHolder.setBackground((Color) object);
//                leftPlaceHolder.setBackground((Color) object);
                         }
        }
        super.setComponentValue(name, object);

    }

    
    
//    public void setContainerLayout(Object layout) {
//    	System.err.println("IN LAYOUT: CLASS: "+layout.getClass());
//    	if(layout==null) {
//    		layoutComponent = null;
//    	}
//    	if (layout instanceof EchoLayoutImpl) {
//    		System.err.println("Setting layout");
//                layoutComponent = (EchoLayoutImpl) layout;
//    	}
//    }
    
    public Component getInnerComponent() {
    	return realContent;
    }
    
    
    public void addToContainer(Object c, Object constraints) {
    	
    	
    	System.err.println("\n********************************\nAttempting to add component: "+c.getClass()+" to: "+getClass()+"\n********************************\n");
        if (c instanceof nextapp.echo2.extras.app.MenuBarPane) {
        	MenuBarPane m = (MenuBarPane) c;

        	System.err.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
        	mySplit.setSeparatorPosition(new Extent(25,Extent.PX));
        	menuPane.add(m); 
        } else {
            Component child = (Component) c;
            Component cc = realContent;
            if (child instanceof WindowPane) {
            	System.err.println("Window detected");
            	addWindowPane((WindowPane)child);
            } else {
                 if(layoutComponent!=null) {
                 	// do layoutstuff
                	 System.err.println("LAYOUT DETECTED ON FRAME!!!!");
                 	layoutComponent.setParentComponent(this);
                 	layoutComponent.addChildComponent(child, constraints);
                 } else {
                     cc.add(child);
                         	 
                 }
               if (constraints != null && constraints instanceof LayoutData) {
                    child.setLayoutData((LayoutData) constraints);
                }
                if (getLayout() != null) {
                    getLayout().childAdded(c);
                }
            }
        }
    }

	private void addWindowPane(WindowPane child) {
		WindowPane sp = (WindowPane)child;
		System.err.println("Windowpane detected.");
		System.err.println("is viz: "+sp.isVisible()+" w: "+sp.getWidth()+" h: "+sp.getHeight());
		TipiScreen s = (TipiScreen) getContext().getDefaultTopLevel();
		// Watch this.
		final Window w = (Window) s.getTopLevel();
		if(w==null) {
			System.err.println("oh dear, no toplevel found");
		}
		w.getContent().add(child);
	}

    public void removeFromContainer(Object c) {
    
        	realContent.remove((Component) c);

    }

    protected Object getComponentValue(String name) {
        if ("title".equals(name)) {
            return myWindow.getTitle();
        }
        return super.getComponentValue(name);
    }

}

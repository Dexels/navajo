package com.dexels.navajo.tipi.components.echoimpl;

import nextapp.echo2.app.*;
import nextapp.echo2.app.layout.GridLayoutData;

import com.dexels.navajo.tipi.components.echoimpl.impl.TipiLayoutManager;
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

    private ContainerEx realContent;

	private ContainerEx menuPane;
//
//	private ContainerEx topPlaceHolder;
//
//	private ContainerEx leftPlaceHolder;

//	private Grid myWindowGrid;

    public TipiFrame() {
    }

    public Window getWindow() {

    	return myWindow;
    }

    public Object createContainer() {
        myWindow = new Window();
        contentPane = new ContentPane();
        contentPane.setInsets(new Insets(10,10,10,10));
        myWindow.setContent(contentPane);
        mySplit = new SplitPane(SplitPane.ORIENTATION_VERTICAL);
        mySplit.setSeparatorHeight(new Extent(0,Extent.PX));
        mySplit.setSeparatorPosition(new Extent(35,Extent.PX));
        contentPane.add(mySplit);
        menuPane = new ContainerEx();
        realContent = new ContainerEx();
        realContent.setzIndex(Integer.MIN_VALUE);
        mySplit.add(menuPane); 
        menuPane.setzIndex(Integer.MAX_VALUE);
        
        mySplit.add(realContent);
        
        //        contentPane.add(realContent);
//        actualContent = new ContainerEx();
//        realContent.add(actualContent);
//        actualContent.setBackground(new Color(240,230,220));
//        actualContent.setTop(new Extent(20, Extent.PX));

        //        realContent = new ContainerEx();
//        myWindowGrid = new Grid(3);
//		topPlaceHolder = new ContainerEx();
//		myWindowGrid.add(topPlaceHolder);
//        GridLayoutData gd = new GridLayoutData();
//        gd.setColumnSpan(3);
//        topPlaceHolder.setLayoutData(gd);
//        leftPlaceHolder = new ContainerEx();
//        myWindowGrid.add(leftPlaceHolder);
//        GridLayoutData gd2 = new GridLayoutData();
//        leftPlaceHolder.setLayoutData(gd2);
//        myWindowGrid.setRowHeight(0, new Extent(15,Extent.PX));
//        myWindowGrid.setColumnWidth(0, new Extent(10,Extent.PX));
//        myWindowGrid.setColumnWidth(1, new Extent(780,Extent.PX));
//        myWindowGrid.setRowHeight(1, new Extent(560 ,Extent.PX));
//        myWindowGrid.add(realContent);
//        contentPane.add(realContent);
//         contentPane.add(myWindowGrid);
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
    
    
    public void setContainerLayout(Object layout) {
        if (layout instanceof Component) {
        	System.err.println("Deprecated layout usage IN TipiEchoDataComponent");
            layoutComponent = (Component) layout;
            if (layoutComponent instanceof Sizeable) {
                ((Sizeable) layoutComponent).setWidth(new Extent(100, Extent.PERCENT));
                ((Sizeable) layoutComponent).setHeight(new Extent(100, Extent.PERCENT));
            }
            realContent.add(layoutComponent);

        } else {
            System.err.println("*********************\nStrange layout found!\n*********************");
        }
//    	realContent.add(layout)
    }

    
    
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

    public void addToContainer(Object c, Object constraints) {
    	
    	
    	System.err.println("Attempting to add component: "+c.getClass());
        if (c instanceof MenuBar) {
            MenuBar m = (MenuBar) c;
//            m.setPosition(Positionable.ABSOLUTE);
//            m.setTop(new Extent(0, Extent.PX));
//            m.setWidth(new Extent(97, Extent.PERCENT));
//            m.setHeight(new Extent(20, Extent.PX));
//            mySplit.setSeparatorPosition(new Extent(30,Extent.PX));
//            mySplit.setSeparatorHeight(new Extent(5,Extent.PX));
            menuPane.add(m);
           
//            actualContent.setPosition(Positionable.ABSOLUTE);

//            actualContent.setTop(new Extent(20, Extent.PX));
//            actualContent.setHeight(new Extent(97, Extent.PERCENT));
//            actualContent.setWidth(new Extent(97, Extent.PERCENT));
            
            // MEnu geslooopt. kijk ik later naar
            // innerContainer.add(m);
            // contentPane.setTop(new Extent(20, Extent.PX));
        } else {
//        	if(contentPane.getComponentCount()>0) {
//        		System.err.println("CAN NOT ADD MORE COMPONENTS TO A FRAME!");
//        		System.err.println(": "+c.getClass());
//        		return;
//        	}
        	
        	
           
            Component child = (Component) c;
            Component cc = realContent;
            if (layoutComponent != null) {
                cc = layoutComponent;
            }
            if (child instanceof WindowPane) {
                TipiScreen s = (TipiScreen) getContext().getDefaultTopLevel();
                // Watch this.
                final Window w = (Window) s.getTopLevel();
               cc.add(child);
            } else {
                 if(cc.getComponentCount()>0) {
                	return;
                }
                 cc.add(child);
               if (constraints != null && constraints instanceof LayoutData) {
                    child.setLayoutData((LayoutData) constraints);
                }
                if (getLayout() != null) {
                    getLayout().childAdded(c);
                }
                
            }
        	
//            Component child = (Component) c;
//            realContent.add(child);
//            realContent.add(child);
//            conten
//            if (constraints != null && constraints instanceof LayoutData) {
//                child.setLayoutData((LayoutData) constraints);
//            }

            
            
            
            
        }
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

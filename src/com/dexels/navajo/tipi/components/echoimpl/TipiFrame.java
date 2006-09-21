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

public class TipiFrame extends TipiEchoDataComponentImpl {

    private ContentPane myContentPane = new ContentPane();

    private Window myWindow;

    // private ContainerEx innerContainer;

    private ContentPane contentPane;

    private ContainerEx realContent;
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
                if (realContent!=null) {
                    realContent.setBackground((Color) object);
				}
                myWindow.setBackground((Color) object);
//                topPlaceHolder.setBackground((Color) object);
//                leftPlaceHolder.setBackground((Color) object);
                         }
        }
        super.setComponentValue(name, object);

    }

    public void addToContainer(Object c, Object constraints) {
        if (c instanceof MenuBar) {
            MenuBar m = (MenuBar) c;
            m.setPosition(Positionable.ABSOLUTE);
            m.setTop(new Extent(0, Extent.PX));
            m.setWidth(new Extent(100, Extent.PERCENT));
            m.setHeight(new Extent(20, Extent.PX));
            // MEnu geslooopt. kijk ik later naar
            // innerContainer.add(m);
            // contentPane.setTop(new Extent(20, Extent.PX));
        } else {
            Component child = (Component) c;
//            realContent.add(child);
            contentPane.add(child);
            if (constraints != null && constraints instanceof LayoutData) {
                child.setLayoutData((LayoutData) constraints);
            }

        }
    }

    public void removeFromContainer(Object c) {
        Component cc = (Component) getContainer();
        Component child = (Component) c;
        if (c instanceof MenuBar) {
            // MEnu geslooopt. kijk ik later naar
            // innerContainer.remove((Component) c);

        } else {
            contentPane.remove((Component) c);

        }
    }

    protected Object getComponentValue(String name) {
        if ("title".equals(name)) {
            return myWindow.getTitle();
        }
        return super.getComponentValue(name);
    }

}

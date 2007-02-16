/*
 * Created on Jul 10, 2006
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.dexels.navajo.tipi.components.echoimpl;

//import echopointng.*;
import nextapp.echo2.app.*;
import nextapp.echo2.app.event.*;
import nextapp.echo2.app.table.*;
import nextapp.echo2.app.text.*;
import nextapp.echo2.app.layout.*;

public class BorderLayout extends SplitPane {

   public static final String NORTH  = "0";
   public static final String SOUTH  = "1";
   public static final String EAST   = "2";
   public static final String WEST   = "3";
   public static final String CENTER = "4";

   public static final int iNORTH  = 0;
   public static final int iSOUTH  = 1;
   public static final int iEAST   = 2;
   public static final int iWEST   = 3;
   public static final int iCENTER = 4;

   Component northC, southC,centerC, westC, eastC;
 
   public BorderLayout() {
       super(SplitPane.ORIENTATION_VERTICAL, new Extent(80, Extent.PX));
       ContentPane north, south, east, west, center;

       setStyleName("DefaultResizable");
       
       SplitPaneLayoutData splitPaneLayoutData;
       
       splitPaneLayoutData = new SplitPaneLayoutData();
       splitPaneLayoutData.setInsets(new Insets(10));
       add(north = new ContentPane());
       north.add(northC = new Column());
       add(new Label(), BorderLayout.NORTH);

       SplitPane splitPaneNorth = new SplitPane(SplitPane.ORIENTATION_HORIZONTAL_LEADING_TRAILING,
                                                new Extent(150));
       splitPaneNorth.setStyleName("DefaultResizable");
       add(splitPaneNorth);
       
       splitPaneLayoutData = new SplitPaneLayoutData();
       splitPaneLayoutData.setInsets(new Insets(10));
       splitPaneNorth.add(west = new ContentPane());
       west.add(westC = new Row());
       add(new Label(), BorderLayout.WEST);

       SplitPane splitPaneWest = new SplitPane(SplitPane.ORIENTATION_VERTICAL_BOTTOM_TOP,
                                               new Extent(150));
       splitPaneWest.setStyleName("DefaultResizable");
       splitPaneNorth.add(splitPaneWest);
       
       splitPaneLayoutData = new SplitPaneLayoutData();
       splitPaneLayoutData.setInsets(new Insets(10));
       splitPaneWest.add(south = new ContentPane());
       south.add(southC = new Column());
       add(new Label(), BorderLayout.SOUTH);

       SplitPane splitPaneSouth = new SplitPane(SplitPane.ORIENTATION_HORIZONTAL_TRAILING_LEADING,
                                                new Extent(150));
       splitPaneSouth.setStyleName("DefaultResizable");
       splitPaneWest.add(splitPaneSouth);
       
       splitPaneLayoutData = new SplitPaneLayoutData();
       splitPaneLayoutData.setInsets(new Insets(10));
       splitPaneSouth.add(east = new ContentPane());
       east.add(eastC = new Row());
       add(new Label(), BorderLayout.EAST);

       splitPaneSouth.add(center = new ContentPane());
       center.add(centerC = new ContentPane());
       add(new Label(), BorderLayout.CENTER);
   }

   public void add(Component c, String pos) {
       switch (Integer.parseInt(pos)) {
       case iNORTH :
           northC.removeAll();
           northC.add(c);
           break;
       case iSOUTH :
           southC.removeAll();
           southC.add(c);
           break;
       case iEAST :
           eastC.removeAll();
           eastC.add(c);
           break;
       case iWEST :
           westC.removeAll();
           westC.add(c);
           break;
       case iCENTER :
           centerC.removeAll();
           centerC.add(c);
           break;
       }
   }

   public void remove(Component c) {
       // don't care which pane it's in, try remove it from all of them
       northC.remove(c);
       southC.remove(c);
       eastC.remove(c);
       westC.remove(c);
       centerC.remove(c);
   }
}
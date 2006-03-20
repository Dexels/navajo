/*
 * Created on Feb 16, 2006
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.dexels.navajo.tipi.components.echoimpl;


import java.beans.*;

import sun.security.krb5.internal.*;

import nextapp.echo2.app.*;

import com.dexels.navajo.document.*;
import com.dexels.navajo.tipi.*;
import com.dexels.navajo.tipi.components.echoimpl.helpers.*;

import echopointng.*;
import echopointng.tabbedpane.*;

public class TipiTabbedQuestionList extends TipiBaseQuestionList {
    private Component lastSelectedTab = null;

    private DefaultTabModel defaultTabModel = null;

    private TabbedPane myTabbedPane;

    protected Object getGroupConstraints(Message groupMessage) {
        // TODO Auto-generated method stub
        Property name = groupMessage.getProperty("Name");
        if (name!=null) {
            if (name.getValue()==null) {
                return "Unknown tab";
            }
        } else {
            return name.getValue();
            
        }
        return name.getValue();
          }
    
//    public Object createContainer() {
//        final TipiComponent me = this;
//        
//        final TabbedPane jt = new TabbedPane();
//        jt.addPropertyChangeListener(new PropertyChangeListener() {
//   
//        public void propertyChange(PropertyChangeEvent evt) {
//            try {
//                Component childContainer =jt.getComponent(jt.getSelectedIndex());
//                me.performTipiEvent("onTabChanged", null, false);
//                lastSelectedTab = jchildContainer;
//              }
//              catch (TipiException ex) {
//                System.err.println("Exception while switching tabs.");
//                ex.printStackTrace();
//              }
//           
//        }
//        });
//        return jt;
//      }
//    
    public Object createContainer() {
        final TipiComponent me = this;
        myTabbedPane = new TabbedPane();
        defaultTabModel = new DefaultTabModel();
        myTabbedPane.setTabSpacing(0);
//      myTabbedPane.setForeground(new Color(0,0,0));
        myTabbedPane.setModel(defaultTabModel);
        TipiHelper th = new EchoTipiHelper();
        th.initHelper(this);
        addHelper(th);
        myTabbedPane.addPropertyChangeListener(new PropertyChangeListener() {

            public void propertyChange(PropertyChangeEvent evt) {
                // TODO Auto-generated method stub
                // System.err.println("PROPERTY CHANGED!");
                // System.err.println("!WIDTH: "+myTabbedPane.getWidth());
                // System.err.println("!HEIGHT: "+myTabbedPane.getWidth());

            }
            // public void stateChanged(ChangeEvent ce) {
                // try {
                // Component childContainer = jt.getSelectedComponent();
                // me.performTipiEvent("onTabChanged", null, false);
                // lastSelectedTab = jt.getSelectedComponent();
                // }
                // catch (TipiException ex) {
                // System.err.println("Exception while switching tabs.");
                // ex.printStackTrace();
                // }
                // }
            });
        return myTabbedPane;
    }
    
    
    public void addToContainer(Object c, Object constraints) {
        if (c==null) {
            System.err.println("AAAAAAAAAAAAAAAAAAAAAAAP");
        } else {
            System.err.println("Tab izz: "+c.toString());
        }
        defaultTabModel.addTab("" + constraints, (Component) c);
        if (lastSelectedTab == null) {
            lastSelectedTab = (Component) c;
        }
//      System.err.println("WIDTH: " + myTabbedPane.getWidth());
//      System.err.println("HEIGHT: " + myTabbedPane.getWidth());
//      System.err.println("Tab count: "+getChildCount());
//      ButtonEx notSelected =(ButtonEx)defaultTabModel.getTabAt(getChildCount()-1,false);
        ButtonEx selected =(ButtonEx)defaultTabModel.getTabAt(getChildCount()-1,true);
        if (selected!=null) {
            selected.setBorder(new Border(1,new Color(50,50,50),Border.STYLE_GROOVE));
        }

        //      selected.setText("Selected");
//      notSelected.setText("Not selected");
//      System.err.println("Sel: "+selected.toString());
//      System.err.println("NSel: "+notSelected.toString());
//      selected.setForeground(new Color(0,0,0));
//      selected.setBackground(myTabbedPane.getBackground());
//      notSelected.setForeground(new Color(0,0,0));
//      notSelected.setBackground(new Color(150,150,150));
        System.err.println("Setting up model...");
        defaultTabModel.setSelectedBackground(new Color(204,204,204));
        defaultTabModel.setSelectedForeground(new Color(0,0,0));
        defaultTabModel.setBackground(new Color(204,204,204));
        defaultTabModel.setForeground(new Color(153,153,153));
        defaultTabModel.setRolloverBackground(new Color(204,204,204));
        defaultTabModel.setRolloverForeground(new Color(68,68,68));
        defaultTabModel.setSelectedRolloverBackground(new Color(204,204,204));
        defaultTabModel.setSelectedRolloverForeground(new Color(68,68,68));
    }

//    public void removeFromContainer(Object c) {
//        Component cc = (Component) getContainer();
//        Component child = (Component) c;
//        for (int i = 0; i < cc.getComponentCount(); i++) {
//            Component ccc = cc.getComponent(i);
//            if (ccc == c ) {
//                defaultTabModel.releaseTabAt(i);
//                return;
//            }
//        }
//     }

    protected void clearQuestions() {
        // TODO Auto-generated method stub
        
        if (myTabbedPane!=null) {
            while (myTabbedPane.getComponentCount()>0) {
                myTabbedPane.remove(0);
            }
        }
        
        while (defaultTabModel.size()>0) {
            defaultTabModel.removeTabAt(0);
        }
    }

}

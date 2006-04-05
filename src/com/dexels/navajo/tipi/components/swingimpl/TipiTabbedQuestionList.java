/*
 * Created on Feb 16, 2006
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.dexels.navajo.tipi.components.swingimpl;

import java.awt.*;
import java.lang.reflect.*;

import javax.swing.*;
import javax.swing.event.*;

import com.dexels.navajo.document.*;
import com.dexels.navajo.tipi.*;
import com.dexels.navajo.tipi.components.swingimpl.swing.*;

public class TipiTabbedQuestionList extends TipiBaseQuestionList {
    private Component lastSelectedTab = null;
    private JTabbedPane tabbedPane;

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
    
    public Object createContainer() {
        final TipiComponent me = this;
        final JTabbedPane jt = new JTabbedPane();
//        TipiHelper th = new TipiSwingHelper();
//        th.initHelper(this);
//        addHelper(th);
        jt.addChangeListener(new ChangeListener() {
          public void stateChanged(ChangeEvent ce) {
            try {
              Component childContainer = jt.getSelectedComponent();
              me.performTipiEvent("onTabChanged", null, false);
              lastSelectedTab = jt.getSelectedComponent();
            }
            catch (TipiException ex) {
              System.err.println("Exception while switching tabs.");
              ex.printStackTrace();
            }
          }
        });
        return jt;
      }

    public void addToContainer(final Object c, final Object constraints) {
//        System.err.println("Adding to TipiTabbedQuestionList container:   "+c+" constraints: "+constraints);
        runSyncInEventThread(new Runnable(){
            public void run() {
                tabbedPane = (JTabbedPane) getContainer();
                //                pane.addTab( (String) constraints, new JButton("AAAP"));
                tabbedPane.addTab( (String) constraints, (Component) c);
//                tabbedPane.setIconAt(tabbedPane.getTabCount()-1,tabbedPane.getTabCount()%2==0?new ImageIcon(getContext().getResourceURL("com/dexels/navajo/tipi/components/swingimpl/swing/ok.gif")):new ImageIcon(getContext().getResourceURL("com/dexels/navajo/tipi/components/swingimpl/swing/cancel.gif")));
          //        pane.setEnabledAt(pane.indexOfComponent( (Component) c), ( (Component) c).isEnabled());
                if (lastSelectedTab==null) {
                  lastSelectedTab = (Component)c;
                }
            }});
       }

    public void removeFromContainer(Object c) {
        // TODO Auto-generated method stub
        super.removeFromContainer(c);
    }
    public void setGroupValid(boolean valid, TipiBaseQuestionGroup group) {
        super.setGroupValid(valid, group);
        int i = myGroups.indexOf(group);
        if (i<0) {
            System.err.println("Sh!34#@$!");
        }
        tabbedPane.setIconAt(i,valid?new ImageIcon(getContext().getResourceURL("com/dexels/navajo/tipi/components/swingimpl/swing/ok.gif")):new ImageIcon(getContext().getResourceURL("com/dexels/navajo/tipi/components/swingimpl/swing/cancel.gif")));

    }

}

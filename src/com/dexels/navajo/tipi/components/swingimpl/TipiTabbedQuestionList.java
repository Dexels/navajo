/*
 * Created on Feb 16, 2006
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.dexels.navajo.tipi.components.swingimpl;

import java.awt.*;

import javax.swing.*;
import javax.swing.event.*;

import com.dexels.navajo.document.*;
import com.dexels.navajo.tipi.*;
import com.dexels.navajo.tipi.components.swingimpl.swing.*;

public class TipiTabbedQuestionList extends TipiBaseQuestionList {
    private Component lastSelectedTab = null;

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
        TipiHelper th = new TipiSwingHelper();
        th.initHelper(this);
        addHelper(th);
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
        System.err.println("Adding to TipiTabbedQuestionList container:   "+c+" constraints: "+constraints);
        runSyncInEventThread(new Runnable(){

            public void run() {
                // TODO Auto-generated method stub
                JTabbedPane pane = (JTabbedPane) getContainer();
//                pane.addTab( (String) constraints, new JButton("AAAP"));
                pane.addTab( (String) constraints, (Component) c);
          //        pane.setEnabledAt(pane.indexOfComponent( (Component) c), ( (Component) c).isEnabled());
                if (lastSelectedTab==null) {
                  lastSelectedTab = (Component)c;
                }
            }});
       }

}

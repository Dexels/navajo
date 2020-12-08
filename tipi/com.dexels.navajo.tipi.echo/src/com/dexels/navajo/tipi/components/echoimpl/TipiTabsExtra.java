/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.tipi.components.echoimpl;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import nextapp.echo2.app.Component;
import nextapp.echo2.app.ContentPane;
import nextapp.echo2.extras.app.TabPane;
import nextapp.echo2.extras.app.layout.TabPaneLayoutData;

import com.dexels.navajo.tipi.TipiComponent;
import com.dexels.navajo.tipi.TipiException;
import com.dexels.navajo.tipi.TipiHelper;
import com.dexels.navajo.tipi.components.echoimpl.helpers.EchoTipiHelper;

public class TipiTabsExtra extends TipiEchoDataComponentImpl {
	private static final long serialVersionUID = -4745609817544745245L;
	
	private final static Logger logger = LoggerFactory
			.getLogger(TipiTabsExtra.class);
	
    private Component lastSelectedTab = null;

    private TabPane myTabbedPane;

    public Object createContainer() {
        final TipiComponent me = this;
        myTabbedPane = new TabPane();
        myTabbedPane.setStyleName("Default");
        
        TipiHelper th = new EchoTipiHelper();
        th.initHelper(this);
        addHelper(th);
//        myTabbedPane.setTabSpacing(0);
//        myTabbedPane.set
        myTabbedPane.addPropertyChangeListener(new PropertyChangeListener() {

            public void propertyChange(PropertyChangeEvent evt) {
            
            /**
             * WARNING: The tabChanged event does not fire consistently	
             */
            	
            	if(evt.getPropertyName().equals(TabPane.ACTIVE_TAB_INDEX_CHANGED_PROPERTY))
				 {
					try {
						me.performTipiEvent("onTabChanged", null, false);
					} catch (TipiException e) {
						logger.error("Error: ",e);
					}
//                lastSelectedTab = jt.getSelectedComponent();
//                lastSelectedTab.doLayout();
				}
            }
            });
        return myTabbedPane;
    }


    


    public void addToContainer(Object c, Object constraints) {
    	ContentPane pp = new ContentPane();
    	pp.add((Component)c);
    	myTabbedPane.add(pp);
    	TabPaneLayoutData tabPaneLayoutData = new TabPaneLayoutData();
    	tabPaneLayoutData.setTitle(""+constraints);
    	pp.setLayoutData(tabPaneLayoutData);
    }

    public void setComponentValue(String name, Object object) {
        super.setComponentValue(name, object);
//        if (name.equals("selected")) {
//            String sel = (String) object;
//            final TipiComponent tc = getTipiComponent(sel);
//            int ii = myTabbedPane.indexOf((Component) (tc.getContainer()));
//            if (ii >= 0) {
//                myTabbedPane.setActiveTabIndex(ii);
//            }
//        }
        if (name.equals("selectedindex")) {
//            final Integer sel = (Integer) object;
//            myTabbedPane.setActiveTabIndex(sel.intValue());
               }
        // if (name.equals("placement")) {
        // final String sel = (String) object;
        // setTabPlacement(sel);
        // }
        /** @todo Override this com.dexels.navajo.tipi.TipiComponent method */
    }



    public Object getComponentValue(String name) {
        /** @todo Override this com.dexels.navajo.tipi.TipiComponent method */
        if (name.equals("selected")) {

            int ic = myTabbedPane.getActiveTabIndex();
            if (ic >= 0) {
                Component c = myTabbedPane.getComponent(ic);
                TipiComponent tc = getChildByContainer(c);
                return tc;
            } else {
                return null;
            }
        }
        if (name.equals("lastselected")) {
            TipiComponent tc = getChildByContainer(lastSelectedTab);
            return tc;
        }
        if (name.equals("selectedindex")) {
            return Integer.valueOf(myTabbedPane.getActiveTabIndex());
        }
        if (name.equals("lastselectedindex")) {
            return Integer.valueOf(getIndexOfTab(lastSelectedTab));
        }

        return super.getComponentValue(name);
    }

    private int getIndexOfTab(Component c) {
        return myTabbedPane.indexOf(c);
    }
}

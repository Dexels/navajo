package com.dexels.navajo.tipi.components.echoimpl;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import nextapp.echo2.app.*;
import nextapp.echo2.extras.app.TabPane;
import nextapp.echo2.extras.app.layout.TabPaneLayoutData;

import com.dexels.navajo.tipi.TipiComponent;
import com.dexels.navajo.tipi.TipiComponentMethod;
import com.dexels.navajo.tipi.TipiException;
import com.dexels.navajo.tipi.TipiHelper;
import com.dexels.navajo.tipi.components.echoimpl.helpers.EchoTipiHelper;
import com.dexels.navajo.tipi.components.echoimpl.parsers.*;
import com.dexels.navajo.tipi.internal.TipiEvent;

//import echopointng.ButtonEx;
//import echopointng.ContainerEx;
//import echopointng.TabbedPane;
//import echopointng.able.Positionable;
//import echopointng.tabbedpane.DefaultTabModel;

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
public class TipiTabsExtra extends TipiEchoDataComponentImpl {
    private ArrayList tipiList = new ArrayList();

    private ArrayList methodList = new ArrayList();

    private Map tipiMap = new HashMap();

    private TipiComponent selectedComponent = null;

    private Component lastSelectedTab = null;

//    private DefaultTabModel defaultTabModel = null;

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
					try {
						me.performTipiEvent("onTabChanged", null, false);
					} catch (TipiException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
//                lastSelectedTab = jt.getSelectedComponent();
//                lastSelectedTab.doLayout();
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
            return new Integer(myTabbedPane.getActiveTabIndex());
        }
        if (name.equals("lastselectedindex")) {
            TipiComponent tc = getChildByContainer(lastSelectedTab);
            return new Integer(getIndexOfTab(lastSelectedTab));
        }

        return super.getComponentValue(name);
    }

    private int getIndexOfTab(Component c) {
        return myTabbedPane.indexOf(c);
    }
}

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

//    public void processStyles() {
//        System.err.println("Processing styles.... "+styleHintMap);
//        super.processStyles();
//        Color c = ColorParser.parseColor(getStyle("foreground"));
//        if (c!=null) {
//            defaultTabModel.setForeground(c);
//        }
//        c = ColorParser.parseColor(getStyle("background"));
//        if (c!=null) {
//            defaultTabModel.setBackground(c);
//        }
//        c = ColorParser.parseColor(getStyle("selectedforeground"));
//        if (c!=null) {
//            defaultTabModel.setSelectedForeground(c);
//        }
//        c = ColorParser.parseColor(getStyle("selectedbackground"));
//        if (c!=null) {
//            System.err.println("Selectedbackground: "+c.toString());
//            defaultTabModel.setSelectedBackground(c);
//        }
//        c = ColorParser.parseColor(getStyle("rolloverbackground"));
//        if (c!=null) {
//            defaultTabModel.setRolloverBackground(c);
//        }
//        c = ColorParser.parseColor(getStyle("rolloverforeground"));
//        if (c!=null) {
//            defaultTabModel.setRolloverForeground(c);
//        }
//        c = ColorParser.parseColor(getStyle("selectedrolloverbackground"));
//        if (c!=null) {
//            defaultTabModel.setSelectedRolloverBackground(c);
//        }
//        c = ColorParser.parseColor(getStyle("selectedrolloverforeground"));
//        if (c!=null) {
//            defaultTabModel.setSelectedRolloverForeground(c);
//        }
//        String s = getStyle("tabspacing");
//        if (s!=null) {
//            int ii = Integer.parseInt(s);
//            myTabbedPane.setTabSpacing(ii);
//        }
//    }
    
    protected void performComponentMethod(String name, TipiComponentMethod compMeth, TipiEvent event) {
        // if (name.equals("enableTab")) {
        // Operand path = compMeth.getEvaluatedParameter("tabname",event);
        // Operand value = compMeth.getEvaluatedParameter("value",event);
        // String tabName = (String)path.value;
        // final boolean enabled = ((Boolean)value.value).booleanValue();
        // final TipiComponent t = getTipiComponent(tabName);
        // if (t != null) {
        // runSyncInEventThread(new Runnable() {
        // public void run() {
        // Container c = (Container) t.getContainer();
        // JTabbedPane p = (JTabbedPane) getContainer();
        // int index = p.indexOfComponent(c);
        // p.setEnabledAt(index, enabled);
        // if (!enabled && p.getSelectedIndex()==index) {
        // switchToAnotherTab();
        // }
        // }
        // });
        // }
        // else {
        // System.err.println("Sorry could not find tab: " + tabName);
        // }
        // }
    }

    // current tab is disabled. Try to switch to the lastselected, otherwise to
    // the first enabled tab.
    // private final void switchToAnotherTab() {
    // TabbedPane p = (TabbedPane) getContainer();
    //    
    // int lastIndex = p.indexOfComponent(lastSelectedTab);
    // if (lastIndex >= 0 && lastIndex < p.getTabCount() &&
    // p.isEnabledAt(lastIndex)) {
    // p.setSelectedIndex(lastIndex);
    // return;
    // }
    // for (int i = 0; i < p.getTabCount(); i++) {
    // if (p.isEnabledAt(i)) {
    // p.setSelectedIndex(i);
    // return;
    // }
    // }
    // }

    public void addToContainer(Object c, Object constraints) {
//        defaultTabModel.addTab("" + constraints, (Component) c);
//        if (lastSelectedTab == null) {
//            lastSelectedTab = (Component) c;
//        }
//        ButtonEx selected = (ButtonEx) defaultTabModel.getTabAt(getChildCount() - 1, true);
//        selected.setBorder(new Border(1, new Color(50, 50, 50), Border.STYLE_GROOVE));
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

    // public void setTabPlacement(String sel) {
    // int placement = -1;
    // if (sel.equals("top")) {
    // placement = JTabbedPane.TOP;
    // }
    // if (sel.equals("bottom")) {
    // placement = JTabbedPane.BOTTOM;
    // }
    // if (sel.equals("left")) {
    // placement = JTabbedPane.LEFT;
    // }
    // if (sel.equals("right")) {
    // placement = JTabbedPane.RIGHT;
    // }
    // ( (JTabbedPane) getContainer()).setTabPlacement(placement);
    // }

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

package com.dexels.navajo.tipi.components.echoimpl;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import nextapp.echo2.app.*;

import com.dexels.navajo.tipi.TipiComponent;
import com.dexels.navajo.tipi.TipiComponentMethod;
import com.dexels.navajo.tipi.TipiHelper;
import com.dexels.navajo.tipi.components.echoimpl.helpers.EchoTipiHelper;
import com.dexels.navajo.tipi.components.echoimpl.parsers.BorderParser;
import com.dexels.navajo.tipi.internal.TipiEvent;

import echopointng.ButtonEx;
import echopointng.TabbedPane;
import echopointng.tabbedpane.DefaultTabModel;

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
public class TipiTabs extends TipiEchoDataComponentImpl {
    private ArrayList tipiList = new ArrayList();

    private ArrayList methodList = new ArrayList();

    private Map tipiMap = new HashMap();

    private TipiComponent selectedComponent = null;

    private Component lastSelectedTab = null;

    private DefaultTabModel defaultTabModel = null;

    private TabbedPane myTabbedPane;

    public Object createContainer() {
        final TipiComponent me = this;
        myTabbedPane = new TabbedPane();
        defaultTabModel = new DefaultTabModel();
        defaultTabModel.setSelectedBackground(new Color(255, 255, 255));
        defaultTabModel.setSelectedForeground(new Color(0, 0, 0));
        defaultTabModel.setForeground(new Color(153, 153, 153));
        defaultTabModel.setRolloverForeground(new Color(68, 68, 68));
        defaultTabModel.setSelectedRolloverForeground(new Color(68, 68, 68));
        defaultTabModel.setSelectedFont(new Font(Font.ARIAL, Font.BOLD, new Extent(10, Extent.PT)));
        defaultTabModel.setFont(new Font(Font.ARIAL, Font.PLAIN, new Extent(10, Extent.PT)));
        defaultTabModel.setRolloverFont(new Font(Font.ARIAL, Font.BOLD, new Extent(10, Extent.PT)));
        defaultTabModel.setBackground(new Color(255, 255, 255));
        defaultTabModel.setRolloverBackground(new Color(255, 255, 255));
        defaultTabModel.setSelectedRolloverBackground(new Color(255, 255, 255));
        myTabbedPane.setModel(defaultTabModel);
        TipiHelper th = new EchoTipiHelper();
        th.initHelper(this);
        addHelper(th);
        myTabbedPane.setTabSpacing(0);
        myTabbedPane.addPropertyChangeListener(new PropertyChangeListener() {

            public void propertyChange(PropertyChangeEvent evt) {
              }
            });
        return myTabbedPane;
    }

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
        defaultTabModel.addTab("" + constraints, (Component) c);
        if (lastSelectedTab == null) {
            lastSelectedTab = (Component) c;
        }
        // System.err.println("WIDTH: " + myTabbedPane.getWidth());
        // System.err.println("HEIGHT: " + myTabbedPane.getWidth());
        // System.err.println("Tab count: "+getChildCount());
        // ButtonEx notSelected
        // =(ButtonEx)defaultTabModel.getTabAt(getChildCount()-1,false);
        ButtonEx selected = (ButtonEx) defaultTabModel.getTabAt(getChildCount() - 1, true);
        selected.setBorder(new Border(1, new Color(50, 50, 50), Border.STYLE_GROOVE));

        // selected.setText("Selected");
        // notSelected.setText("Not selected");
        // System.err.println("Sel: "+selected.toString());
        // System.err.println("NSel: "+notSelected.toString());
        // selected.setForeground(new Color(0,0,0));
        // selected.setBackground(myTabbedPane.getBackground());
        // notSelected.setForeground(new Color(0,0,0));
        // notSelected.setBackground(new Color(150,150,150));
    }

    public void setComponentValue(String name, Object object) {
        super.setComponentValue(name, object);
        if (name.equals("selected")) {
            String sel = (String) object;
            final TipiComponent tc = getTipiComponent(sel);
            int ii = ((TabbedPane) getContainer()).indexOf((Component) (tc.getContainer()));
            if (ii >= 0) {
                ((TabbedPane) getContainer()).setSelectedIndex(ii);
            }
        }
        if (name.equals("selectedindex")) {
            final Integer sel = (Integer) object;
            ((TabbedPane) getContainer()).setSelectedIndex(sel.intValue());
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

            int ic = ((TabbedPane) getContainer()).getSelectedIndex();
            if (ic >= 0) {
                Component c = ((TabbedPane) getContainer()).getComponent(ic);
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
            return new Integer(((TabbedPane) getContainer()).getSelectedIndex());
        }
        if (name.equals("lastselectedindex")) {
            TipiComponent tc = getChildByContainer(lastSelectedTab);
            return new Integer(getIndexOfTab(lastSelectedTab));
        }

        return super.getComponentValue(name);
    }

    private int getIndexOfTab(Component c) {
        TabbedPane pane = (TabbedPane) getContainer();
        return pane.indexOf(c);
    }
}

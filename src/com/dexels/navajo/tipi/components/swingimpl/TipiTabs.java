package com.dexels.navajo.tipi.components.swingimpl;

import java.util.*;
import java.awt.*;
import javax.swing.*;
import com.dexels.navajo.tipi.*;
import com.dexels.navajo.tipi.components.swingimpl.swing.*;
import javax.swing.event.*;
import com.dexels.navajo.tipi.internal.*;
import com.dexels.navajo.parser.*;
import com.dexels.navajo.document.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */
public class TipiTabs extends TipiSwingDataComponentImpl {
  private ArrayList tipiList = new ArrayList();
  private ArrayList methodList = new ArrayList();
  private Map tipiMap = new HashMap();
  private TipiComponent selectedComponent = null;

  private Component lastSelectedTab = null;

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
          me.performTipiEvent("onTabChanged", null, true);
          lastSelectedTab = jt.getSelectedComponent();
        }
        catch (TipiException ex) {
          System.err.println("Exception while switching tabs.");
        }
      }
    });
    return jt;
  }

  protected void performComponentMethod(String name, TipiComponentMethod compMeth, TipiEvent event) {
    if (name.equals("enableTab")) {
      Operand path = compMeth.getEvaluatedParameter("tabname",event);
      Operand value = compMeth.getEvaluatedParameter("value",event);
      String tabName = (String)path.value;
      final boolean enabled = ((Boolean)value.value).booleanValue();
      final TipiComponent t = getTipiComponent(tabName);
      if (t != null) {
        runSyncInEventThread(new Runnable() {
          public void run() {
            Container c = (Container) t.getContainer();
            JTabbedPane p = (JTabbedPane) getContainer();
            int index = p.indexOfComponent(c);
            p.setEnabledAt(index, enabled);
            if (!enabled && p.getSelectedIndex()==index) {
              switchToAnotherTab();
            }
          }
        });
      }
      else {
        System.err.println("Sorry could not find tab: " + tabName);
      }
    }
  }

  // current tab is disabled. Try to switch to the lastselected, otherwise to the first enabled tab.
  private void switchToAnotherTab() {
    JTabbedPane p = (JTabbedPane) getContainer();
    int lastIndex = p.indexOfComponent(lastSelectedTab);
    if (lastIndex >= 0 && lastIndex < p.getTabCount() && p.isEnabledAt(lastIndex)) {
      p.setSelectedIndex(lastIndex);
      return;
    }
    for (int i = 0; i < p.getTabCount(); i++) {
      if (p.isEnabledAt(i)) {
        p.setSelectedIndex(i);
        return;
      }
    }
  }

  public void addToContainer(Object c, Object constraints) {
    ( (JTabbedPane) getContainer()).addTab( (String) constraints, (Component) c);
    JTabbedPane pane = (JTabbedPane) getContainer();
    pane.setEnabledAt(pane.indexOfComponent( (Component) c), ( (Component) c).isEnabled());
    if (lastSelectedTab==null) {
      lastSelectedTab = (Component)c;
    }
  }

  public void setComponentValue(String name, Object object) {
    super.setComponentValue(name, object);
    if (name.equals("selected")) {
      String sel = (String) object;
      final TipiComponent tc = getTipiComponent(sel);
      runSyncInEventThread(new Runnable() {
        public void run() {
          ( (JTabbedPane) getContainer()).setSelectedComponent( (Component) (tc.getContainer()));
        }
      });
    }
    if (name.equals("selectedindex")) {
      final Integer sel = (Integer) object;
      runSyncInEventThread(new Runnable() {
        public void run() {
          ( (JTabbedPane) getContainer()).setSelectedIndex(sel.intValue());
        }
      });
    }
    if (name.equals("placement")) {
      final String sel = (String) object;
      runSyncInEventThread(new Runnable() {
        public void run() {
          setTabPlacement(sel);
        }
      });
//      ((JTabbedPane)getContainer()).setSelectedComponent(tc.getContainer());
    }
    /**@todo Override this com.dexels.navajo.tipi.TipiComponent method*/
  }

  public void setTabPlacement(String sel) {
    int placement = -1;
    if (sel.equals("top")) {
      placement = JTabbedPane.TOP;
    }
    if (sel.equals("bottom")) {
      placement = JTabbedPane.BOTTOM;
    }
    if (sel.equals("left")) {
      placement = JTabbedPane.LEFT;
    }
    if (sel.equals("right")) {
      placement = JTabbedPane.RIGHT;
    }
    ( (JTabbedPane) getContainer()).setTabPlacement(placement);
  }

  public Object getComponentValue(String name) {
    /**@todo Override this com.dexels.navajo.tipi.TipiComponent method*/
    if (name.equals("selected")) {
      Component c = ((JTabbedPane) getContainer()).getSelectedComponent();
      TipiComponent tc = getChildByContainer(c);
      return tc;
    }
    if (name.equals("lastselected")) {
      TipiComponent tc = getChildByContainer(lastSelectedTab);
      return tc;
    }
    if (name.equals("selectedindex")) {
      return new Integer(((JTabbedPane) getContainer()).getSelectedIndex());
    }
    if (name.equals("lastselectedindex")) {
      TipiComponent tc = getChildByContainer(lastSelectedTab);
      return new Integer(getIndexOfTab(lastSelectedTab));
    }

    return super.getComponentValue(name);
  }

  private int getIndexOfTab(Component c) {
    JTabbedPane pane = (JTabbedPane) getContainer();
    for (int i = 0; i < pane.getComponentCount(); i++) {
      if (pane.getComponent(i)==c) {
        return i;
      }
    }
    return -1;
  }
//  public void load(XMLElement elm, XMLElement instance, TipiContext context) throws com.dexels.navajo.tipi.TipiException {
//    Vector children = elm.getChildren();
//    for (int i = 0; i < children.size(); i++) {
//      XMLElement child = (XMLElement) children.elementAt(i);
//      if (child.getName().equals("tipi-instance")) {
//        String windowName = (String)child.getAttribute("name");
//        String title = (String)child.getAttribute("title");
//        Tipi t = addTipiInstance(context,null,child);
//        JTabbedPane p = (JTabbedPane)getContainer();
//        p.addTab(title, t.getContainer());
//      }
//    }
//
//    super.load(elm,instance, context);
//  }
}

package com.dexels.navajo.tipi.components.swingimpl;

import java.util.*;

import java.awt.*;
import javax.swing.*;

import com.dexels.navajo.tipi.*;
import com.dexels.navajo.tipi.components.swingimpl.swing.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class TipiCardPanel extends TipiSwingDataComponentImpl {
  private final CardLayout myCardLayout = new CardLayout();
  private final ArrayList myComponentList = new ArrayList();
  private final Map myComponentMap = new HashMap();

  private TipiComponent selectedComponent = null;

  public TipiCardPanel() {
  }

  public Object createContainer() {
    JPanel jt = new JPanel();
    jt.setLayout(myCardLayout);
    TipiHelper th = new TipiSwingHelper();
    th.initHelper(this);
    addHelper(th);
     return jt;
  }


  public void addToContainer(Object c, Object constraints) {
    TipiComponent tc = (TipiComponent)myComponentMap.get(c);
    if (tc!=null) {
      ( (Container) getContainer()).add((Component) c,tc.getId() );
    }
  }

  public void setComponentValue(String name, Object object) {
    super.setComponentValue(name, object);
    if (name.equals("selected")) {
      String sel = (String) object;
      selectedComponent = getTipiComponent(sel);
      myCardLayout.show((Container)getContainer(),selectedComponent.getId());
//      ( (JTabbedPane) getContainer()).setSelectedComponent( (Component) (tc.getContainer()));
    }
    if (name.equals("selectedindex")) {
      Integer sel = (Integer) object;
      selectedComponent = (TipiComponent)myComponentList.get(sel.intValue());
      myCardLayout.show((Container)getContainer(),selectedComponent.getId());
    }
  }

  public Object getComponentValue(String name) {
    /**@todo Override this com.dexels.navajo.tipi.TipiComponent method*/
    if (name.equals("selected")) {
      return selectedComponent;
    }
    if (name.equals("selectedindex")) {
      return new Integer(myComponentList.indexOf(selectedComponent));
    }
    return super.getComponentValue(name);
  }

  public void addComponent(TipiComponent c, TipiContext context, Object td) {
    if (c.getContainer()!=null) {
      myComponentList.add(c);
      myComponentMap.put(c.getContainer(),c);
      if (selectedComponent==null) {
        selectedComponent = c;
      }
    }
    super.addComponent(c,context,td);
  }

  public void removeChild(TipiComponent child) {
    myComponentList.remove(child);
    myComponentMap.remove(child);
    if (selectedComponent==child) {
      selectedComponent = null;
    }
    super.removeChild(child);
  }

}

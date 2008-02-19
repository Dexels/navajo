package com.dexels.navajo.tipi.components.swingimpl;

import java.awt.*;

import javax.swing.*;

import com.dexels.navajo.tipi.components.swingimpl.swing.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class TipiSplitPane extends TipiSwingDataComponentImpl {

  public TipiSplitPane() {
  }

  private JPanel left = new JPanel();
  private JPanel right = new JPanel();

  public Object createContainer() {
    left.setLayout(new BorderLayout());
    right.setLayout(new BorderLayout());
    TipiSwingSplitPane sp = new TipiSwingSplitPane(TipiSwingSplitPane.HORIZONTAL_SPLIT,left,right);
    Dimension minimumSize = new Dimension(0, 0);
    left.setMinimumSize(minimumSize);
    right.setMinimumSize(minimumSize);

    sp.setOneTouchExpandable(true);
    sp.setDividerSize(10);
    sp.setDividerLocation(0.5);
    sp.setContinuousLayout(true);
    return sp;
  }

  public void addToContainer(Object c, Object constraints) {
    if(constraints==null) {
    	throw new IllegalArgumentException("Constraint required when adding to a splitpane");
    }
    String constr = constraints.toString();
      Component cc = (Component)c;
    Dimension minimumSize = new Dimension(0, 0);
    if("bottom".equals(constr)){
//      ((JSplitPane) getContainer()).add((Component)c, JSplitPane.BOTTOM);
      ((TipiSwingSplitPane) getContainer()).setStringOrientation("vertical");
      right.add(cc,BorderLayout.CENTER);
    }
    if("right".equals(constr)){
      ((TipiSwingSplitPane) getContainer()).setStringOrientation("horizontal");
//      ((JSplitPane) getContainer()).add((Component)c, JSplitPane.RIGHT);
      right.add(cc,BorderLayout.CENTER);
    }
    if("top".equals(constr)){
      ((TipiSwingSplitPane) getContainer()).setStringOrientation("vertical");
//      ((JSplitPane) getContainer()).add((Component)c, JSplitPane.TOP);
      left.add(cc,BorderLayout.CENTER);
    }
    if("left".equals(constr)){
      ((TipiSwingSplitPane) getContainer()).setStringOrientation("horizontal");
//      ((JSplitPane) getContainer()).add((Component)c, JSplitPane.LEFT);
      left.add(cc,BorderLayout.CENTER);
    }
    left.setMinimumSize(minimumSize);
    right.setMinimumSize(minimumSize);
    left.setPreferredSize(minimumSize);
    right.setPreferredSize(minimumSize);
  }

  public void setComponentValue(String name, Object object) {
    super.setComponentValue(name, object);
    if (name.equals("orientation")) {
      String sel = (String) object;
      if("horizontal".equals(sel)){
        ((TipiSwingSplitPane) getContainer()).setOrientation(JSplitPane.HORIZONTAL_SPLIT);
      }
      if("vertical".equals(sel)){
        ((TipiSwingSplitPane) getContainer()).setOrientation(JSplitPane.VERTICAL_SPLIT);
      }
    }
    if (name.equals("dividerlocation")) {
      int loc = ( (Integer) object).intValue();
      ((TipiSwingSplitPane) getContainer()).setDividerLocation(loc);
    }
    if (name.equals("dividersize")) {
      int size = ( (Integer) object).intValue();
      ((TipiSwingSplitPane) getContainer()).setDividerSize(size);
    }
    if (name.equals("onetouchexpandable")) {
      boolean otex = ( (Boolean) object).booleanValue();
      ((TipiSwingSplitPane) getContainer()).setOneTouchExpandable(otex);
    }

  }

}

package com.dexels.navajo.tipi.components.swingimpl;

import com.dexels.navajo.tipi.components.core.*;
import javax.swing.*;
import java.awt.*;

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

  public Object createContainer() {
    JSplitPane sp = new JSplitPane();

    return sp;
  }

  public void addToContainer(Object c, Object constraints) {
    String constr = constraints.toString();
    if("bottom".equals(constr)){
      ((JSplitPane) getContainer()).add((Component)c, JSplitPane.BOTTOM);
    }
    if("right".equals(constr)){
      ((JSplitPane) getContainer()).add((Component)c, JSplitPane.RIGHT);
    }
    if("top".equals(constr)){
      ((JSplitPane) getContainer()).add((Component)c, JSplitPane.TOP);
    }
    if("left".equals(constr)){
      ((JSplitPane) getContainer()).add((Component)c, JSplitPane.LEFT);
    }
  }

  public void setComponentValue(String name, Object object) {
    super.setComponentValue(name, object);
    if (name.equals("orientation")) {
      String sel = (String) object;
      if("horizontal".equals(sel)){
        ((JSplitPane) getContainer()).setOrientation(JSplitPane.HORIZONTAL_SPLIT);
      }
      if("vertical".equals(sel)){
        ((JSplitPane) getContainer()).setOrientation(JSplitPane.VERTICAL_SPLIT);
      }
    }
    if (name.equals("dividerlocation")) {
      int loc = ( (Integer) object).intValue();
      ((JSplitPane) getContainer()).setDividerLocation(loc);
    }
  }

}
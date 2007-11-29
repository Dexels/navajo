package com.dexels.navajo.tipi.components.swingimpl.layout;

import java.awt.*;
import com.dexels.navajo.tipi.*;
import com.dexels.navajo.tipi.components.core.*;
import com.dexels.navajo.tipi.tipixml.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */
public class TipiFlowLayout
    extends TipiLayoutImpl {
  private FlowLayout myFlow = new FlowLayout();
  public TipiFlowLayout() {
  }

  public void createLayout() {
    FlowLayout layout = new FlowLayout();
    String align = myDefinition.getStringAttribute("alignment");
    if(align.equals("left")) {
        layout.setAlignment(FlowLayout.LEFT);
    }
    if(align.equals("right")) {
        layout.setAlignment(FlowLayout.RIGHT);
    }
    if(align.equals("center")) {
        layout.setAlignment(FlowLayout.CENTER);
    }

    String gap = myDefinition.getStringAttribute("gap");
    if(gap!=null) {
    	int g = Integer.parseInt(gap);
        layout.setHgap(g);
    }
    setLayout(layout);
  }

  public Object parseConstraint(String text, int index) {
    return null;
  }
  
  

protected void setValue(String name, TipiValue tv) {
    throw new UnsupportedOperationException("Not implemented yet. But I should.");
  }
}

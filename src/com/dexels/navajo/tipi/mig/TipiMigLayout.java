package com.dexels.navajo.tipi.mig;

import net.miginfocom.swing.MigLayout;

import com.dexels.navajo.tipi.TipiValue;
import com.dexels.navajo.tipi.components.core.TipiLayoutImpl;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */
public class TipiMigLayout
    extends TipiLayoutImpl {
   public TipiMigLayout() {
  }

  public void createLayout() {
    String align = myDefinition.getStringAttribute("grid");
    MigLayout layout = new MigLayout(align);
    
    setLayout(layout);
  }

  public Object parseConstraint(String text, int index) {
    return text;
  }
  
  

protected void setValue(String name, TipiValue tv) {
    throw new UnsupportedOperationException("Not implemented yet. But I should.");
  }
}

package com.dexels.navajo.tipi.components;

import java.awt.*;
import com.dexels.navajo.tipi.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class TipiTableLayout extends GridBagLayout {

  private int currentRow = 0;
  private int currentColumn = 0;

  public TipiTableLayout() {
  }

  public void addLayoutComponent(Component comp, Object constraints) {
    GridBagConstraints cons = new GridBagConstraints(currentColumn, currentRow, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(2, 2, 2, 2), 0, 0);
    super.addLayoutComponent(comp, cons);
  }

  public void startRow(){

  }

  public void endRow(){
    currentRow++;
    currentColumn = 0;
  }

  public void startColumn(){

  }

  public void endColumn(){
    currentColumn++;
  }


}
package com.dexels.navajo.tipi.components.echoimpl.echo;

import echopoint.layout.GridLayoutManager;
import nextapp.echo.Component;
import echopoint.layout.GridLayoutManager.OutOfBoundsException;
import echopoint.layout.GridLayoutManager.OverlapException;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author Frank Lyaruu
 * @version 1.0
 */

public class GridLayout extends GridLayoutManager {
  public GridLayout() {
    super(1,1);
    setFullWidth(true);
    setFullHeight(true);
  }

  /**
   * addLayoutComponent
   *
   * @param comp Component
   * @throws OutOfBoundsException
   * @throws OverlapException
   * @todo Implement this echopoint.layout.GridLayoutManager method
   */
  public void addLayoutComponent(Component comp) throws OutOfBoundsException,
      OverlapException {
    throw new RuntimeException("Adding components without constraints is not supported in GridLayout");
  }
  public void addLayoutComponent(Component comp, Object constraints) throws OutOfBoundsException, OverlapException {
    CellConstraints cell = (CellConstraints)constraints;
    System.err.println("Adding to GridLAyout: "+constraints.toString());
    if (Math.max(cell.getCol1(),cell.getCol2())>=getWidth()) {
      System.err.println("Increasing width to: "+(cell.getCol2()+1));
      setWidth(Math.max(cell.getCol1(),cell.getCol2())+1);
    }
    if (Math.max(cell.getRow1(),cell.getRow2())>=getHeight()) {
      System.err.println("Increasing height to: "+(cell.getRow2()+1));
      setHeight(Math.max(cell.getRow1(),cell.getRow2())+1);
    }
    System.err.println("Adding: "+getWidth()+","+getHeight());
    super.addLayoutComponent(comp,constraints);
  }

  public CellConstraints createConstraints(int column1, int row1, int column2, int row2) {
    return new CellConstraints(column1,row1,column2,row2);
  }
  public CellConstraints createConstraints(int column1, int row1) {
    return new CellConstraints(column1,row1);
  }
}

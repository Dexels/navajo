package com.dexels.navajo.tipi.components.echoimpl.layout;

import java.io.*;
import java.util.*;

import echopoint.layout.*;
import nextapp.echo.*;

public class EchoGridBagLayout
    extends AbstractLayoutManager {

  public static final int ODD_LEFT = 32;
  public static final int ODD_RIGHT = 64;
  public static final int ODD_CENTER = 128;
  public static final int EVEN_LEFT = 256;
  public static final int EVEN_RIGHT = 512;
  public static final int EVEN_CENTER = 1024;
  public static final int HORZ_ALIGNMENT_MASK = 0x0000001F;

  private ArrayList cells = new ArrayList();
  private int cursorCol = 0;
  private int cursorRow = 0;
  private int height = 100;
  private int width = 100;
  private int cellSpacing = 0;
  private int horizontalAlignment = EchoConstants.LEFT;
  private int verticalAlignment = EchoConstants.TOP;
  private boolean fullWidth = false;
  private boolean fullHeight = false;

  /**
   * An <code>OverlapException</code> exception is thrown
   * if any of the CellConstraints constaints overlap.
   * <p>
   * Note that it is derived from a RuntimeException and hence
   * try/catch blocks are not manadatory.
   */
  public class OverlapException
      extends IllegalStateException
      implements Serializable {
    EchoGridBagConstraints constraint;
    public OverlapException(String msg, EchoGridBagConstraints constraint) {
      super(msg);
      this.constraint = constraint;
    }

    public EchoGridBagConstraints getConstraint() {
      return constraint;
    }
  }

  /**
   * An <code>OutOfBoundsException</code> exception is thrown
   * if any of the CellConstraints constaints are outside
   * the width or height of the GridLayoutManager.
   * <p>
   * Note that it is derived from a RuntimeException and hence
   * try/catch blocks are not manadatory.
   */
  public class OutOfBoundsException
      extends IllegalStateException
      implements Serializable {
    EchoGridBagConstraints constraint;

    public OutOfBoundsException(String msg, EchoGridBagConstraints constraint) {
      super(msg);
      this.constraint = constraint;
    }

    public EchoGridBagConstraints getConstraint() {
      return constraint;
    }

  }

  public EchoGridBagLayout() {
    setHeight(10);
    setWidth(10);
  }

  public EchoGridBagLayout(int width, int height) {
    setWidth(width);
    setHeight(height);
  }

  /**
   * Adds a new cell to the list of cells, in order, top to bottom
   * left to right.
   */
  private void _addInOrder(EchoGridBagConstraints newCell) {
    Iterator it = cells.iterator();
    boolean added = false;
    int index = 0;
    while (it.hasNext()) {
      EchoGridBagConstraints compare = (EchoGridBagConstraints) it.next();

      if ( (compare.getCol1() > newCell.getCol1() && compare.getRow1() >= newCell.getRow1()) || compare.getRow1() > newCell.getRow1()) {
        cells.add(index, newCell);
        added = true;
        break;
      }
      index++;
    }
    if (!added) {
      cells.add(newCell);
    }
  }

  /**
   * Does one cell overlap another?
   */
  private boolean _overlaps(EchoGridBagConstraints cell) {
    for (Iterator it = cells.iterator(); it.hasNext(); ) {
      EchoGridBagConstraints compare = (EchoGridBagConstraints) it.next();
      if (compare.overlaps(cell)) {
        return true;
      }
    }
    return false;
  }

  /**
   * Adds a component to the LayoutManager in the next available cell.
   * <p>
   * The Component will be added at the current cursor position.  If the cell
   * at the current cursor position has a Component it it, the new Component will
   * added at the next available cell, extending the grid down as required.
   * <p>
   * Also note that the cursor is left "in" the cell of the added Component.
   * <p>
   * This method is not intended to be called by any other than
   * an LayoutManageable during Component add().
   * <p>
   *
   */
  public void addLayoutComponent(Component comp) throws OutOfBoundsException, OverlapException {
    throw new RuntimeException("Adding to EchoGridBagLayout without constraints not supported!!!");
  }

  /**
   * Adds a component to the LayoutManager with the specified contraints
   * object.  The contraints object must be an instance of
   * GridCellArea or null.
   *
   * If the contraints Object is null then the Component will be added at the
   * current cursor position.  If the cell at the current cursor position
   * has a Component it it, the new Component will added at the next available
   * cell, extending the grid down as required.
   * <p>
   * This method is not intended to be called by any other than
   * an LayoutManageable during Component add().
   *
   */
  public void addLayoutComponent(Component comp, Object constraints) throws OutOfBoundsException, OverlapException {
    if (! (constraints instanceof EchoGridBagConstraints) && constraints != null) {
      throw new IllegalArgumentException("The GridLayoutManager constraint must be of type CellConstraints, but is of class: " + constraints.getClass());
    }

    EchoGridBagConstraints cell = (EchoGridBagConstraints) constraints;
    if (cell != null) {
      //
      // we have a real cell position.  Be strict with it!
      if (cell.getCol2() >= getWidth() || cell.getRow2() >= getHeight()) {
        System.err.println("width: " + getWidth() + ", height: " + getHeight() + ", col2: " + cell.getCol2() + ", row2: " + cell.getRow2());
        throw new OutOfBoundsException("CellConstraints is out bounds : " + cell + " make sure you specify it in {column,row} order.", cell);
      }
      if (_overlaps(cell)) {
        throw new OverlapException("CellConstraints overlaps other cells " + cell, cell);
      }

      super.addLayoutComponent(comp, cell);
      _addInOrder(cell);
    }
    else {
      addLayoutComponent(comp);
    }
  }

  /**
   * Gets the cellspacing of the GridLayoutManager
   *
   * @return int
   */
  public int getCellSpacing() {
    return cellSpacing;
  }

  /**
   * This returns the Component currently at position {col,row} or null
   * if no component is currently there.
   */
  public Component getComponent(int col, int row) {
    EchoGridBagConstraints cell = new EchoGridBagConstraints(col, row, 1, 1, 0.0, 0.0, 17, 2, new Insets(0, 0, 0, 0), 0, 0);
    for (Iterator it = cells.iterator(); it.hasNext(); ) {
      EchoGridBagConstraints compare = (EchoGridBagConstraints) it.next();
      if (compare.overlaps(cell)) {
        Component c = getComponent(compare);
        return c;
      }
    }
    return null;
  }

  /**
   * This returns the GridCellConstraint currently at position {col,row} or null
   * if no component is currently there.
   */
  public EchoGridBagConstraints getContraints(int col, int row) {
    EchoGridBagConstraints cell = new EchoGridBagConstraints(col, row, 1, 1, 0.0, 0.0, 17, 2, new Insets(0, 0, 0, 0), 0, 0);
    for (Iterator it = cells.iterator(); it.hasNext(); ) {
      EchoGridBagConstraints compare = (EchoGridBagConstraints) it.next();
      if (compare.overlaps(cell)) {
        return compare;
      }
    }
    return null;
  }

  /**
   * Returns the current Column position of the Grid cursor.
   *
   * @return int
   */
  public int getCursorColumn() {
    return cursorCol;
  }

  /**
   * Returns the current Row position of the Grid cursor
   *
   * @return int
   */
  public int getCursorRow() {
    return cursorRow;
  }

  /**
   * Gets the height of the GridLayoutManager
   *
   * @return int
   */
  public int getHeight() {
    return height;
  }

  /**
   * Gets the horizontal alignment of the GridLayoutManager.
   * <p>
   * This can be one of the EchoConstant values :
   * <ul>
   * <li>EchoConstant.LEFT</li>
   * <li>EchoConstant.RIGHT</li>
   * <li>EchoConstant.CENTER</li>
   * </ul>
   * <p>
   * You can also "OR" on the following values to overrride the above
   * <ul>
   * <li>ODD_LEFT</li>
   * <li>ODD_RIGHT</li>
   * <li>ODD_CENTER</li>
   * <li>EVEN_LEFT</li>
   * <li>EVEN_RIGHT</li>
   * <li>EVEN_CENTER</li>
   * </ul>
   * The above will only be applied to their respective "odd" or "even"
   * columns.
   * <p>
   *
   * @return int
   */
  public int getHorizontalAlignment() {
    return horizontalAlignment;
  }

  /**
   * Gets the vertical alignment of the GridLayoutManager.
   * <p>
   * This can be one of the EchoConstant values :
   * <ul>
   * <li>EchoConstant.TOP</li>
   * <li>EchoConstant.MIDDLE</li>
   * <li>EchoConstant.BOTTOM</li>
   * </ul>
   * <p>
   * @return int
   */
  public int getVerticalAlignment() {
    return verticalAlignment;
  }

  /**
   * Sets the height of the GridLayoutManager
   *
   * @return int
   */
  public int getWidth() {
    return width;
  }

  /**
   * Returns TRUE if the <code>GridLayoutManager</code> will fill its
   * parents full height
   *
   * @return boolean
   */
  public boolean isFullHeight() {
    return fullHeight;
  }

  /**
   * Returns TRUE if the <code>GridLayoutManager</code> will fill its
   * parents full width
   *
   * @return boolean
   */
  public boolean isFullWidth() {
    return fullWidth;
  }

  /**
   * Moves the current cursor position down a row and to the first
   * column (col == 0) in the grid.  The height will be expanded to
   * accomodate this change.
   */
  public void newLine() {
    cursorCol = 0;
    cursorRow++;
  }

  /**
   * Removes a component from the GridLayoutManager at the specified position.
   */
  public void removeLayoutComponent(int col, int row) {
    EchoGridBagConstraints cell = new EchoGridBagConstraints(col, row, 1, 1, 0.0, 0.0, 17, 2, new Insets(0, 0, 0, 0), 0, 0);

    for (Iterator it = cells.iterator(); it.hasNext(); ) {
      EchoGridBagConstraints compare = (EchoGridBagConstraints) it.next();
      if (compare.overlaps(cell)) {
        Component comp = getComponent(compare);
        super.removeLayoutComponent(comp);
        it.remove();
      }
    }
  }

  /**
   * Removes a component from the LayoutManager.  This method is called by
   * the containing component when a child component is to be removed.
   */
  public void removeLayoutComponent(Component comp) {
    Object constraint = getContraints(comp);
    for (Iterator it = cells.iterator(); it.hasNext(); ) {
      if (it.next() == constraint) {
        it.remove();
        break;
      }
    }
    super.removeLayoutComponent(comp);
    if (cells.size() == 0) {
      cursorCol = 0;
      cursorRow = 0;
    }
  }

  /**
   * Sets the cell spacing of the GridLayoutManager
   *
   * @param newCellSpacing int
   */
  public void setCellSpacing(int newCellSpacing) {
    cellSpacing = newCellSpacing;
  }

  /**
   * Sets the current Column position of the Grid cursor.
   */
  public void setCursorColumn(int newColumn) {
    cursorCol = newColumn;
  }

  /**
   * Sets the current Row position of the Grid cursor
   *
   */
  public void setCursorRow(int newRow) {
    cursorRow = newRow;
  }

  /**
   * If this is TRUE then the <code>GridLayoutManager</code> will fill its
   * parents full height.
   *
   * @param newFullHeight boolean
   */
  public void setFullHeight(boolean newFullHeight) {
    fullHeight = newFullHeight;
  }

  /**
   * If this is TRUE then the <code>GridLayoutManager</code> will fill its
   * parents full width.
   *
   * @param newFullWidth boolean
   */
  public void setFullWidth(boolean newFullWidth) {
    fullWidth = newFullWidth;
  }

  /**
   * Sets the height of the GridLayoutManager. This will throws an
   * OutOfBoundsException if the newHeight is less than current
   * constraints.
   *
   * @param newHeight int
   */
  public void setHeight(int newHeight) throws OutOfBoundsException {
    if (newHeight < 1) {
      throw new IllegalArgumentException("New height is < 1");
    }

    for (Iterator it = cells.iterator(); it.hasNext(); ) {
      EchoGridBagConstraints cell = (EchoGridBagConstraints) it.next();
      if (cell.getRow2() >= newHeight) {
        throw new OutOfBoundsException("New height " + newHeight + " is smaller than current contraints", cell);
      }
    }
    height = newHeight;
  }

  /**
   * Sets the horizontal alignment of the GridLayoutManager.
   * <p>
   * This can be one of the EchoConstant values :
   * <ul>
   * <li>EchoConstant.LEFT</li>
   * <li>EchoConstant.RIGHT</li>
   * <li>EchoConstant.CENTER</li>
   * </ul>
   * <p>
   * You can also "OR" on the following values to overrride the above
   * <ul>
   * <li>ODD_LEFT</li>
   * <li>ODD_RIGHT</li>
   * <li>ODD_CENTER</li>
   * <li>EVEN_LEFT</li>
   * <li>EVEN_RIGHT</li>
   * <li>EVEN_CENTER</li>
   * </ul>
   * The above will only be applied to their respective "odd" or "even"
   * columns.
   * <p>
   * @param newHorizontalAlignment int
   */
  public void setHorizontalAlignment(int newHorizontalAlignment) {
    horizontalAlignment = newHorizontalAlignment;
  }

  /**
   * Sets the vertical alignment of the GridLayoutManager.
   * <p>
   * This can be one of the EchoConstant values :
   * <ul>
   * <li>EchoConstant.TOP</li>
   * <li>EchoConstant.MIDDLE</li>
   * <li>EchoConstant.BOTTOM</li>
   * </ul>
   * <p>
   * @param newVerticalAlignment int
   */
  public void setVerticalAlignment(int newVerticalAlignment) {
    verticalAlignment = newVerticalAlignment;
  }

  /**
   * Sets the width of the GridLayoutManager.  This will throws an
   * OutOfBoundsException if the newWidth is less than current
   * constraints.
   *
   * @param newWidth int
   */
  public void setWidth(int newWidth) throws OutOfBoundsException {
    if (newWidth < 1) {
      throw new IllegalArgumentException("New width is < 1");
    }

    for (Iterator it = cells.iterator(); it.hasNext(); ) {
      EchoGridBagConstraints cell = (EchoGridBagConstraints) it.next();
      if (cell.getCol2() >= newWidth) {
        throw new OutOfBoundsException("New width " + newWidth + " is smaller than current contraints", cell);
      }
    }
    width = newWidth;
  }

  /**
   * Moves the current cursor position along one column.  If this moves the
   * cursor position past the width of the grid, then the cursor will be
   * moved down a row and set to the first column.
   */
  public void space() {
    cursorCol++;
    if (cursorCol >= width) {
      cursorCol = 0;
      cursorRow++;
    }
  }

  /**
   *
   */
  public String toString() {
    return "w=" + width + " h=" + height + " cC=" + cursorCol + " cR=" + cursorRow + " cells=" + cells;
  }
}

package com.dexels.navajo.tipi.components.echoimpl.layout;

import java.io.*;
import java.util.*;

import nextapp.echo.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */
public class EchoGridBagConstraints
    implements Serializable {
  private String[] myConstraints = new String[14];
  public int gridx, gridy, gridwidth, gridheight, ipadx, ipady, anchor, fill;
  public double weightx, weighty;
  public Insets insets;
  private CellConstraints myCellConstraints;

  public EchoGridBagConstraints() {
  }

  public EchoGridBagConstraints(int x, int y, int w, int h, double wx, double wy, int anchor, int fill, Insets insets, int padx, int pady) {
    gridx = x;
    gridy = y;
    gridwidth = w;
    gridheight = h;
    weightx = wx;
    weighty = wy;
    this.anchor = anchor;
    this.fill = fill;
    this.insets = insets;
    ipadx = padx;
    ipady = pady;
    myCellConstraints = new CellConstraints(gridx, gridy, gridx + (gridwidth - 1), gridy + (gridheight - 1));

  }

  public void parse(String cs) {
//    String cs = elm.getStringAttribute("gridbag");
    StringTokenizer tok = new StringTokenizer(cs, ", ");
    int tokenCount = tok.countTokens();
    if (tokenCount != 14) {
      throw new RuntimeException("Gridbag for: " + cs + " is invalid!");
    }
    else {
      for (int i = 0; i < 14; i++) {
//        int con = new Integer(tok.nextToken()).intValue();
        myConstraints[i] = tok.nextToken();
      }
      gridx = Integer.parseInt(myConstraints[0]) + 1;
      gridy = Integer.parseInt(myConstraints[1]) + 1;
      gridwidth = Integer.parseInt(myConstraints[2]);
      gridheight = Integer.parseInt(myConstraints[3]);
      weightx = Double.parseDouble(myConstraints[4]);
      weighty = Double.parseDouble(myConstraints[5]);
      anchor = Integer.parseInt(myConstraints[6]);
      fill = Integer.parseInt(myConstraints[7]);
      insets = new Insets(Integer.parseInt(myConstraints[9]), Integer.parseInt(myConstraints[8]), Integer.parseInt(myConstraints[11]), Integer.parseInt(myConstraints[10]));
      ipadx = Integer.parseInt(myConstraints[12]);
      ipady = Integer.parseInt(myConstraints[13]);
      myCellConstraints = new CellConstraints(gridx, gridy, gridx + (gridwidth - 1), gridy + (gridheight - 1));
    }
  }

  public EchoGridBagConstraints(String s) {
    parse(s);
  }

  public int getVerticalAlignment() {
    if (anchor == 18 || anchor == 11 || anchor == 12) {
      return EchoConstants.TOP;
    }
    else if (anchor == 16 || anchor == 15 || anchor == 14) {
      return EchoConstants.BOTTOM;
    }
    else {
      return EchoConstants.CENTER;
    }
  }

  public int getHorizontalAlignment() {
    if (anchor == 18 || anchor == 17 || anchor == 16) {
      return EchoConstants.LEFT;
    }
    else if (anchor == 12 || anchor == 13 || anchor == 14) {
      return EchoConstants.RIGHT;
    }
    else {
      return EchoConstants.CENTER;
    }
  }

  public String toString() {
    return "" + gridx + "," + gridy + "," + gridwidth + "," + gridheight + "," + weightx + "," + weighty + "," + anchor + "," + fill + "," + insets.getTop() + "," + insets.getLeft() + "," + insets.getBottom() + "," + insets.getRight() + "," + ipadx + "," + ipady;
  }

  public int getCol1() {
    return myCellConstraints.col1;
  }

  public int getCol2() {
    return myCellConstraints.col2;
  }

  public int getRow1() {
    return myCellConstraints.row1;
  }

  public int getRow2() {
    return myCellConstraints.row2;
  }

  public boolean overlaps(EchoGridBagConstraints cell) {
    return gridx <= (cell.gridx + cell.gridwidth - 1) && gridy <= (cell.gridy + cell.gridheight - 1) && (gridx + gridwidth - 1) >= cell.gridx && (gridy + gridheight - 1) >= cell.gridy;
  }

  /**
   * Returns the current height value of the cell or -1 if its not set
   * @return the current height value of the cell or -1 if its not set
   */
  public int getHeight() {
    return myCellConstraints.height;
  }

  /**
   * Returns the current width value of the cell or -1 if its not set
   * @return the current width value of the cell or -1 if its not set
   */
  public int getWidth() {
    return myCellConstraints.width;
  }

  /**
   * Returns the height untis in action for this cell.
   * @return the height untis in action for this cell.
   */
  public int getHeightUnits() {
    return myCellConstraints.heightUnits;
  }

  /**
   * Returns the width untis in action for this cell.
   * @return the width untis in action for this cell.
   */
  public int getWidthUnits() {
    return myCellConstraints.widthUnits;
  }

  /**
   * Sets the height of the cell
   * @param height - the height of the cell
   */
  public void setHeight(int height) {
    myCellConstraints.setHeight(height);
  }

  /**
   * Sets the height units of the cell
   * @param units - the height units of the cell
   */
  public void setHeightUnits(int units) {
    myCellConstraints.setHeightUnits(units);
  }

  /**
   * Sets the width of the cell
   * @param width the width of the cell
   */
  public void setWidth(int width) {
    myCellConstraints.setWidth(width);
  }

  /**
   * Sets the width units of the cell
   * @param units - the width units of the cell
   */
  public void setWidthUnits(int units) {
    myCellConstraints.setWidthUnits(units);
  }

  public EchoGridBagConstraints setHeightInPixels(int height) {
    myCellConstraints.setHeightInPixels(height);
    return this;
  }

  public EchoGridBagConstraints setHeightInPercent(int height) {
    myCellConstraints.setHeightInPercent(height);
    return this;
  }

  public EchoGridBagConstraints setWidthInPixels(int width) {
    myCellConstraints.setWidthInPixels(width);
    return this;
  }

  public EchoGridBagConstraints setWidthInPercent(int width) {
    myCellConstraints.setWidthInPercent(width);
    return this;
  }

  public static class CellConstraints
      implements Serializable {

    /**
     * A constant used in the <code>setWidthUnits()</code> and
     * <code>setHeightUnits()</code> methods.
     * This value specifies that pixel-based units are used
     * in defining the individual column or row sizes and the
     * overall width and height of the grid.
     */
    public static final int PIXEL_UNITS = 0;

    /**
     * A constant used in the <code>setWidthUnits()</code> and
     * <code>setHeightUnits()</code> methods.
     * This value specifies that percent-based are used in defining both the
     * individual column or row sizes and the overall width or height of the
     * grid.
     */
    public static final int PERCENT_UNITS = 2;

    private int col1 = 0;
    private int row1 = 0;
    private int col2 = 0;
    private int row2 = 0;

    private int height = EchoConstants.UNDEFINED_SIZE;
    private int heightUnits = PIXEL_UNITS;
    private int width = EchoConstants.UNDEFINED_SIZE;
    private int widthUnits = PIXEL_UNITS;

    /**
     * CellConstraints constructor for col1 == col2 and row1 == row2.  The co-ordinates will be normalised
     * so that 0 &lt;= col1 &gt;= col2 and 0 &lt;= row1 &gt;= row2.
     */
    public CellConstraints(int col1, int row1) {
      this(col1, row1, col1, row1);
    }

    /**
     * CellConstraints constructor.  The co-ordinates will be normalised
     * so that 0 &lt;= col1 &gt;= col2 and 0 &lt;= row1 &gt;= row2.
     */
    public CellConstraints(int col1, int row1, int col2, int row2) {
      super();
      int temp;
      if (col2 < col1) {
        temp = col2;
        col1 = col2;
        col2 = temp;
      }
      if (row2 < row1) {
        temp = row2;
        row1 = row2;
        row2 = temp;
      }
      if (col1 < 0) {
        col1 = 0;

      }
      if (row1 < 0) {
        row1 = 0;

      }
      this.col1 = col1;
      this.col2 = col2;
      this.row1 = row1;
      this.row2 = row2;
    }

    /**
     * Returns the Col1 co-ord
     *
     * @return int
     */
    public int getCol1() {
      return col1;
    }

    /**
     * Returns the Col2 co-ord
     *
     * @return int
     */
    public int getCol2() {
      return col2;
    }

    /**
     * Returns the Row1 co-ord
     *
     * @return int
     */
    public int getRow1() {
      return row1;
    }

    /**
     * Returns the Row1 co-ord
     *
     * @return int
     */
    public int getRow2() {
      return row2;
    }

    /**
     * Returns true of the current GridCellContraint overlaps with the
     * provided one.
     */
    public boolean overlaps(CellConstraints cell) {
      return col1 <= cell.getCol2() && row1 <= cell.getRow2() && col2 >= cell.getCol1() && row2 >= cell.getRow1();
    }

    /**
     *
     */
    public String toString() {
      return "{" + col1 + "," + row1 + "," + col2 + "," + row2 + "}";
    }

    /**
     * Returns the current height value of the cell or -1 if its not set
     * @return the current height value of the cell or -1 if its not set
     */
    public int getHeight() {
      return height;
    }

    /**
     * Returns the current width value of the cell or -1 if its not set
     * @return the current width value of the cell or -1 if its not set
     */
    public int getWidth() {
      return width;
    }

    /**
     * Returns the height untis in action for this cell.
     * @return the height untis in action for this cell.
     */
    public int getHeightUnits() {
      return heightUnits;
    }

    /**
     * Returns the width untis in action for this cell.
     * @return the width untis in action for this cell.
     */
    public int getWidthUnits() {
      return widthUnits;
    }

    /**
     * Sets the height of the cell
     * @param height - the height of the cell
     */
    public void setHeight(int height) {
      this.height = height;
    }

    /**
     * Sets the height units of the cell
     * @param units - the height units of the cell
     */
    public void setHeightUnits(int units) {
      if (units != PIXEL_UNITS && units != PERCENT_UNITS) {
        throw new IllegalArgumentException("The units must be PIXEL_UNITS or PERCENT_UNITS");
      }
      heightUnits = units;
    }

    /**
     * Sets the width of the cell
     * @param width the width of the cell
     */
    public void setWidth(int width) {
      this.width = width;
    }

    /**
     * Sets the width units of the cell
     * @param units - the width units of the cell
     */
    public void setWidthUnits(int units) {
      if (units != PIXEL_UNITS && units != PERCENT_UNITS) {
        throw new IllegalArgumentException("The units must be PIXEL_UNITS or PERCENT_UNITS");
      }
      widthUnits = units;
    }

    public CellConstraints setHeightInPixels(int height) {
      setHeight(height);
      setHeightUnits(PIXEL_UNITS);
      return this;
    }

    public CellConstraints setHeightInPercent(int height) {
      setHeight(height);
      setHeightUnits(PERCENT_UNITS);
      return this;
    }

    public CellConstraints setWidthInPixels(int width) {
      setWidth(width);
      setWidthUnits(PIXEL_UNITS);
      return this;
    }

    public CellConstraints setWidthInPercent(int width) {
      setWidth(width);
      setWidthUnits(PERCENT_UNITS);
      return this;
    }

  }

}

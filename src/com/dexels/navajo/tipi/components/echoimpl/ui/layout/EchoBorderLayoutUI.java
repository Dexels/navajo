package com.dexels.navajo.tipi.components.echoimpl.ui.layout;

import echopoint.layout.*;
import echopoint.layout.GridLayoutManager.*;
import echopoint.ui.*;
import echopoint.ui.layout.*;
import nextapp.echo.*;
import nextapp.echoservlet.*;
import nextapp.echoservlet.html.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 *
 * This class is definately used, but at the moment there is nothing custom about it and it's just a plain GridLayoutManagerUI
 *
 */

public class EchoBorderLayoutUI
    extends GridLayoutManagerUI {

  public EchoBorderLayoutUI() {
  }

  public void registered() {
  }

  private ComponentPeer _componentPeers[];

  /**
   * This class is used by GridLayoutManagerUI to manage the layout of the grid cells
   */
  private final class GridCellCoord {
    static final int UNKNOWN = -3;
    static final int CNOOP = -2;
    static final int NOOP = -1;
    static final int COMPONENT = 0;
    static final int BLANK = 1;

    private GridLayoutManager grid;
    private Component component;
    protected GridLayoutManager.CellConstraints cell;
    private int op = UNKNOWN;
    private int colspan = 0;
    private int rowspan = 0;
    private int col = 0;
    private int row = 0;

    /** contructor for a cell coord */
    private GridCellCoord(GridLayoutManager grid, int col, int row) {
      super();
      this.grid = grid;
      this.component = null;
      this.col = col;
      this.row = row;
      cell = grid.getContraints(col, row);
      if (cell != null) {
        // we have a cell but is it align to the top left
        if (isTopLeft(cell, col, row)) {
          colspan = cell.getCol2() - cell.getCol1() + 1;
          rowspan = cell.getRow2() - cell.getRow1() + 1;
          component = grid.getComponent(col, row);
          op = COMPONENT;
        }
        else {
          op = CNOOP;
        }
      }
      else {
        op = UNKNOWN;
      }
    }

    /** returns true if the cell is aligned to the top left corner of the cell */
    private boolean isTopLeft(CellConstraints cell, int col, int row) {
      if (cell == null) {
        return false;
      }
      if (cell.getCol1() == col && cell.getRow1() == row) {
        return true;
      }
      else {
        return false;
      }
    }

    /** work out the colspan for a blank cell from the current position */
    private int calcBlankColSpan(int col, int row) {
      int i = col;
      while (i < grid.getWidth()) {
        CellConstraints cell = grid.getContraints(i, row);
        if (cell != null && isTopLeft(cell, i, row)) {
          break;
        }
        i++;
      }
      this.colspan = i - col;
      return this.colspan;
    }

    private int getColspan() {
      return colspan;
    }

    private Component getComponent() {
      return component;
    }

    private int getOp() {
      return op;
    }

    private int getRowspan() {
      return rowspan;
    }

    private void setOp(int newOp) {
      op = newOp;
    }

    public String toString() {
      StringBuffer buf = new StringBuffer();
      if (op == UNKNOWN) {
        buf.append("UNKNOWN");
      }
      if (op == NOOP) {
        buf.append("NOOP");
      }
      if (op == CNOOP) {
        buf.append("CNOOP");
      }
      if (op == COMPONENT) {
        buf.append("COMPONENT");
      }
      if (op == BLANK) {
        buf.append("BLANK");

      }
      if (cell != null) {
        buf.append(" ");
        buf.append(cell);
      }
      buf.append(" c:");
      buf.append(col);
      buf.append(" r:");
      buf.append(row);

      buf.append(" cs:");
      buf.append(colspan);
      buf.append(" rs:");
      buf.append(rowspan);
      return buf.toString();
    }
  }

  /**
   * A ComponentPeer for a Component please!
   */
  private ComponentPeer _getPeer(Component component) {
    for (int i = 0; i < _componentPeers.length; i++) {
      if (_componentPeers[i].getComponent() == component) {
        return _componentPeers[i];
      }
    }
    return null;
  }

  /**
   * Renders the actual TD for the peer to go in.
   */
  private void _renderPeer(RenderingContext rc, Element td, ComponentPeer peer, GridLayoutManager grid, int col, int row) {
    ComponentStyle cellStyle = new ComponentStyle();
    cellStyle.addElementType(ElementNames.TD);

    int hAlign = grid.getHorizontalAlignment();
    int horzMasked = hAlign & GridLayoutManager.HORZ_ALIGNMENT_MASK;

    setHorizontalAlignment(td, cellStyle, horzMasked);
    setVerticalAlignment(td, cellStyle, grid.getVerticalAlignment());

    if (col % 2 == 0) {
      if ( (hAlign & GridLayoutManager.EVEN_LEFT) == GridLayoutManager.EVEN_LEFT) {
        setHorizontalAlignment(td, cellStyle, EchoConstants.LEFT);
      }
      else if ( (hAlign & GridLayoutManager.EVEN_RIGHT) == GridLayoutManager.EVEN_RIGHT) {
        setHorizontalAlignment(td, cellStyle, EchoConstants.RIGHT);
      }
      else if ( (hAlign & GridLayoutManager.EVEN_CENTER) == GridLayoutManager.EVEN_CENTER) {
        setHorizontalAlignment(td, cellStyle, EchoConstants.CENTER);
      }
    }
    else {
      if ( (hAlign & GridLayoutManager.ODD_LEFT) == GridLayoutManager.ODD_LEFT) {
        setHorizontalAlignment(td, cellStyle, EchoConstants.LEFT);
      }
      else if ( (hAlign & GridLayoutManager.ODD_RIGHT) == GridLayoutManager.ODD_RIGHT) {
        setHorizontalAlignment(td, cellStyle, EchoConstants.RIGHT);
      }
      else if ( (hAlign & GridLayoutManager.ODD_CENTER) == GridLayoutManager.ODD_CENTER) {
        setHorizontalAlignment(td, cellStyle, EchoConstants.CENTER);
      }
    }

    String cellStyleName = rc.getDocument().addStyle(cellStyle);
    td.addAttribute(ElementNames.Attributes.CLASS, cellStyleName);

    // and render the component
    peer.render(rc, td);
  }

  /**
   * This method is called when the LayoutManager should layout the
   * child component peer objects.  This method will be called from the
   * ComponentPeer of the Component that contains this LayoutManager.
   *
   * @see nextapp.echoservlet.ComponentPeer for more information on rendering
   */
  public void render(RenderingContext rc, Element parent, ComponentPeer parentPeer, ComponentPeer[] children) {
    _componentPeers = children;
    GridLayoutManager grid = (GridLayoutManager) getLayoutManager();

    ////////////////////////////////////////////////
    // First step to build a quick model of our cells
    // in a W x H array
    ////////////////////////////////////////////////
    GridCellCoord[][] coords = new GridCellCoord[grid.getWidth()][grid.getHeight()];

    //
    // this step will work out if we have a component
    // and whether its top,left aligned
    //
    for (int row = 0; row < grid.getHeight(); row++) {
      for (int col = 0; col < grid.getWidth(); col++) {
        coords[col][row] = new GridCellCoord(grid, col, row);
      }
    }
    //	// This step needs to look forward and backwards to see
    // if we have neighbours.
    //
    for (int row = 0; row < grid.getHeight(); row++) {
      for (int col = 0; col < grid.getWidth(); col++) {
        GridCellCoord coord = coords[col][row];
        int op = coord.getOp();
        int prevOp = GridCellCoord.UNKNOWN;
        if (col - 1 >= 0) {
          prevOp = coords[col - 1][row].getOp();

        }
        if (op == GridCellCoord.UNKNOWN) {
          // are we at the left edge
          if (col - 1 == -1) {
            coord.setOp(GridCellCoord.BLANK);
            coord.calcBlankColSpan(col, row);
            // is the previous cell a NO-OP or COMPONENT?
          }
          else if (prevOp == GridCellCoord.NOOP || prevOp == GridCellCoord.BLANK) {
            coord.setOp(GridCellCoord.NOOP);
            // its a blank cell that needs a colspan
          }
          else {
            coord.setOp(GridCellCoord.BLANK);
            coord.calcBlankColSpan(col, row);
          }
        }
      }
    }

    ////////////////////////////////////////////////
    // Now we can output HTML
    ////////////////////////////////////////////////
    Installer.startComment(parentPeer, parent);

    Element table = new Element(ElementNames.TABLE);
    table.addAttribute("border", 0);
    if (grid.isFullWidth()) {
      table.addAttribute("width", "100%");
    }
    if (grid.isFullHeight()) {
      table.addAttribute("height", "100%");
    }
    table.addAttribute("cellpadding", 0);
    table.addAttribute("cellspacing", grid.getCellSpacing());

    for (int row = 0; row < grid.getHeight(); row++) {
      Element tr = null;
      for (int col = 0; col < grid.getWidth(); col++) {
        GridCellCoord coord = coords[col][row];
        CellConstraints cell = coord.cell;

        // NOOP or CNOOP
        if (coord.getOp() == GridCellCoord.NOOP || coord.getOp() == GridCellCoord.CNOOP) {
          continue;
        }

        Element td = new Element(ElementNames.TD);
        if (tr == null) {
          tr = new Element(ElementNames.TR);
          table.add(tr);
        }
        tr.add(td);

        td.setWhitespaceRelevant(true);
        if (cell != null) {
          if (cell.getWidth() != -1) {
            String width = "" + cell.getWidth() + (cell.getWidthUnits() == CellConstraints.PIXEL_UNITS ? "" : "%");
            td.addAttribute("width", width);
          }
          if (cell.getHeight() != -1) {
            String height = "" + cell.getWidth() + (cell.getWidthUnits() == CellConstraints.PIXEL_UNITS ? "" : "%");
            td.addAttribute("height", height);
          }
        }

        if (coord.getColspan() > 1) {
          td.addAttribute("colspan", coord.getColspan());
        }
        if (coord.getRowspan() > 1) {
          td.addAttribute("rowspan", coord.getRowspan());

          // COMPONENT
        }
        if (coord.getOp() == GridCellCoord.COMPONENT) {
          Component c = coord.getComponent();

          ComponentPeer peer = _getPeer(c);

          if (peer != null) {
            _renderPeer(rc, td, peer, grid, col, row);
          }
          else {
            td.addHtml("\n<!-- No peer for found component at {" + col + "," + row + "} -->\n");
          }
        }
        // BLANK
        if (coord.getOp() == GridCellCoord.BLANK) {
          td.addHtml("&nbsp;");
        }

      }
    }
    parent.add(table);
    Installer.endComment(parentPeer, parent);
    rc.getDocument().setCursorOnNewLine(true);
  }

  /**
   * Sets horizintal alignment
   */
  private void setHorizontalAlignment(Element td, ComponentStyle style, int alignment) {
    style.setHorizontalAlignment(alignment);
    switch (alignment) {
      case EchoConstants.LEFT:
        td.addAttribute("align", "left");
        break;
      case EchoConstants.CENTER:
        td.addAttribute("align", "center");
        break;
      case EchoConstants.RIGHT:
        td.addAttribute("align", "right");
        break;
    }

  }

  /**
   * Sets vertical alignment
   */
  private void setVerticalAlignment(Element td, ComponentStyle style, int alignment) {
    style.setVerticalAlignment(alignment);
    switch (alignment) {
      case EchoConstants.TOP:
        td.addAttribute("valign", "top");
        break;
      case EchoConstants.CENTER:
        td.addAttribute("valign", "middle");
        break;
      case EchoConstants.BOTTOM:
        td.addAttribute("valign", "bottom");
        break;
    }

  }

}

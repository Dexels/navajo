package com.dexels.navajo.tipi.components.echoimpl.ui.layout;

/*
 * This file is part of the Echo Point Project.  This project is a collection
 * of Components that have extended the Echo Web Application Framework.
 *
 * EchoPoint is free software; you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * EchoPoint is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Echo Point; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */

import com.dexels.navajo.tipi.components.echoimpl.layout.*;
import echopoint.layout.*;
import echopoint.layout.GridLayoutManager.*;
import echopoint.ui.*;
import echopoint.ui.layout.*;
import nextapp.echo.*;
import nextapp.echoservlet.*;
import nextapp.echoservlet.html.*;

/**
 *
 * EchoGridBagLayoutUI is the peer for EchoGridBagLayout.
 */
public class EchoGridBagLayoutUI
    extends LayoutManagerPeer {
  private ComponentPeer _componentPeers[];

  /**
   * This class is used by EchoGridBagLayoutUI to manage the layout of the grid cells
   */
  private final class GridCellCoord {
    static final int UNKNOWN = -3;
    static final int CNOOP = -2;
    static final int NOOP = -1;
    static final int COMPONENT = 0;
    static final int BLANK = 1;

    private EchoGridBagLayout grid;
    private Component component;
    protected EchoGridBagConstraints cell;
    private int op = UNKNOWN;
    private int colspan = 0;
    private int rowspan = 0;
    private int col = 0;
    private int row = 0;

    /** contructor for a cell coord */
    private GridCellCoord(EchoGridBagLayout grid, int col, int row) {
      //super();????
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
    private boolean isTopLeft(EchoGridBagConstraints cell, int col, int row) {
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
        EchoGridBagConstraints cell = grid.getContraints(i, row);
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
  private void _renderPeer(RenderingContext rc, Element td, ComponentPeer peer, EchoGridBagLayout grid, int col, int row) {
//    System.err.println(
//        "--------------------->>>  Rendering peer in EchoGridBagLayoutUI...");

    EchoGridBagConstraints cell = grid.getContraints(col, row);
//    System.err.println("Peer has constraints: " + cell);

    ComponentStyle cellStyle = new ComponentStyle();
    cellStyle.addElementType(ElementNames.TD);

    int hAlign = cell.getHorizontalAlignment();
    int horzMasked = hAlign & EchoGridBagLayout.HORZ_ALIGNMENT_MASK;
    setHorizontalAlignment(td, cellStyle, horzMasked);
    setVerticalAlignment(td, cellStyle, cell.getVerticalAlignment());

    if (col % 2 == 0) {
      if ( (hAlign & EchoGridBagLayout.EVEN_LEFT) == EchoGridBagLayout.EVEN_LEFT) {
        setHorizontalAlignment(td, cellStyle, EchoConstants.LEFT);
      }
      else if ( (hAlign & EchoGridBagLayout.EVEN_RIGHT) == EchoGridBagLayout.EVEN_RIGHT) {
        setHorizontalAlignment(td, cellStyle, EchoConstants.RIGHT);
      }
      else if ( (hAlign & EchoGridBagLayout.EVEN_CENTER) == EchoGridBagLayout.EVEN_CENTER) {
        setHorizontalAlignment(td, cellStyle, EchoConstants.CENTER);
      }
    }
    else {
      if ( (hAlign & EchoGridBagLayout.ODD_LEFT) == EchoGridBagLayout.ODD_LEFT) {
        setHorizontalAlignment(td, cellStyle, EchoConstants.LEFT);
      }
      else if ( (hAlign & EchoGridBagLayout.ODD_RIGHT) == EchoGridBagLayout.ODD_RIGHT) {
        setHorizontalAlignment(td, cellStyle, EchoConstants.RIGHT);
      }
      else if ( (hAlign & EchoGridBagLayout.ODD_CENTER) == EchoGridBagLayout.ODD_CENTER) {
        setHorizontalAlignment(td, cellStyle, EchoConstants.CENTER);
      }
    }

    String cellStyleName = rc.getDocument().addStyle(cellStyle);

    td.addAttribute(ElementNames.Attributes.CLASS, cellStyleName);
    //
    // and render the component
    peer.render(rc, td);
  }

  private boolean rowHasComponents(int row, EchoGridBagLayout grid, GridCellCoord[][] coords) {
    for (int col = 0; col < grid.getWidth(); col++) {
      GridCellCoord coord = coords[col][row];
      if (coord.getOp() == GridCellCoord.COMPONENT) {
        return true;
      }
    }
    return false;

  }

  /**
   * This method is called when the LayoutManager should layout the
   * child component peer objects.  This method will be called from the
   * ComponentPeer of the Component that contains this LayoutManager.
   *
   * @see nextapp.echoservlet.ComponentPeer for more information on rendering
   */
  public void render(RenderingContext rc, Element parent, ComponentPeer parentPeer, ComponentPeer[] children) {
//    System.err.println(
//        "--------------------->>>  Rendering in EchoGridBagLayoutUI...");
    _componentPeers = children;
    EchoGridBagLayout grid = (EchoGridBagLayout) getLayoutManager();

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
//            System.err.println("calc: " + row + ", " + col + ", op: " + coord.getOp());
            // is the previous cell a NO-OP or COMPONENT?
          }
          else if (prevOp == GridCellCoord.NOOP || prevOp == GridCellCoord.BLANK) {
            coord.setOp(GridCellCoord.NOOP);
//            System.err.println("calc: " + row + ", " + col + ", op: " + coord.getOp());
            // its a blank cell that needs a colspan
          }
          else {
            coord.setOp(GridCellCoord.BLANK);
            coord.calcBlankColSpan(col, row);
//            System.err.println("calc: " + row + ", " + col + ", op: " + coord.getOp());
          }
        }
      }
    }

    // deterime grid x unit
    int max_component_count = 0;
    for (int row = 0; row < grid.getHeight(); row++) {
      int row_comp_count = 0;
      for (int col = 0; col < grid.getWidth(); col++) {
        GridCellCoord coord = coords[col][row];
        if (coord.getOp() == GridCellCoord.COMPONENT) {
          if (coord.cell.weightx > 0.0) {
            row_comp_count++;
          }
        }
      }
      if (row_comp_count > max_component_count) {
        max_component_count = row_comp_count;
      }
    }

    double grid_unit = 1.0 / max_component_count;

    //check all rows and calculate width...

    for (int row = 0; row < grid.getHeight(); row++) {
      double totaly = 0.0;
      for (int col = 0; col < grid.getWidth(); col++) {
        GridCellCoord coord = coords[col][row];
        if (coord != null && coord.cell != null && coord.getOp() == GridCellCoord.COMPONENT) {
          EchoGridBagConstraints cons = coord.cell;
          totaly += cons.weighty;
        }
      }
      for (int col = 0; col < grid.getWidth(); col++) {
        GridCellCoord coord = coords[col][row];
        if (coord != null && coord.cell != null && coord.getOp() == GridCellCoord.COMPONENT) {
          EchoGridBagConstraints cons = coord.cell;
          if (cons.weightx > 0.0) {
            cons.weightx = coord.getColspan() * grid_unit;
          }
          if (cons.weighty > 0.0) {
            cons.weighty = cons.weighty / totaly;
          }
//          System.err.println(" -- Set component: " + row + ", " + col +
//                             " weightx: " + cons.weightx + ", weighty: " +
//                             cons.weighty + ", colspan: " + coord.getColspan());
        }
      }
    }

    ////////////////////////////////////////////////
    // Now we can output HTML
    ////////////////////////////////////////////////

    Installer.startComment(parentPeer, parent);

    Element table = new Element(ElementNames.TABLE);
    table.addAttribute("border", 0);
    table.addAttribute("width", "100%");
    table.addAttribute("height", "100%");
    table.addAttribute("cellpadding", 0);
    table.addAttribute("cellspacing", grid.getCellSpacing());

    for (int row = 0; row < grid.getHeight(); row++) {
      Element tr = null;
      if (rowHasComponents(row, grid, coords)) {
        for (int col = 0; col < grid.getWidth(); col++) {
          GridCellCoord coord = coords[col][row];
          EchoGridBagConstraints cell = coord.cell;

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

            //add gridbag constraints
            Insets insets = cell.insets;
            String insetString = "padding-top:" + insets.getTop() + "px;" + "padding-left:" + insets.getLeft() + "px;" + "padding-bottom:" + insets.getBottom() + "px;" + "padding-right:" + insets.getRight() + "px;";
            td.addAttribute("style", insetString);

            if (cell.weightx > 0) {
              int perc = (int) (cell.weightx * 100);
              td.addAttribute("width", perc + "%");
            }
            else {
              if (cell.ipadx > 0) {
                td.addAttribute("width", cell.ipadx + "px");
              }
            }
            if (cell.weighty > 0) {
              int perc = (int) (cell.weighty * 100);
              td.addAttribute("height", perc + "%");
            }
            else {
              if (cell.ipady > 0) {
                td.addAttribute("height", cell.ipady + "px");
              }
            }

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
    }
    parent.add(table);
    Installer.endComment(parentPeer, parent);
    rc.getDocument().setCursorOnNewLine(true);

  }

  /**
   * Sets horizintal alignment
   */
  private void setHorizontalAlignment(Element td, ComponentStyle style, int alignment) {
    //style.setHorizontalAlignment(alignment);
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
    //style.setVerticalAlignment(alignment);
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

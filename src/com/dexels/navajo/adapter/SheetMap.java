package com.dexels.navajo.adapter;

import com.dexels.navajo.adapter.poi.POITools;

import com.dexels.navajo.mapping.*;
import com.dexels.navajo.server.*;
import com.dexels.navajo.document.*;

import org.apache.poi.hssf.dev.*;
import org.apache.poi.hssf.eventmodel.*;
import org.apache.poi.hssf.model.*;
import org.apache.poi.hssf.record.*;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.poifs.filesystem.*;


import java.io.*;

import java.util.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author Arjen Schoneveld
 * @version $Id$
 *
 * Excel spreadsheet adapter.
 *
 */

public class SheetMap implements Mappable {

  public String cellQuery;
  public CellMap cell;
  public CellMap [] rowByOffset;
  public CellMap [] columnByOffset;
  public ColumnMap [] column;
  public String sheetName;
  public int rowOffset = 0;
  public int columnOffset = -1;

  private HSSFSheet sheet;

  /**
   * set the name of the excel sheet. Format ([file location:tab]), where "tab"
   * sets the index of the sheet's tab pane.
   *
   * @param sheetName
   * @throws UserException
   */
  public void setSheetName(String sheetName) throws UserException {

    String fileName = "";

    try {
      StringTokenizer tokens = new StringTokenizer(sheetName, ":");
      if (tokens.countTokens() != 2)
          throw new UserException(-1, "Invalid sheet specification: " + sheetName);
      fileName = tokens.nextToken();
      String sheetNumber = tokens.nextToken();
      POIFSFileSystem fs      =
              new POIFSFileSystem(new FileInputStream(fileName));
      HSSFWorkbook wb = new HSSFWorkbook(fs);
      HSSFSheet sheet = wb.getSheetAt(Integer.parseInt(sheetNumber));
      this.sheet = sheet;
    } catch (FileNotFoundException fnfe) {
      throw new UserException(-1, "Could not open spreadsheet: " + fileName);
    } catch (IOException ioe) {
      throw new UserException(-1, "Error reading spreadsheet: " + fileName + "(" + ioe.getMessage() + ")");
    }
  }

  public void load(Parameters parms, Navajo inMessage, Access access, NavajoConfig config) throws MappableException, UserException {
  }

  /**
   * Define a cell query:
   * Either
   * [A-z]+[1-9][0-9]*, e.g. E45
   * or
   * [A-z]+[1-9][0-9]*:[A-z]+[1-9][0-9]*, e.g. B23:K45
   *
   * @param id
   */

  public void setCellQuery(String id) {
    this.cellQuery = id;
  }

  /**
   * return a single cell if the cell-query result only contains a single cell.
   *
   * @param id
   * @return
   */
  public CellMap getCell() {

    CellMap cm = null;
    HSSFCell cell = POITools.getCell(sheet, cellQuery);
    if (cell != null) {
      cm = new CellMap();
      cm.setCell(cell, "");
    }
    return cm;
  }

  /**
   * Return result of cell query column-wise.
   * Each column contains 1 or more rows that contain the cells.
   *
   * @return
   * @throws UserException
   */
  public ColumnMap [] getColumn() throws UserException {
    if (column == null) {
      ArrayList all = POITools.getColumn(sheet, cellQuery);
      column = new ColumnMap[all.size()];
      for (int i = 0 ; i < all.size(); i++) {
        column[i] = new ColumnMap();
        ArrayList row = (ArrayList) all.get(i);
        if (row != null) {
          column[i].row = new CellMap[row.size()];
          column[i].row = (CellMap []) row.toArray(column[i].row);
        }
      }
    }
    return column;
  }

  /**
   * Returns an entire column by a relative offset (see setColumnOffset()) of the total result of the cell-query.
   *
   * @return
   * @throws UserException
   */
  public CellMap [] getColumnByOffset() throws UserException {
    if (column == null)
      getColumn();
    if (columnOffset >= column.length)
      throw new UserException(-1, "Column offset too large: " + columnOffset);
    return column[columnOffset].getRow();
  }

  /**
   * Returns an entire row by a relative offset (see setRowOffset()) of the total result of the cell-query.
   *
   * @return
   * @throws UserException
   */
  public CellMap [] getRowByOffset() throws UserException {
    if (column == null)
      getColumn();
    ArrayList result = new ArrayList();
    for (int i = 0; i < column.length; i++) {
       CellMap [] cr = column[i].getRow();
       if (rowOffset >= cr.length)
        throw new UserException(-1, "Row offset too large: " + rowOffset);
       result.add(cr[rowOffset]);
    }
    CellMap [] rResult = new CellMap[result.size()];
    rResult = (CellMap []) result.toArray(rResult);
    return rResult;
  }

  public void store() throws MappableException, UserException {
  }

  public void kill() {
  }

  public static void main(String args[]) throws Exception {
    SheetMap sm = new SheetMap();
    sm.setSheetName("/home/arjen/cijfers.xls:0");
    sm.setCellQuery("A10:A20");
    ColumnMap [] column = sm.getColumn();
    for (int i = 0; i < column.length; i++) {
      CellMap [] row = column[i].getRow();
      for (int j = 0; j < row.length; j++) {
        CellMap cell = row[j];
        System.out.println("value of " + cell.getId() + " = " + cell.getCellValue() + "(" + cell.getCellType() + ")");
      }
    }
  }

  public void setRowOffset(int rowOffset) {
    this.rowOffset = rowOffset;
  }
  public void setColumnOffset(int columnOffset) {
    this.columnOffset = columnOffset;
  }
}
